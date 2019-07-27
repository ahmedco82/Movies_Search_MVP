package com.ahmedco.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmedco.movies.Core.GetDataContract;
import com.ahmedco.movies.Core.Presenter;
import com.ahmedco.movies.R;
import com.ahmedco.movies.adapter.MovieRecyclerViewAdapter;
import com.ahmedco.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

// import nl.bramdehart.movies.adapters.MovieRecyclerViewAdapter;
// import nl.bramdehart.movies.data.NetworkUtils;
// import nl.bramdehart.movies.models.Movie;

 /**
 *Search results activity.
 *Used to display movie search results.
 */

public class SearchResultsActivity extends AppCompatActivity implements  GetDataContract.View{

    private String searchQuery;
    private TextView tvErrorMessage;
    private TextView tvResultsTitle;
    private ProgressBar pbLoadingIndicator;
    private RecyclerView rvMovieList;
    private ArrayList<Movie>movies;
    private Presenter mPresenter;
    // private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        // Bind components
        //  context = SearchResultsActivity.this;
        tvErrorMessage = findViewById(R.id.tv_error_message);
        pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        tvResultsTitle = findViewById(R.id.tv_results_title);
        rvMovieList = findViewById(R.id.rv_movie_list);
        rvMovieList.setNestedScrollingEnabled(false);
        // Get received data from previous intent
        Intent intent = getIntent();
        searchQuery = intent.getExtras().getString("SearchQuery");
        // Start search query
        pbLoadingIndicator.setVisibility(View.VISIBLE);
        mPresenter = new Presenter(this);
        mPresenter.getDataFromJSON(this,searchQuery);
    }

    @Override
    public void onGetDataSuccess(String message, List<Movie> list) {
        pbLoadingIndicator.setVisibility(View.INVISIBLE);
        // MovieRecyclerViewAdapter rvAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), movies);
        MovieRecyclerViewAdapter rvAdapter = new MovieRecyclerViewAdapter(this, list);
        rvMovieList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvMovieList.setAdapter(rvAdapter);

    }
    @Override
    public void onGetDataFailure(String message){
        Log.i("DataFailure :", ""+message);
    }

    @Override
    public void showProgress(){


    }

    @Override
    public void hideProgress(){

    }
    /**
     * Populates the recyclerview with the retrieved movies.
     */
     /**
     * Show recycler view.
     */
    private void showRecyclerView(){
        tvErrorMessage.setVisibility(View.INVISIBLE);
        rvMovieList.setVisibility(View.VISIBLE);
        Log.i("showRecyclerView :", "ok1");
    }
     /**
     * Show error message.
     */
    private void showErrorMessage(){
        Log.i("showErrorMessage :", "ok21");
        rvMovieList.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }

























     /**
      * Inner class that takes care of the query task.
      */


/*
    public static class TMDBQueryTask extends AsyncTask<URL,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoadingIndicator.setVisibility(View.VISIBLE);
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
            pbLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showRecyclerView();
                try {
                    parseMovies(s);
                    populateRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }
        }
    }
*/
     /**
      * Parses the movie JSON string and inserts the results as a new Movie object into the Movie array.
      *
      * @param moviesJSONString
      * The JSON string containing the results.
      */

/*
    private void parseMovies(String moviesJSONString) throws JSONException {
        JSONObject resultJSONObject = new JSONObject(moviesJSONString);
        JSONArray moviesJSONArray = resultJSONObject.getJSONArray("results");
        movies = new ArrayList<Movie>();
        // Loop through json array
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
        tvResultsTitle.setText(movies.size() + " "+getResources().getString(R.string.results_heading) + " '" + searchQuery + "'");
    }
*/

/*
         private void parseMovies(String moviesJSONString) throws JSONException {
             JSONObject resultJSONObject = new JSONObject(moviesJSONString);
             JSONArray moviesJSONArray = resultJSONObject.getJSONArray("results");
             movies = new ArrayList<Movie>();
             // Loop through json array
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
             // tvResultsTitle.setText(movies.size() + " "+getResources().getString(R.string.results_heading) + " '" + searchQuery + "'");
         }

         */
     }



