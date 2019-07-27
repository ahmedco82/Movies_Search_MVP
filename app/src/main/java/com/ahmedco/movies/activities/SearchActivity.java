package com.ahmedco.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmedco.movies.Core.GetDataContract;
import com.ahmedco.movies.Core.Presenter;
import com.ahmedco.movies.R;
import com.ahmedco.movies.model.Movie;

import java.util.List;

/**
 * Search activity.
 * Used to make a search call.
 */
public class SearchActivity extends AppCompatActivity implements  GetDataContract.View{

    private EditText etSearchBox;
    private Button btnSearchMovie;
    private Presenter mPresenter;

      /**
      * Submits a search and starts a search result activity.
      */

    private void submitSearch(){
        Log.i("Trace","Yes");
        Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
        String TMDBQuery = etSearchBox.getText().toString();
        //Passing data to the Search Results activity
        intent.putExtra("SearchQuery",TMDBQuery);
        SearchActivity.this.startActivity(intent);
        mPresenter = new Presenter(this);
        mPresenter.getDataFromJSON(this,TMDBQuery);
        //getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Set bottombar active item
        etSearchBox = findViewById(R.id.et_search_box);
        btnSearchMovie = findViewById(R.id.btn_search_movie);
        // Set on click listener on button
        btnSearchMovie.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
              submitSearch();
            }
        });
    }

    @Override
    public void onGetDataSuccess(String message,List<Movie>list){

    }

    @Override
    public void onGetDataFailure(String message){

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
