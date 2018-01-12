package com.labidi.wafa.secondchance.API;

import com.labidi.wafa.secondchance.Entities.Commentaire;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.Entities.LoginBody;
import com.labidi.wafa.secondchance.Entities.Response.CommentaireResponse;
import com.labidi.wafa.secondchance.Entities.Response.DemandesResponse;
import com.labidi.wafa.secondchance.Entities.Response.LikesResponse;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.Entities.Response.PostsResponse;
import com.labidi.wafa.secondchance.Entities.Response.SearchResponse;
import com.labidi.wafa.secondchance.Entities.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Wafa on 19/11/2017.
 */

public interface UserService {
    interface RegisterInterface {
        @POST("test.php")
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
        @POST("checkDemande.php")
        Call<DemandesResponse> checkInvitationById(
                @Field("idUser") int idReciever
        );



        @FormUrlEncoded
        @POST("getFriendsListById.php")
        Call<DemandesResponse> getFriendList(
                @Field("idUser") int idReciever
        );
    }

    interface LoginInterface {
        @POST("logIn.php")
        Call<LoginResponse> userLogin(@Body LoginBody InscriptionBody);
    }

    interface updateInfoInterface {
        @POST("updateUser.php")
        Call<ConfirmationResponse> userUpdateInfo(@Body User user);
    }

    interface insertPost {
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

        @GET("getOnPost.php")
        Call<PostsResponse> getOnPost(@Query("idUser") int idUser);
    }

    interface profilepic {
        @FormUrlEncoded
        @POST("profilepic.php")
        Call<ResponseBody> profilepic(@Field("image") String image,
                                      @Field("idUser") int idUser,
                                      @Field("title") String imageTitle
        );


    }
    interface couverturepic {
        @FormUrlEncoded
        @POST("couverturepic.php")
        Call<ResponseBody> couverturepic(@Field("image") String image,
                                         @Field("idUser") int idUser,
                                         @Field("title") String imageTitle
        );


    }

    interface Commentaire {
        @FormUrlEncoded
        @POST("getCommentaire.php")
        Call<CommentaireResponse> getCommentaire (@Field("idPost") int idPost);
        @POST("insertCommentaire.php")
        Call<ConfirmationResponse>comment(@Body com.labidi.wafa.secondchance.Entities.Commentaire commentaire);
    }
    interface Likes {
        @FormUrlEncoded
        @POST ("getLikes.php")
        Call<LikesResponse> getLikes (@Field("idUser") Integer idUser );
        @FormUrlEncoded
        @POST ("like.php")
        Call<ConfirmationResponse> like (@Field("idUser") Integer idUser , @Field("idPost") Integer idPost);
        @FormUrlEncoded
        @POST ("unlike.php")
        Call<ConfirmationResponse> unlike (@Field("idUser") Integer idUser , @Field("idPost") Integer idPost);


    }

    public interface deletePost {
        @FormUrlEncoded
        @POST("deletePost.php")
        Call<PostsResponse> deletePost(
                @Field("idPost") int idPost);
    }

    public interface editProfile {
        @FormUrlEncoded
        @POST("editProfile.php")
        Call<PostsResponse> deletePost(
                @Field("id") int id);
    }

}
