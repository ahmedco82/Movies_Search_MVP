package com.ahmedco.movies.Core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ahmedco.movies.BuildConfig;
import com.ahmedco.movies.NetworkUtils;
import com.ahmedco.movies.R;
import com.ahmedco.movies.api.Client;
import com.ahmedco.movies.api.Service;
import com.ahmedco.movies.model.Movie;
import com.ahmedco.movies.model.MoviesResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Intractor implements GetDataContract.Interactor{
    private ArrayList<Movie> movies;
    private GetDataContract.onGetDataListener mOnGetDatalistener;
    private GetDataContract.View view;


    public Intractor(GetDataContract.onGetDataListener mOnGetDatalistener){
        this.mOnGetDatalistener = mOnGetDatalistener;
    }


    @Override
    public void initRetrofitCall(Context context) {
        checkSortOrder(context);
    }


    @Override
    public void initJSONCall(Context context, String Search) {
        URL TMDBSearchURL = NetworkUtils.buildSearchUrl(Search);
        new Intractor.TMDBQueryTask().execute(TMDBSearchURL);
    }

    public class TMDBQueryTask extends AsyncTask<URL,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pbLoadingIndicator.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String TMDBSearchResults = null;
            try {
                TMDBSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return TMDBSearchResults;
        }
        //Executes when the API call is finished.
        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    parseMovies(s);
                    // showRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    mOnGetDatalistener.onFailure(e.getMessage());
              }
            } else {
                //showErrorMessage();
            }
        }
    }

    private void parseMovies(String moviesJSONString) throws JSONException {
        JSONObject resultJSONObject = new JSONObject(moviesJSONString);
        JSONArray moviesJSONArray = resultJSONObject.getJSONArray("results");
        movies = new ArrayList<Movie>();
        //Loop through json array ..................................................
        for (int i = 0; i < moviesJSONArray.length(); i++) {
            JSONObject movieJSONObject = new JSONObject(moviesJSONArray.get(i).toString());
            if (!movieJSONObject.isNull("poster_path")) {
                int movieId = movieJSONObject.getInt("id");
                String posterPath = movieJSONObject.getString("poster_path");
                // Add movie object
                movies.add(new Movie(movieId, posterPath));
            }
        }
        // Set result count
      //  Log.i("Search_MoviesIs:",""+movies.size());
        mOnGetDatalistener.onSuccess("List Size:",movies);
       // tvResultsTitle.setText(movies.size() + " "+getResources().getString(R.string.results_heading) + " '" + searchQuery + "'");
    }



    private void checkSortOrder(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrder = preferences.getString(context.getString(R.string.pref_sort_order_key), context.getString(R.string.pref_most_popular));
        if (sortOrder.equals(context.getString(R.string.pref_most_popular))) {
          //  Log.d(LOG_TAG, "Sorting by most popular");
            loadJSON();
        }else{
            //Log.d(LOG_TAG, "Sorting by vote average");
            loadJSON1();
        }
    }

    private void loadJSON(){
        try{
            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                view.hideProgress();
                return;
            }
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>(){
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response){
                    List<Movie> movies = response.body().getResults();
                    mOnGetDatalistener.onSuccess("List Size:",movies);
                }

                @Override
                public void onFailure(Call<MoviesResponse>call,Throwable t) {
                    mOnGetDatalistener.onFailure(t.getMessage());
                  //    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }

    private void loadJSON1(){
           try{
            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                view.hideProgress();
              return;
            }
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>(){
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                   // Log.i("Call00:", movies.get(0).getTitle());
                    mOnGetDatalistener.onSuccess("List Size:",movies);
                }
                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                   // Log.d("Error", t.getMessage());
                    mOnGetDatalistener.onFailure(t.getMessage());
                    //Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
           // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}