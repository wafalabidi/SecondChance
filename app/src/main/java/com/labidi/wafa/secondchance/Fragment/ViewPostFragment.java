package com.labidi.wafa.secondchance.Fragment;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.Utils.SquareImageView;
import com.labidi.wafa.secondchance.Utils.UniversalImageLoader;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by macbook on 26/12/2017.
 */

public class ViewPostFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ViewPostFragment";


    public ViewPostFragment(){
        super();
        setArguments(new Bundle());
    }

    String caption;
    String imageUrl;

    //widgets
    private SquareImageView mPostImage;
    private TextView mBackLabel, mCaption, mUsername, mTimestamp;
    private ImageView  mEllipses, mHeartRed, mHeartWhite, mProfileImage;


    //vars
    Post post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_post, container, false);
        mPostImage = (SquareImageView) view.findViewById(R.id.post_image);
        //bottomNavigationView = (BottomNavigationViewEx) view.findViewById(R.id.bottomNavViewBar);
        mCaption = (TextView) view.findViewById(R.id.image_caption);
        mUsername = (TextView) view.findViewById(R.id.username);
        mUsername.setText(User.FirstName);
        mTimestamp = (TextView) view.findViewById(R.id.image_time_posted);
        mEllipses = (ImageView) view.findViewById(R.id.ivEllipses);
        mHeartRed = (ImageView) view.findViewById(R.id.image_heart_red);
        mHeartWhite = (ImageView) view.findViewById(R.id.image_heart);
        mProfileImage = (ImageView) view.findViewById(R.id.profile_photo);
        mEllipses.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);
        Bundle bundle=getArguments();
        mCaption.setText(post.getSaying());
        mPostImage.setImageURI(Uri.parse((String.valueOf(bundle.getString("")))));
        Toast.makeText(getApplicationContext(), imageUrl + " is clicked!", Toast.LENGTH_SHORT).show();
        if(imageUrl!=""){
            Picasso.with(getActivity()).load(imageUrl).into(mPostImage);       }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(User.imgprofile!=""){
            Picasso.with(getActivity()).load(User.imgprofile).into(mProfileImage);       }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        getPhotoDetails();
        //setupWidgets();

        return view;
    }

    private void getPhotoDetails(){


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ivEllipses){
            RetrofitClient retrofitClient = new RetrofitClient();
            UserService.deletePost service = retrofitClient.getRetrofit().create(UserService.deletePost.class);
            //UserService.
            Call<PostsResponse> call = service.deletePost(post.getidPost());
            call.enqueue(new Callback<PostsResponse>() {
                @Override
                public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                    if (response.isSuccessful()) {
                        Log.e("psika","piska");


                    } else {
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PostsResponse> call, Throwable t) {
                    if (t != null){
                        Log.e("upload Errors" , t.getMessage());
                    }
                }
            });
            Toast.makeText(getApplicationContext(), imageUrl + " is clicked!", Toast.LENGTH_SHORT).show();

        }

    }
}
