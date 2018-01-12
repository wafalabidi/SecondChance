package com.labidi.wafa.secondchance;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.FeedAdapter;
import com.labidi.wafa.secondchance.Adapters.FeedItemAnimator;
import com.labidi.wafa.secondchance.Adapters.ResearchResultAdapter;
import com.labidi.wafa.secondchance.Entities.Demande;
import com.labidi.wafa.secondchance.Entities.Like;
import com.labidi.wafa.secondchance.Entities.LikeDAO;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.DemandesResponse;
import com.labidi.wafa.secondchance.Entities.Response.LikesResponse;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.Response.SearchResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.Utils.FriendsWatcherService;
import com.labidi.wafa.secondchance.Utils.LocalFiles;
import com.labidi.wafa.secondchance.view.FeedContextMenu;
import com.labidi.wafa.secondchance.view.FeedContextMenuManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wafa on 09/12/2017.
 */


public class MainActivity extends BaseDrawerActivity implements FeedAdapter.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener {
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    @BindView(R.id.btnCreate)
    FloatingActionButton fabCreate;
    @BindView(R.id.content)
    CoordinatorLayout clContent;
    private FeedAdapter feedAdapter;

    private boolean pendingIntroAnimation;
    private JobInfo jobInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPosts();
        ComponentName name = new ComponentName(this ,FriendsWatcherService.class );

        jobInfo = new JobInfo.Builder(0 , name).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setPeriodic(1000 * 60 * 15).build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 10000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Util.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();

                    }
                })
                .start();

    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();


    }

    @Override
    public void onCommentsClick(View v, int position) {
        //final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        // intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        //startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v, int iduser) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this, iduser);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }

    private void getPosts() {

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.insertPost service = retrofitClient.getRetrofit().create(UserService.insertPost.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Integer currentUserId = localFiles.getInt(LocalFiles.Id);
        Call<PostsResponse> call = service.getAllPost(currentUserId);// TODO change user ID
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {

                if (response.isSuccessful() && response.body().getPost() != null) {
                    for (Post p : response.body().getPost()
                            ) {
                        p.setImage(RetrofitClient.BASE_URL + p.getImage());
                    }
                    Log.e("GePost" , "Get Post");
                    getLikes((ArrayList<Post>) response.body().getPost());
                }
                Log.e("Responsoe", response.message());

            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.e("Responsoe", t.getMessage());

            }
        });
    }

    private void getLikes(ArrayList<Post> posts) {
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Integer currentUserId = localFiles.getInt(LocalFiles.Id);
        LikeDAO likeDAO = new LikeDAO();
        likeDAO.getLikes(new Like(0, currentUserId)).enqueue(new Callback<LikesResponse>() {
            @Override
            public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {
                ArrayList<Like> likes  = new ArrayList<>();
                if (response.body().getLikes() != null) {
                  likes  = response.body().getLikes();

                }
                Log.e("Get Likes" , "Get Likes");

                feedAdapter = new FeedAdapter(MainActivity.this, posts, likes);
                feedAdapter.setOnFeedItemClickListener(MainActivity.this);
                rvFeed.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this) {
                    @Override
                    protected int getExtraLayoutSpace(RecyclerView.State state) {
                        return 300;
                    }

                    @Override
                    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                        try {
                            super.onLayoutChildren(recycler, state);
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }

                };
                rvFeed.setLayoutManager(linearLayoutManager);

                rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
                    }
                });
                rvFeed.setItemAnimator(new FeedItemAnimator());

            }

            @Override
            public void onFailure(Call<LikesResponse> call, Throwable t) {
                Log.e("Get lieks ", t.getMessage());
            }
        });


    }

    public void ResearchClicked(View view) {
        Intent intent = new Intent(this, SearchPeoplesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

}