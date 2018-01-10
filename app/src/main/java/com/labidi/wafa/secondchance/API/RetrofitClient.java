package com.labidi.wafa.secondchance.API;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wafa on 19/11/2017.
 */

public class RetrofitClient extends AppCompatActivity {

    //  private static final String BASE_URL ="http://172.16.153.116:8888/DivroceBook/";public static final String BASE_URL ="http://172.19.6.94:8888/DivroceBook/";
    //public static final String BASE_URL ="http://192.168.1.3:8888/DivroceBook/";
    //public static final String BASE_URL = "http://172.16.4.4:8888/DivroceBook/";
    public static final String BASE_URL ="http://172.16.153.116:8888/DivroceBook/";
    protected Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
