package com.example.bitapplication.javaexample;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {


    /**
     * The return type is important here
     * The class structure that you've defined in Call<T>
     * should exactly match with your json response
     * If you are not using another api, and using the same as mine
     * then no need to worry, but if you have your own API, make sure
     * you change the return type appropriately
     **/
    @GET("currentprice.json")
    Call<JsonElement> getCash();

}
