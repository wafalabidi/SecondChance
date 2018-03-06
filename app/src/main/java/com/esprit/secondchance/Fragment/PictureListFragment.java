package com.esprit.secondchance.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Adapters.PhotosAdapter;
import com.esprit.secondchance.Entities.ConfirmationResponse;
import com.esprit.secondchance.Entities.Post;
import com.esprit.secondchance.Entities.Response.PostsResponse;
import com.esprit.secondchance.Entities.User;
import com.esprit.secondchance.MeasUtils;
import com.esprit.secondchance.R;
import com.esprit.secondchance.RecyclerViewClickListener;
import com.esprit.secondchance.RecyclerViewTouchListener;
import com.esprit.secondchance.UserProfileActivity;
import com.esprit.secondchance.Utils.LocalFiles;
import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;

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
    private User currentUser;
    private PhotosAdapter photosAdapter;
    private ViewPostFragment viewPostFragment;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        int spacing = MeasUtils.dpToPx(2, getActivity());
        recyclerView.addItemDecoration(new GreedoSpacingItemDecoration(spacing));
        LocalFiles localFiles = new LocalFiles(getActivity().getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        if (currentUser.getId() == localFiles.getInt(LocalFiles.Id))
            SetUpRecyclerView();
        else
            SetUpRecycler();
        getPosts();
        viewPostFragment = new ViewPostFragment();
    }

    private void SetUpRecycler() {
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(),
                recyclerView,
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        viewPostFragment.setP(photosAdapter.getItem(position));
                        ((UserProfileActivity) getActivity()).commitFragment(viewPostFragment);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    private void SetUpRecyclerView() {

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(),
                recyclerView,
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        viewPostFragment.setP(photosAdapter.getItem(position));
                        ((UserProfileActivity) getActivity()).commitFragment(viewPostFragment);

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        showDialog(position);
                    }
                }));
    }

    private void showDialog(int position) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.confirm_delete_dialog);
        LinearLayout cancel = dialog.findViewById(R.id.cancel_button);
        LinearLayout confirm = dialog.findViewById(R.id.confirm_button);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        confirm.setOnClickListener(v -> {
            long post = recyclerView.getAdapter().getItemId(position);
            deletePost(dialog, post, position);

        });
        dialog.show();
    }

    private void deletePost(Dialog dialog, long post, int position) {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.deletePost deletePost = retrofitClient.getRetrofit().create(UserService.deletePost.class);
        Call<ConfirmationResponse> call = deletePost.deletePost((int) post);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Post deleted with succes", Toast.LENGTH_SHORT).show();
                ((PhotosAdapter) recyclerView.getAdapter()).removeItem(position);
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Unable to reach server please retry later", Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage());

            }
        });
    }

    private void getPosts() {

        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.insertPost service = retrofitClient.getRetrofit().create(UserService.insertPost.class);

        Call<PostsResponse> call = service.getPost(currentUser.getId());
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {

                if (response.isSuccessful() && response.body().getPost() != null) {
                    for (Post p : response.body().getPost()
                            ) {
                        if (!p.getImage().contains(RetrofitClient.BASE_URL))
                            p.setImage(RetrofitClient.BASE_URL + p.getImage());

                    }
                    posts = response.body().getPost();
                    photosAdapter = new PhotosAdapter(getActivity(), posts);
                    recyclerView.setAdapter(photosAdapter);

                    final GreedoLayoutManager layoutManager = new GreedoLayoutManager(photosAdapter);
                    layoutManager.setMaxRowHeight(MeasUtils.dpToPx(150, getActivity()));
                    recyclerView.setLayoutManager(layoutManager);
                    photosAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.e("Response", t.getMessage());

            }
        });
    }


}
