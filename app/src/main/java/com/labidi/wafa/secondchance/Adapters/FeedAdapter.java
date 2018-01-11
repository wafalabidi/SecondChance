package com.labidi.wafa.secondchance.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.CommentActivity;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.Like;
import com.labidi.wafa.secondchance.Entities.LikeDAO;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.LikesResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.MainActivity;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.Utils.LocalFiles;
import com.labidi.wafa.secondchance.view.LoadingFeedItemView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wafa on 09/12/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;

    //  private final List<FeedItem> feedItems = new ArrayList<>();
    public ArrayList<Post> items;
    private Context context;
    private OnFeedItemClickListener onFeedItemClickListener;
    private ArrayList<Like> likes;
    private boolean showLoadingView = false;
    LikeDAO likeDAO;
    LocalFiles localFiles;
    private Integer currentUserId;

    public FeedAdapter(Context context) {
        this.context = context;
    }

    public FeedAdapter(Context context, ArrayList items, ArrayList<Like> likes) {
        this.context = context;
        this.items = items;
        this.likes = likes;
        likeDAO = new LikeDAO();

        localFiles = new LocalFiles(context.getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        currentUserId = localFiles.getInt(LocalFiles.Id);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DEFAULT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
            CellFeedViewHolder cellFeedViewHolder = new CellFeedViewHolder(view);
            setupClickableViews(view, cellFeedViewHolder);
            return cellFeedViewHolder;
        } else if (viewType == VIEW_TYPE_LOADER) {
            LoadingFeedItemView view = new LoadingFeedItemView(context);
            view.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            return new LoadingCellFeedViewHolder(view);
        }

        return null;
    }

    private void setupClickableViews(final View view, final CellFeedViewHolder cellFeedViewHolder) {

        cellFeedViewHolder.btnComments.setOnClickListener(v -> onFeedItemClickListener.onCommentsClick(view, cellFeedViewHolder.getAdapterPosition()));
        cellFeedViewHolder.btnMore.setOnClickListener(v ->
                onFeedItemClickListener.onMoreClick(v, cellFeedViewHolder.getAdapterPosition()));
        cellFeedViewHolder.ivFeedCenter.setOnClickListener(v -> {
            int adapterPosition = cellFeedViewHolder.getAdapterPosition();
//                feedItems.get(adapterPosition).likesCount++;
            notifyItemChanged(adapterPosition, ACTION_LIKE_IMAGE_CLICKED);
            if (context instanceof MainActivity) {
                ((MainActivity) context).showLikedSnackbar();
            }
        });
        cellFeedViewHolder.btnLike.setOnClickListener(v -> {
            int adapterPosition = Math.abs(items.size() - 1 - cellFeedViewHolder.getAdapterPosition());
            //feedItems.get(adapterPosition).likesCount++;
            Post post = items.get(adapterPosition);
            Integer postId = post.getidPost();
            if (cellFeedViewHolder.feedItem.isLiked) {
                ((ImageButton) v).setImageResource(R.drawable.ic_heart_outline_grey);
                likeDAO.unLike(new Like(postId, currentUserId)).enqueue(new Callback<ConfirmationResponse>() {
                    @Override
                    public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                        Log.e("Unlike", "Ok");
                    }

                    @Override
                    public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                        Log.e("Unlike", t.getMessage());

                    }
                });
                likes.remove(new Like(postId, currentUserId));
                Log.e("Poist ID ", " " + postId);
                cellFeedViewHolder.feedItem.isLiked = ! cellFeedViewHolder.feedItem.isLiked ;

            } else {
                ((ImageButton) v).setImageResource(R.drawable.ic_heart_red);
                Log.e("Poist ID ", " Unliked" + postId);
                likeDAO.Like(new Like(postId, currentUserId)).enqueue(new Callback<ConfirmationResponse>() {
                    @Override
                    public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                        Log.e("Like", "Ok");
                        likes.add(new Like(postId, currentUserId));

                    }

                    @Override
                    public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                        Log.e("Like", t.getMessage() + postId);

                    }
                });
                likes.add(new Like(postId, currentUserId));
                cellFeedViewHolder.feedItem.isLiked = ! cellFeedViewHolder.feedItem.isLiked ;

            }

            notifyItemChanged(adapterPosition, ACTION_LIKE_BUTTON_CLICKED);


        });
        cellFeedViewHolder.ivUserProfile.setOnClickListener(v -> {
            Post post = items.get(cellFeedViewHolder.getAdapterPosition());
            onFeedItemClickListener.onProfileClick(view, post.getIdUser());
        });
        cellFeedViewHolder.btnComments.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, CommentActivity.class);
            if (items.get(cellFeedViewHolder.getAdapterPosition()).getidPost() != 0) {
                intent.putExtra("idPost", items.get(cellFeedViewHolder.getAdapterPosition()).getidPost());
                context.startActivity(intent);
            } else
                Toast.makeText(context, "Eroor–", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int row = Math.abs(items.size() - 1 - position);
        ((CellFeedViewHolder) viewHolder).bindView(items.get(row));

        if (getItemViewType(position) == VIEW_TYPE_LOADER) {
            bindLoadingFeedItem((LoadingCellFeedViewHolder) viewHolder);
        }
    }

    private void bindLoadingFeedItem(final LoadingCellFeedViewHolder holder) {
        holder.loadingFeedItemView.setOnLoadingFinishedListener(() -> {
            showLoadingView = false;
            notifyItemChanged(0);
        });
        holder.loadingFeedItemView.startLoading();
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(boolean animated) {
       /* feedItems.clear();
        feedItems.addAll(Arrays.asList(
                new FeedItem(33, false),
                new FeedItem(1, false),
                new FeedItem(223, false),
                new FeedItem(2, false),
                new FeedItem(6, false),
                new FeedItem(8, false),
                new FeedItem(99, false)
        ));
        if (animated) {
            notifyItemRangeInserted(0, feedItems.size());
        } else {
            notifyDataSetChanged();
        }*/
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public class CellFeedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFeedCenter)
        ImageView ivFeedCenter;
        @BindView(R.id.ivFeedBottom)
        TextView ivFeedBottom;
        @BindView(R.id.btnComments)
        ImageButton btnComments;
        @BindView(R.id.btnLike)
        ImageButton btnLike;
        @BindView(R.id.btnMore)
        ImageButton btnMore;
        @BindView(R.id.vBgLike)
        View vBgLike;
        @BindView(R.id.ivLike)
        ImageView ivLike;
        @BindView(R.id.tsLikesCounter)
        TextSwitcher tsLikesCounter;
        @BindView(R.id.ivUserProfile)
        TextView ivUserProfile;
        @BindView(R.id.vImageRoot)
        FrameLayout vImageRoot;
        @BindView(R.id.userProfilePic)
        CircleImageView userProfilpic;
        FeedItem feedItem;
        User currentUser;

        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User currentUser) {
            this.currentUser = currentUser;
        }

        public CellFeedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(Post post) {
            feedItem = new FeedItem(post.getLikes(), false);
            if (likes.contains(new Like(post.getidPost(), currentUserId))) {
                btnLike.setImageResource(R.drawable.ic_heart_red);
                feedItem.isLiked = true;
                Log.e("Islike", "islikde");
            } else {
                btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
                Log.e("not Like", "not like");

            }
           Picasso.with(FeedAdapter.this.context).load(post.getImage()).placeholder(R.drawable.source).into(ivFeedCenter);
           // ivFeedCenter.setImageResource(R.drawable.source);
            ivUserProfile.setText(post.getFirstName());
            if (post.getImg_profile() != "") {
                Picasso.with(context).load(RetrofitClient.BASE_URL + post.getImg_profile()).into(userProfilpic);
            }

            ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ivFeedBottom.setText(post.getSaying());

            // btnLike.setImageResource(R.drawable.ic_heart_outline_grey);// TODO Dynamize
            tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(
                    R.plurals.likes_count, post.getLikes(), post.getLikes()
            ));


        }

        public FeedItem getFeedItem() {
            return feedItem;
        }
    }

    public class LoadingCellFeedViewHolder extends CellFeedViewHolder {

        LoadingFeedItemView loadingFeedItemView;

        public LoadingCellFeedViewHolder(LoadingFeedItemView view) {
            super(view);
            this.loadingFeedItemView = view;
        }

        @Override
        public void bindView(Post post) {
            super.bindView(post);
        }
    }

    public static class FeedItem {
        public int likesCount;
        public boolean isLiked;

        public FeedItem(int likesCount, boolean isLiked) {
            this.likesCount = likesCount;
            this.isLiked = isLiked;
        }
    }

    public interface OnFeedItemClickListener {
        void onCommentsClick(View v, int position);

        void onMoreClick(View v, int position);

        void onProfileClick(View v, int idUser);
    }
}
