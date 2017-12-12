package com.labidi.wafa.secondchance.API;

import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.Entities.Response.DemandesResponse;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.Response.SearchResponse;
import com.labidi.wafa.secondchance.Entities.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Wafa on 19/11/2017.
 */

public interface UserService {
    public interface RegisterInterface {
        @POST("registerUser.php")
        Call<ConfirmationResponse> studentLogin(@Body InscriptionBody inscriptionBody);

        @FormUrlEncoded
        @POST("searchUser.php")
        Call<SearchResponse> searchUser(
                @Field("search") String imageTitle
        );

        @FormUrlEncoded
        @POST("getUserById.php")
        Call<LoginResponse> getUserById(
                @Field("idUser") int idUser
        );

        @FormUrlEncoded
        @POST("AccepterDemande.php")
        Call<ConfirmationResponse> accepterDemande(
                @Field("idUser") int idSender,
                @Field("idUser2") int idReciever
        );

        @FormUrlEncoded
        @POST("RefuserDemande.php")
        Call<ConfirmationResponse> refuserDemande(
                @Field("idUser") int idSender,
                @Field("idUser2") int idReciever
        );


        @FormUrlEncoded
        @POST("EnvoyerDemande.php")
        Call<ConfirmationResponse> sendRequest(
                @Field("idUser") int idSender,
                @Field("idUser2") int idReciever
        );

        @FormUrlEncoded
        @POST("getDemandes.php")
        Call<DemandesResponse> checkInvitation(
                @Field("idUser") int idReciever
        );

        @FormUrlEncoded
        @POST("getDemandesById.php")
        Call<DemandesResponse> checkInvitationById(
                @Field("idUser") int idReciever
        );
    }

    public interface LoginInterface {
        @POST("logIn.php")
        Call<LoginResponse> userLogin(@Body InscriptionBody InscriptionBody);
    }

    public interface updateInfoInterface {
        @POST("updateUser.php")
        Call<ConfirmationResponse> userUpdateInfo(@Body User user);
    }

    public interface insertPost {
        @FormUrlEncoded
        @POST("insertPost.php")
        Call<ResponseBody> insertPost(@Field("description") String description,
                                      @Field("image") String image,
                                      @Field("idUser") int idUser,
                                      @Field("title") String imageTitle
        );

        @GET("getPost.php")
        Call<PostsResponse> getPost(@Query("idUser") int idUser);

        @GET("getAllPost.php")
        Call<PostsResponse> getAllPost(@Query("idUser") int idUser);
    }

    public interface profilepic {
        @FormUrlEncoded
        @POST("profilepic.php")
        Call<ResponseBody> profilepic(@Field("image") String image,
                                      @Field("idUser") int idUser,
                                      @Field("title") String imageTitle
        );


    }

}
