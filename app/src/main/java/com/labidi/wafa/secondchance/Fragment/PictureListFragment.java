package com.labidi.wafa.secondchance.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;
import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.PhotosAdapter;
import com.labidi.wafa.secondchance.Entities.Post;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.MeasUtils;
import com.labidi.wafa.secondchance.R;
import com.labidi.wafa.secondchance.RecyclerViewClickListener;
import com.labidi.wafa.secondchance.RecyclerViewTouchListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

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
        int spacing = MeasUtils.dpToPx(2, getActivity());
        recyclerView.addItemDecoration(new GreedoSpacingItemDecoration(spacing));
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                ViewPostFragment newFragment = new ViewPostFragment();
                newFragment.imageUrl =posts.get(position).getImage();
                newFragment.caption = posts.get(position).getSaying();
                newFragment.post = posts.get(position);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

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
