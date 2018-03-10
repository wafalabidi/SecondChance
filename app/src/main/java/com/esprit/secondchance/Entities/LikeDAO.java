package com.esprit.secondchance.Entities;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.Response.LikesResponse;

import retrofit2.Call;

/**
 * Created by sofien on 08/01/2018.
 */

public class LikeDAO {

    public RetrofitClient retrofitClient;

    public LikeDAO() {
        retrofitClient = new RetrofitClient();
    }

    public Call<ConfirmationResponse> Like(Like like) {
        UserService.Likes likes = retrofitClient.getRetrofit().create(UserService.Likes.class);
        Call<ConfirmationResponse> call = likes.like(like.getIdUser(), like.getIdPost());
        return call;

    }
    public Call<ConfirmationResponse> unLike(Like like) {
        UserService.Likes likes = retrofitClient.getRetrofit().create(UserService.Likes.class);
        Call<ConfirmationResponse> call = likes.unlike( like.getIdUser(),like.getIdPost());
        return call;

    }
    public Call<LikesResponse> getLikes(Like like) {
        UserService.Likes likes = retrofitClient.getRetrofit().create(UserService.Likes.class);
        Call<LikesResponse> call = likes.getLikes(like.getIdUser());
        return call;

    }
}
