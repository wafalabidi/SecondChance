package com.esprit.secondchance.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.FeedAdapter;
import com.esprit.secondchance.CommentActivity;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.Like;
import com.esprit.secondchance.Entities.Post;
import com.esprit.secondchance.Entities.Response.LikesResponse;
import com.esprit.secondchance.Entities.Response.PostsResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.MainActivity;
import com.esprit.secondchance.R;
import com.esprit.secondchance.UserProfileActivity;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.SquareImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by macbook on 26/12/2017.
 */

public class ViewPostFragment extends Fragment {
    private static final String TAG = "ViewPostFragment";


    public ViewPostFragment() {
        super();
        setArguments(new Bundle());
    }

    public void setP(Post p) {
        post = p;

    }

    //widgets
    private ImageView mPostImage;
    private TextView mBackLabel, mCaption, mUsername, mTimestamp;
    private ImageView mEllipses, mHeartRed, mHeartWhite, mProfileImage;
    ImageButton btnComments, btnLike;


    //vars
    Post post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_post, container, false);
        mPostImage = view.findViewById(R.id.ivFeedCenter);
        btnLike = view.findViewById(R.id.btnLike);
        mCaption = view.findViewById(R.id.ivFeedBottom);
        mUsername = view.findViewById(R.id.ivUserProfile);
        mUsername.setText(User.FirstName);
        mProfileImage = view.findViewById(R.id.userProfilePic);
        Bundle bundle = getArguments();
        mCaption.setText(post.getSaying());
        btnComments = view.findViewById(R.id.btnComments);
        mPostImage.setImageURI(Uri.parse((String.valueOf(bundle.getString("")))));
        if (post.getImage() != "") {
            Picasso.with(getActivity()).load(post.getImage()).into(mPostImage);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (User.imgprofile != "") {
            Picasso.with(getActivity()).load(User.imgprofile).into(mProfileImage);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        checkLike();
        getPhotoDetails();
        //setupWidgets();
        btnComments.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), CommentActivity.class);

            intent.putExtra("idPost", post.getidPost());
            startActivity(intent);

        });
        return view;
    }

    private void getPhotoDetails() {
    }

    public void checkLike() {
        LocalFiles localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.Likes likes = retrofitClient.getRetrofit().create(UserService.Likes.class);
        Call<LikesResponse> call = likes.getLikes(localFiles.getInt(LocalFiles.Id));
        call.enqueue(new Callback<LikesResponse>() {
            @Override
            public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {

                if (response.body().getLikes() != null) {
                    if (response.body().getLikes().contains(new Like(post.getidPost(), localFiles.getInt(LocalFiles.Id)))) {
                        btnLike.setImageResource(R.drawable.ic_heart_red);
                        btnLike.setOnClickListener(v -> unlike(localFiles.getInt(LocalFiles.Id), post.getidPost(), v));
                    }
                } else {
                    btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
                    btnLike.setOnClickListener(v -> like(localFiles.getInt(LocalFiles.Id), post.getidPost(), v));
                }
            }

            @Override
            public void onFailure(Call<LikesResponse> call, Throwable t) {
            }
        });
    }

    private void unlike(int idUser, int idPost, View v) {
        ((ImageButton) v).setImageResource(R.drawable.ic_heart_outline_grey);
        btnLike.setOnClickListener(vi -> like(idUser, post.getidPost(), vi));

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.Likes likes = retrofitClient.getRetrofit().create(UserService.Likes.class);
        Call<ConfirmationResponse> call = likes.unlike(idPost, idUser);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                Log.e("Start", "Checkpoint 3");


            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to acces remote server", Toast.LENGTH_SHORT).show();
                Log.e("BBBBB", t.getMessage());

            }
        });
    }

    private void like(int idUser, int idPost, View v) {

        ((ImageButton) v).setImageResource(R.drawable.ic_heart_red);
        btnLike.setOnClickListener(vi -> unlike(idUser, post.getidPost(), vi));
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.Likes likes = retrofitClient.getRetrofit().create(UserService.Likes.class);
        Call<ConfirmationResponse> call = likes.like(idPost, idUser);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                Log.e("Start", "Checkpoint 4");

            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to acces remote server", Toast.LENGTH_SHORT).show();
                Log.e("CCCCCC", t.getMessage());

            }
        });
    }

}
