package com.example.caroleenanwar.movie.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

    private static Retrofit retrofit = null;
    private static String Token="2dcd5337446cbfc860a67095ea8f6e7d";
    private static String BASE="https://api.themoviedb.org/3/";
    private static String IMAGE_BASE="http://image.tmdb.org/t/p/w185/";

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request ;

                    request = chain.request().newBuilder().addHeader("api_key", Token).build();


                return chain.proceed(request);
            }
        }).connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



        return retrofit;
    }

    public static void setToken(String token) {
        Token = token;
    }

    public static String getBASE() {
        return BASE;
    }

    public static String getImageBase() {
        return IMAGE_BASE;
    }

    public static String getToken() {
        return Token;
    }
}