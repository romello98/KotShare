package com.example.kotshare.data_access.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicesConfiguration
{
    private static final String BASE_URL = "https://10.0.2.2:5001/";
    private static ServicesConfiguration servicesConfiguration;
    private Token token;
    private Retrofit retrofit;

    private ServicesConfiguration()
    {
        setToken(null);
    }

    private void buildRetrofit()
    {
        OkHttpClient httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(token);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(ServicesConfiguration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
    }

    public void setToken(Token token)
    {
        this.token = token;
        buildRetrofit();
    }

    public static ServicesConfiguration getInstance()
    {
        if(servicesConfiguration == null) servicesConfiguration = new ServicesConfiguration();
        return servicesConfiguration;
    }

    public Retrofit getRetrofit()
    {
        return retrofit;
    }
}
