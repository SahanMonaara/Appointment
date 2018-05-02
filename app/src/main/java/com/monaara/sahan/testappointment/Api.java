package com.monaara.sahan.testappointment;

import com.monaara.sahan.testappointment.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sahan on 4/29/2018.
 */

public interface Api {
    String BASE_URL =  "http://thesaurus.altervista.org/thesaurus/";
//calling getter method from web service with passed values
    @GET("v1")
    Call<Feed> getData(@Query("word") String word,@Query("key") String key,@Query("language") String language, @Query("output") String output);
}
