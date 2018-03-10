package com.esprit.secondchance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.customfonts.MyEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FirstLoginActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_PHOTO1 =3;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private String mCurrentPhotoPath;

    Bitmap selectedImage;

    MyEditText work;
    MyEditText studies;
    MyEditText country;
    MyEditText description;
    CircleImageView profile;
    private User user;
    LinearLayout getStartedButton;
    LocalFiles localFiles ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login_activity);
        work = findViewById(R.id.et_work);
        studies = findViewById(R.id.et_studies);
        country = findViewById(R.id.et_country);
        description = findViewById(R.id.et_description);
        profile = findViewById(R.id.profilePic);
        profile.setOnClickListener(this);
        getStartedButton = findViewById(R.id.getStart_button);
        getStartedButton.setOnClickListener(this);
        localFiles = new LocalFiles( getSharedPreferences(LocalFiles.USER_FILE , Context.MODE_PRIVATE)) ;

    }
    private void openGallery(int request) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, request);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_PHOTO && data != null) {
            Uri path = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                profile.setImageBitmap(selectedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE  ) {
            Log.e("Image" , mCurrentPhotoPath) ;
            selectedImage = BitmapFactory.decodeFile(mCurrentPhotoPath);
            profile.setImageBitmap(selectedImage);
            //galleryAddPic();
            //setPic();
        }

        if(selectedImage != null && requestCode == REQUEST_PHOTO)
            uploadPicture();


    }

    private void setPic() {

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        profile.setImageBitmap(bitmap);


    }


    private void uploadPicture() {
        LocalFiles localFiles = new LocalFiles(this.getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing ... ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String image = convertImageToString(selectedImage) ;
        String title = localFiles.getString(LocalFiles.FirstName) + User.LastName + System.currentTimeMillis();

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.profilepic service = retrofitClient.getRetrofit().create(UserService.profilepic.class);
        //UserService.
        Call<ResponseBody> call = service.profilepic( image, User.Id,title);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    User.imgprofile = RetrofitClient.BASE_URL+"Images/"+title+".jpg";
                    User.imgprofile.trim();
                    Log.e("url", User.imgprofile);
                } else {
                    Toast.makeText(FirstLoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.pop_up_img_profile, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                openGallery(REQUEST_PHOTO1);
                return true ;
            case R.id.gallerie:
                openGallery(REQUEST_PHOTO);
                return true ;
            default:
                return false;
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.imageView2){
            showPopup(v);

        }
        if(v.getId()== R.id.getStart_button){
            UpdateInfo();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void UpdateInfo() {
        final User user = new User();
        user.setWork(work.getText().toString());
        user.setStudies(studies.getText().toString());
        user.setHobbies(description.getText().toString());
        user.setCountry(country.getText().toString());
        user.setId(localFiles.getInt(LocalFiles.Id));
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.updateInfo updateInfoInterface = retrofitClient.getRetrofit().create(UserService.updateInfo.class);
        Call<ConfirmationResponse> call = updateInfoInterface.updateInfo(user);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FirstLoginActivity.this, "all good ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FirstLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
}
