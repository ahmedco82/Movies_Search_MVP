package com.ahmedco.movies.api;

import com.ahmedco.movies.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by delaroy on 4/14/17.
 */
public interface Service {
    @GET("movie/popular")
    Call<MoviesResponse>getPopularMovies(@Query("api_key") String apiKey);
    @GET("movie/top_rated")
    Call<MoviesResponse>getTopRatedMovies(@Query("api_key") String apiKey);
}
