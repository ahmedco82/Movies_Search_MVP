package com.ahmedco.movies.Core;

import android.content.Context;

import com.ahmedco.movies.model.Movie;

import java.util.List;

public class Presenter implements GetDataContract.Presenter, GetDataContract.onGetDataListener {



    private GetDataContract.View mGetDataView;
    private Intractor mIntractor;


    public Presenter(GetDataContract.View mGetDataView){
        this.mGetDataView = mGetDataView;
        mIntractor = new Intractor(this);
    }

    @Override
    public void getDataFromRetrofit(Context context) {
        mIntractor.initRetrofitCall(context);
    }

    @Override
    public void getDataFromJSON(Context context, String Search) {
        mIntractor.initJSONCall(context,Search);
    }

    @Override
    public void onSuccess(String message, List<Movie> list) {
        mGetDataView.onGetDataSuccess(message,list);

    }
    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }
}
