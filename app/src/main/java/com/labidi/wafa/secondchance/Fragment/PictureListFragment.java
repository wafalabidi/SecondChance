package com.labidi.wafa.secondchance.Fragment;

import android.app.FragmentContainer;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.PhotosAdapter;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.MeasUtils;
import com.labidi.wafa.secondchance.OnItemClickListener;
import com.labidi.wafa.secondchance.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import agency.tango.materialintroscreen.animations.wrappers.NextButtonTranslationWrapper;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 24/12/2017.
 */

public class PictureListFragment extends Fragment {

    List<Post> posts;
    private RecyclerView recyclerView;
    Context mContect;
    PhotosAdapter adaper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_picture_list, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Grid profile
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView .setHasFixedSize(true);
        int spacing = MeasUtils.dpToPx(5, getActivity());
        recyclerView.addItemDecoration(new GreedoSpacingItemDecoration(spacing));
        //adaper = new PhotosAdapter(getActivity(),posts, new OnItemClickListener());
        getPosts();

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
                Log.e("Response", response.message());

            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.e("Response", t.getMessage());

            }
        });
    }


}
