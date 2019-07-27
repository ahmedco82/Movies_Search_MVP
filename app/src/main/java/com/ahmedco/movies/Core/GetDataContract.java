package com.ahmedco.movies.Core;


import android.content.Context;

import com.ahmedco.movies.model.Movie;

import java.util.List;

public interface GetDataContract {

    interface View{
        void onGetDataSuccess(String message,List<Movie> list);
        void onGetDataFailure(String message);
        void showProgress();
        void hideProgress();
    }

    interface Presenter{
        void getDataFromRetrofit(Context context);
        void getDataFromJSON(Context context,String Search);
    }

    interface Interactor{
        void initRetrofitCall(Context context);
        void initJSONCall(Context context,String Search);
    }

    interface onGetDataListener{
        void onSuccess(String message,List<Movie>list);
        void onFailure(String message);
    }

}
