package com.labidi.wafa.secondchance.Fragment;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.RvHomeAdapter;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.HomeActivity;
import com.labidi.wafa.secondchance.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sofien on 20/11/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener  {
    @BindView(R.id.rvHome)
    RecyclerView rcHome;
    RvHomeAdapter rvHomeAdapter;
    Button bGallery;
    private final int REQUEST_PHOTO = 200;
    Bitmap selectedImage;
    ImageView ivNewPost;
    private final Post post = new Post();
    EditText etPostSaying;
    RelativeLayout expandableLayout;
    Button bShare;
    TextView tvPost;
    boolean isExpanded = false;
    LinearLayout.LayoutParams expanded;
    LinearLayout.LayoutParams collapsed;
    LinearLayout homRacine;
    List<Post> list;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    Button bCamera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflater == null) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View view = inflater.inflate(R.layout.home_fragment, null);
        ButterKnife.bind(this, view);
        InitWidget(view);
        getPosts();

        return view;
    }

    private void InitWidget(View view) {
        ((HomeActivity)getActivity()).getFabCamera().setOnClickListener(this);
        ((HomeActivity)getActivity()).getFabGallery().setOnClickListener(this);



        bCamera = view.findViewById(R.id.bCamera);
        bCamera.setOnClickListener(this);
        list = new ArrayList<>();
        Post post = new Post();
        post.setImage("http://192.168.1.102:8888/DivroceBook/Images/Beta1511622394033.jpg");
        post.setSaying("Static Saying for testing purpuse");
        rvHomeAdapter = new RvHomeAdapter(getActivity(), list);
        rcHome = view.findViewById(R.id.rvHome);
        bGallery = (Button) view.findViewById(R.id.bGallery);
        bGallery.setOnClickListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcHome.setLayoutManager(layoutManager);
        rcHome.setAdapter(rvHomeAdapter);
        rcHome.setNestedScrollingEnabled(false);
        ivNewPost = (ImageView) view.findViewById(R.id.ivNewPost);
        etPostSaying = (EditText) view.findViewById(R.id.etPostSaying);
        bShare = (Button) view.findViewById(R.id.bShare);
        bShare.setOnClickListener(this);
        expandableLayout = view.findViewById(R.id.expandableLayout);
        tvPost = view.findViewById(R.id.tvPost);
        tvPost.setOnClickListener(this);
        expanded = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        collapsed = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        homRacine = view.findViewById(R.id.homRacine);
        LayoutTransition transition = homRacine.getLayoutTransition();
        transition.enableTransitionType(LayoutTransition.CHANGING);
        expandableLayout.setLayoutParams(collapsed);
        bShare.setEnabled(false);
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
                ivNewPost.setImageBitmap(selectedImage);
                post.convertImageToString(selectedImage);
                bShare.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE  ) {
            Log.e("Image" , mCurrentPhotoPath) ;
            selectedImage = BitmapFactory.decodeFile(mCurrentPhotoPath);
            ivNewPost.setImageBitmap(selectedImage);
            bShare.setEnabled(true);

            //galleryAddPic();
            //setPic();
        }
        //Log.e("Image" , mCurrentPhotoPath) ;

    }

    private void setPic() {

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ivNewPost.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.menu_gallery) {
            openGallery();
        } else if (view.getId() == R.id.bShare) {
            uploadPicture();
            Log.e("CheckPoint ", "1");
        } else if (view.getId() == R.id.tvPost) {
            if (isExpanded) {
                tvPost.setBackground(getActivity().getDrawable(R.drawable.gradient_1));
                expandableLayout.setLayoutParams(collapsed);
                isExpanded = !isExpanded;
            } else {
                tvPost.setBackground(getActivity().getDrawable(R.drawable.gradient_3));
                isExpanded = !isExpanded;
                expandableLayout.setLayoutParams(expanded);

            }
        } else if (view.getId() == R.id.menu_camera) {
            dispatchTakePictureIntent();
        }
    }

    private void uploadPicture() {
        post.convertImageToString(selectedImage);
        post.setSaying(etPostSaying.getText().toString());
        post.setIdUser(User.Id);
        post.setTitle("Beta" + System.currentTimeMillis());

        Log.e("CheckPoint ", post.getTitle() + "/n" + post.getSaying());

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.insertPost service = retrofitClient.getRetrofit().create(UserService.insertPost.class);
        Call<ResponseBody> call = service.insertPost(post.getSaying(), post.getImage(), post.getIdUser(), post.getTitle());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

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
                    }

                    rvHomeAdapter.setItems(response.body().getPost());
                    rvHomeAdapter.notifyDataSetChanged();
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

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        ivNewPost.setImageURI(Uri.parse(mCurrentPhotoPath));
        getActivity().sendBroadcast(mediaScanIntent);

    }

}
