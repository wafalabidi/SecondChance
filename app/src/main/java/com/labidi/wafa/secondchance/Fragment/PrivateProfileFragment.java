package com.labidi.wafa.secondchance.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.PhotosAdapter;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.MeasUtils;
import com.labidi.wafa.secondchance.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.labidi.wafa.secondchance.Fragment.HomeFragment.REQUEST_IMAGE_CAPTURE;

/**
 * Created by Wafa on 11/12/2017.
 */

public class PrivateProfileFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_PHOTO = 2;
    // tvNom .setText( User.name);
    TextView tv_user_firstname;
    TextView tv_work;
    CircleImageView cim_img_profile;
    private String mCurrentPhotoPath;
    Bitmap selectedImage;
    List<Post> posts;
    private RecyclerView recyclerView;
    User currentUser ;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_user_firstname = (TextView)getView().findViewById(R.id.tv_user_firstName);
        tv_work=(TextView)getView().findViewById(R.id.tv_work);
        cim_img_profile = (CircleImageView)getView().findViewById(R.id.avatar);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        cim_img_profile.setOnClickListener(this);
        tv_user_firstname.setText(currentUser.getFirstName());
        tv_work.setText(currentUser.getLastName());
        if(User.imgprofile!=""){
            Picasso.with(getActivity()).load(currentUser.getImg_profile()).into(cim_img_profile);       }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Log.e("Id User " , " " + currentUser.toString());

        int spacing = MeasUtils.dpToPx(4, getActivity());
        recyclerView.addItemDecoration(new GreedoSpacingItemDecoration(spacing));
        getPosts();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.avatar){

        }
    }



    private void getPosts() {

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.insertPost service = retrofitClient.getRetrofit().create(UserService.insertPost.class);
        Call<PostsResponse> call = service.getPost(currentUser.getId());// TODO change user ID
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {

                if (response.isSuccessful()) {
                    for (Post p : response.body().getPost()
                            ) {
                        p.setImage(RetrofitClient.BASE_URL + p.getImage());
                        Log.e("farhat",p.toString());

                    }
                    posts= response.body().getPost();
                    PhotosAdapter photosAdapter = new PhotosAdapter(getActivity(),posts);
                    recyclerView.setAdapter(photosAdapter);

                    final GreedoLayoutManager layoutManager = new GreedoLayoutManager(photosAdapter);
                    layoutManager.setMaxRowHeight(MeasUtils.dpToPx(150, getActivity()));

                    recyclerView.setLayoutManager(layoutManager);
                    photosAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
                Log.e("Responsoe", response.message());

            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.e("Responsoe", t.getMessage());

            }
        });
    }

}

