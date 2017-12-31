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
    //private BottomNavigationViewEx bottomNavigationView;
    private TextView mBackLabel, mCaption, mUsername, mTimestamp;
    private ImageView mBackArrow, mEllipses, mHeartRed, mHeartWhite, mProfileImage;


    //vars
    Post post;
    private int mActivityNumber = 0;
    private String photoUsername = "";
    private String profilePhotoUrl = "";
    //private UserAccountSettings mUserAccountSettings;

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
        Post post = new Post();
        mCaption.setText(String.valueOf(bundle.getString("")));
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

        setupBottomNavigationView();
        getPhotoDetails();
        //setupWidgets();

        return view;
    }

    private void getPhotoDetails(){


    }

    private void setupWidgets(){
       /* String timestampDiff = getTimestampDifference();
        if(!timestampDiff.equals("0")){
            mTimestamp.setText(timestampDiff + " DAYS AGO");
        }else{
            mTimestamp.setText("TODAY");
        }
        UniversalImageLoader.setImage(mUserAccountSettings.getProfile_photo(), mProfileImage, null, "");
        mUsername.setText(mUserAccountSettings.getUsername());*/
    }

    /**
     * Returns a string representing the number of days ago the post was made
     * @return
     */
    private void getTimestampDifference(){
       /* Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        //final String photoTimestamp = mPhoto.getDate_created();
       try{
            //timestamp = sdf.parse(photoTimestamp);
            //difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference/*/
    }

    /**
     * retrieve the activity number from the incoming bundle from profileActivity interface
     * @return
     */
    private void getActivityNumFromBundle(){
        /*Log.d(TAG, "getActivityNumFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            return bundle.getInt(getString(R.string.activity_number));
        }else{
            return 0;
        }*/
    }

    /**
     * retrieve the photo from the incoming bundle from profileActivity interface
     * @return
     */
    private Post getPhotoFromBundle(){
        Log.d(TAG, "getPhotoFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            return bundle.getParcelable(getString(R.string.photo));
        }else{
            return null;
        }
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
      /*  Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(getActivity(),getActivity() ,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(mActivityNumber);
        menuItem.setChecked(true); */
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
