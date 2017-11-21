package com.labidi.wafa.secondchance.API;

import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.InscriptionBody;
import com.labidi.wafa.secondchance.Entities.Response.LoginResponse;
import com.labidi.wafa.secondchance.Entities.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Wafa on 19/11/2017.
 */

public interface UserService {
    public interface RegisterInterface {
        @POST("registerUser.php")
        Call<ConfirmationResponse> studentLogin(@Body InscriptionBody inscriptionBody);
    }

    public interface LoginInterface {
        @POST("logIn.php")
        Call<LoginResponse> userLogin(@Body InscriptionBody InscriptionBody);
    }

    public interface updateInfoInterface {
        @POST("updateUser.php")
        Call<ConfirmationResponse> userUpdateInfo(@Body User user);
    }
    @Multipart
    @POST("")
    Call<ResponseBody> uploadPost(
            @Part("description")RequestBody description,
            @Part MultipartBody.Part photo
            );
}
