package com.esprit.secondchance.Fragment;

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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.R;
import com.esprit.secondchance.Utils.LocalFiles;
import com.esprit.secondchance.Utils.NonSwipeableViewPager;
import com.esprit.secondchance.Utils.ProgressDialogAlternative;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sofien on 20/11/2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, View.OnLongClickListener {
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_PHOTO1 = 3;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    TextView tv_user_firstname;
    TextView tv_work;
    CircleImageView cim_img_profile;
    private String mCurrentPhotoPath;
    Bitmap selectedImage;
    KenBurnsView couverture_pic;
    LocalFiles localFiles;
    Button bFollowProfil;
    NonSwipeableViewPager vpProfil;
    private PictureListFragment pictureListFragment;
    private InfoProfileFragment infoProfileFragment;

    TabLayout tabLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        tv_user_firstname = getView().findViewById(R.id.tv_user_firstName);
        tv_work = getView().findViewById(R.id.tv_work);
        cim_img_profile = getView().findViewById(R.id.avatar);
        tv_user_firstname.setText(localFiles.getString(LocalFiles.FirstName));
        tv_work.setText(localFiles.getString(LocalFiles.Work));
        couverture_pic = getView().findViewById(R.id.couverture_pic);
        cim_img_profile.setOnLongClickListener(this);
        couverture_pic.setOnLongClickListener(this);
        bFollowProfil = view.findViewById(R.id.bFollowProfil);
        bFollowProfil.setVisibility(View.GONE);
        vpProfil = view.findViewById(R.id.vpProfil);
        tabLayout = view.findViewById(R.id.tabs);

        if (localFiles.getString(LocalFiles.imgprofile) != "") {
            Picasso.with(getActivity()).load(localFiles.getString(LocalFiles.imgprofile)).into(cim_img_profile);
        } else {
            cim_img_profile.setImageResource(R.drawable.couple);
        }

        if (localFiles.getString(LocalFiles.imgcouverture) != "") {
            Picasso.with(getActivity()).load(localFiles.getString(LocalFiles.imgcouverture)).into(couverture_pic);
        } else {
            couverture_pic.setImageResource(R.drawable.tanit);
        }

        tv_user_firstname.setText(localFiles.getString(LocalFiles.FirstName));
        tv_work.setText(localFiles.getString(LocalFiles.LastName));
        InitPostFragment();

    }

    private void InitPostFragment() {
        infoProfileFragment = new InfoProfileFragment();
        pictureListFragment = new PictureListFragment();
        User user = new User();
        LocalFiles localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        user.setId(localFiles.getInt(LocalFiles.Id));
        pictureListFragment.setCurrentUser(user);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(pictureListFragment, "Posts");
        adapter.addFragment(infoProfileFragment, "Personal Information");
        adapter.addFragment(new FriendsListFragment(), "Friend List");
        vpProfil.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpProfil);


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.avatar) {
            showPopup(v);

        }
        if (v.getId() == R.id.couverture_pic) {
            showPopupCover(v);
        }

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
                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                cim_img_profile.setImageBitmap(selectedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            Log.e("Image", mCurrentPhotoPath);
            selectedImage = BitmapFactory.decodeFile(mCurrentPhotoPath);
            cim_img_profile.setImageBitmap(selectedImage);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_PHOTO1 && data != null) {
            Uri path = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                couverture_pic.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//Log.e("Image" , mCurrentPhotoPath) ;
        if (selectedImage != null && requestCode == REQUEST_PHOTO)
            uploadPicture();
        else if (selectedImage != null && requestCode == REQUEST_PHOTO1)
            uploadPictureCouverture();

    }

    private void setPic() {

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        cim_img_profile.setImageBitmap(bitmap);
        couverture_pic.setImageBitmap(bitmap);


    }


    private void uploadPicture() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Processing ... ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String image = convertImageToString(selectedImage);
        String name = localFiles.getString(LocalFiles.FirstName);
        String lastName = localFiles.getString(LocalFiles.LastName);
        String title = "Couverture_" + name + lastName + System.currentTimeMillis();
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.profilepic service = retrofitClient.getRetrofit().create(UserService.profilepic.class);
        //UserService.
        Call<ResponseBody> call = service.profilepic(image, localFiles.getInt(LocalFiles.Id), title);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                localFiles.insertString(LocalFiles.imgprofile, RetrofitClient.BASE_URL + "Images/" + title + ".jpg");
                Log.e("url", localFiles.getString(LocalFiles.imgprofile));

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("upload Errors", t.getMessage());
                progressDialog.dismiss();

            }
        });

    }

    private void uploadPictureCouverture() {
        ProgressDialogAlternative.ShowDIalog(getActivity());

        String image = convertImageToString(selectedImage);
        String name = localFiles.getString(LocalFiles.FirstName);
        String lastName = localFiles.getString(LocalFiles.LastName);
        String title = "Couverture_" + name + lastName + System.currentTimeMillis();

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.couverturepic service = retrofitClient.getRetrofit().create(UserService.couverturepic.class);

        Call<ResponseBody> call = service.couverturepic(image, localFiles.getInt(LocalFiles.Id), title);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ProgressDialogAlternative.Dissmiss();
                    localFiles.insertString(LocalFiles.imgcouverture, RetrofitClient.BASE_URL + "Images/" + title + ".jpg");
                    Log.e("img couverture", RetrofitClient.BASE_URL + "Images/" + title + ".jpg");
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
                ProgressDialogAlternative.Dissmiss();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                ProgressDialogAlternative.Dissmiss();

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

    private void dispatchTakePictureIntent(int request) {
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

    public void showPopupCover(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.pop_up_img_cover, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        cim_img_profile.setImageURI(Uri.parse(mCurrentPhotoPath));
        couverture_pic.setImageURI(Uri.parse(mCurrentPhotoPath));
        getActivity().sendBroadcast(mediaScanIntent);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                openGallery(REQUEST_PHOTO1);
                return true;
            case R.id.gallerie:
                openGallery(REQUEST_PHOTO);

                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == couverture_pic.getId()) {
            openGallery(REQUEST_PHOTO1);
        } else if (view.getId() == cim_img_profile.getId()) {
            openGallery(REQUEST_PHOTO);
        }
        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
