package com.labidi.wafa.secondchance.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.CustomPagerAdapter;
import com.labidi.wafa.secondchance.Adapters.PhotosAdapter;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.MeasUtils;
import com.labidi.wafa.secondchance.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.labidi.wafa.secondchance.Fragment.HomeFragment.REQUEST_IMAGE_CAPTURE;

/**
 * Created by sofien on 20/11/2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener , PopupMenu.OnMenuItemClickListener {
    private static final int REQUEST_PHOTO = 2;
    // tvNom .setText( User.name);
    TextView tv_user_firstname;
    TextView tv_work;
    CircleImageView cim_img_profile;
    private String mCurrentPhotoPath;
    Bitmap selectedImage;
    List<Post> posts;
    private RecyclerView recyclerView;

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
        tv_user_firstname.setText(User.FirstName);
        tv_work.setText(User.Work);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        cim_img_profile.setOnClickListener(this);
        if(User.imgprofile!=""){
            Picasso.with(getActivity()).load(User.imgprofile).into(cim_img_profile);       }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //Grid profile


        int spacing = MeasUtils.dpToPx(4, getActivity());
        recyclerView.addItemDecoration(new GreedoSpacingItemDecoration(spacing));
        getPosts();


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.avatar){
            showPopup(v);

        }
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_PHOTO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_PHOTO && data != null) {
            Uri path = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                cim_img_profile.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE  ) {
            Log.e("Image" , mCurrentPhotoPath) ;
            selectedImage = BitmapFactory.decodeFile(mCurrentPhotoPath);
            cim_img_profile.setImageBitmap(selectedImage);

            //galleryAddPic();
            //setPic();
        }
        //Log.e("Image" , mCurrentPhotoPath) ;
            if(selectedImage != null)
                uploadPicture();
    }

    private void setPic() {

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        cim_img_profile.setImageBitmap(bitmap);
    }


    private void uploadPicture() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Processing ... ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String image = convertImageToString(selectedImage) ;
        String title = User.FirstName + User.LastName + System.currentTimeMillis();

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.profilepic service = retrofitClient.getRetrofit().create(UserService.profilepic.class);
        Call<ResponseBody> call = service.profilepic( image,User.Id,title);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t != null){
                    Log.e("upload Errors" , t.getMessage());
                }
            }
        });

    }
    public String convertImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.labidi.wafa.secondchance",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.pop_up_img_profile, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        cim_img_profile.setImageURI(Uri.parse(mCurrentPhotoPath));
        getActivity().sendBroadcast(mediaScanIntent);

    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                dispatchTakePictureIntent();
                return true ;
            case R.id.gallerie:
                openGallery();

                return true ;
            default:
                return false;
        }
    }
    private void getPosts() {

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.insertPost service = retrofitClient.getRetrofit().create(UserService.insertPost.class);
        Call<PostsResponse> call = service.getPost(User.Id);// TODO change user ID
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
