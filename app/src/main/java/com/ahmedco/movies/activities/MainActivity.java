package com.ahmedco.movies.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ahmedco.movies.Core.GetDataContract;
import com.ahmedco.movies.Core.Presenter;
import com.ahmedco.movies.R;
import com.ahmedco.movies.SettingsActivity;
import com.ahmedco.movies.adapter.MoviesAdapter;
import com.ahmedco.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;
// https://github.com/delaroy/Movies/tree/master/app/src/main

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener , GetDataContract.View{

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie>movieList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private AppCompatActivity activity = MainActivity.this;
    public static final String LOG_TAG = MoviesAdapter.class.getName();
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       initViews();
    }

    public Activity getActivity(){
       Context context = this;
        while (context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context=((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(this, movieList);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

         @Override
          public void onRefresh(){
        initViews();
            //Toast.makeText(MainActivity.this, "Movies Refreshed",Toast.LENGTH_SHORT).show();
           }
        });
        mPresenter = new Presenter(this);
        mPresenter.getDataFromRetrofit(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences ,String s){
        mPresenter.getDataFromRetrofit(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (movieList.isEmpty()){
            mPresenter.getDataFromRetrofit(this);
        }else{
            mPresenter.getDataFromRetrofit(this);
        }
    }

      // implements
    @Override
    public void onGetDataSuccess(String message,List<Movie>list){
        recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(),list));
        recyclerView.smoothScrollToPosition(0);
        if (swipeContainer.isRefreshing()){
            swipeContainer.setRefreshing(false);
        }
    }

    // implements ......
    @Override
    public void onGetDataFailure(String message){
        Log.d("Status", message);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress(){
        pd.dismiss();
    }


}

