package com.esprit.secondchance;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.FeedAdapter;
import com.esprit.secondchance.Adapters.FeedItemAnimator;
import com.esprit.secondchance.Entities.Like;
import com.esprit.secondchance.Entities.LikeDAO;
import com.esprit.secondchance.Entities.Post;
import com.esprit.secondchance.Entities.Response.LikesResponse;
import com.esprit.secondchance.Entities.Response.PostsResponse;
import com.esprit.secondchance.Utils.FriendsWatcherService;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.ProgressDialogAlternative;
import com.esprit.secondchance.view.FeedContextMenu;
import com.esprit.secondchance.view.FeedContextMenuManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
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
        setupToolbar();
        getPosts();
        ComponentName name = new ComponentName(this, FriendsWatcherService.class);
        jobInfo = new JobInfo.Builder(0, name).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setPeriodic(1000 * 60 * 15).build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        //ProgressDialogAlternative.ShowDIalog(this);
        getWindow().setEnterTransition(new Explode());
        getWindow().setAllowEnterTransitionOverlap(true);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(() -> {
            rvFeed.smoothScrollToPosition(0);
            feedAdapter.showLoadingView();
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
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);

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
        // int[] startingLocation = new int[2];
        //v.getLocationOnScreen(startingLocation);
        // intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        //startActivity(intent);
        //overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);

    }

    @Override
    public void onProfileClick(View v, int iduser) {
        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
        intent.putExtra("idUser", iduser);
        startActivity(intent);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
        Intent intent = new Intent(this, UserProfileActivity.class);
        int idUser = feedAdapter.items.get(feedItem).getIdUser();
        intent.putExtra("idUser", idUser);
        startActivity(intent);
        Toast.makeText(this, "idUser = " + idUser + " Post ID "
                        + feedAdapter.items.get(feedItem).getidPost()
                , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
        Dialog dialog = new Dialog(this);
        String url = feedAdapter.items.get(feedItem).getImage();
        dialog.setContentView(R.layout.reshare_dialog);
        LinearLayout relativeLayout = dialog.findViewById(R.id.login_button);
        relativeLayout.setOnClickListener(v -> {
            LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
            Post post = new Post();
            EditText editText = dialog.findViewById(R.id.etSaying);

            post.setSaying(editText.getText().toString());
            post.setIdUser(localFiles.getInt(LocalFiles.Id));
            post.setImage(url);
            Upload(post, dialog);

        });
        ImageView imageView = dialog.findViewById(R.id.ivpicture);
        Picasso.with(this).load(url).fit().into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                dialog.show();
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Failed to fetch data Please retry later", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
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
        ProgressDialogAlternative.ShowDIalog(this);
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.insertPost service = retrofitClient.getRetrofit().create(UserService.insertPost.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Integer currentUserId = localFiles.getInt(LocalFiles.Id);
        Call<PostsResponse> call = service.getAllPost(currentUserId);
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {

                if (response.isSuccessful() && response.body().getPost() != null) {
                    for (Post p : response.body().getPost()
                            ) {
                        if (!p.getImage().contains(RetrofitClient.BASE_URL))
                            p.setImage(RetrofitClient.BASE_URL + p.getImage());
                    }
                    getLikes((ArrayList<Post>) response.body().getPost());
                }
                ProgressDialogAlternative.Dissmiss();

            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                ProgressDialogAlternative.Dissmiss();
                Toast.makeText(MainActivity.this, "no connection", Toast.LENGTH_SHORT).show();
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
                ArrayList<Like> likes = new ArrayList<>();
                if (response.body().getLikes() != null) {
                    likes = response.body().getLikes();

                }
                Log.e("Get Likes", "Get Likes");

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
                ProgressDialogAlternative.Dissmiss();


            }

            @Override
            public void onFailure(Call<LikesResponse> call, Throwable t) {
                Log.e("Get lieks ", t.getMessage());
                ProgressDialogAlternative.Dissmiss();

            }
        });


    }

    public void ResearchClicked(View view) {
        Intent intent = new Intent(this, SearchPeoplesActivity.class);
        startActivity(intent);
    }

    private void Upload(Post post, Dialog dialog) {
        ProgressDialogAlternative.ShowDIalog(this);
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.insertPost insertPost = retrofitClient.getRetrofit().create(UserService.insertPost.class);
        Call<ResponseBody> call = insertPost.republish(
                post.getSaying(),
                post.getImage(),
                post.getIdUser()
        );
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    feedAdapter.items.add(post);
                    feedAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    feedAdapter.items.add(post);
                    feedAdapter.notifyDataSetChanged();
                }
                ProgressDialogAlternative.Dissmiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ProgressDialogAlternative.Dissmiss();
                Log.e("Upload Error", t.getMessage());
                ProgressDialogAlternative.Dissmiss();

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}