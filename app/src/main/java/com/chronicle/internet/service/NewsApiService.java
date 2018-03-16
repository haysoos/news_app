package com.chronicle.internet.service;

import com.chronicle.internet.models.Headlines;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsApiService {

    @Headers("X-Api-Key: <NEWS_API_KEY>")
    @GET("v2/top-headlines?pageSize=100")
    Call<Headlines> headlines(@Query("country") String country, @Nullable @Query("category") String category);
}
