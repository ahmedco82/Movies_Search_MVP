package com.ahmedco.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmedco.movies.activities.DetailActivity;
import com.ahmedco.movies.R;
import com.ahmedco.movies.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
* Created by delaroy on 4/14/17.
*/

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie>movieList;

    public MoviesAdapter(Context mContext, List<Movie> movieList){
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card, viewGroup, false);
        return new MyViewHolder(view);
    }

    public List<Movie> getMovies(){
        return movieList;
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i){
        viewHolder.title.setText(movieList.get(i).getOriginalTitle());
        String vote = Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);
        //load poster image with picasso
        Picasso.get().load(movieList.get(i).getPosterPath()).placeholder(R.drawable.load).into(viewHolder.thumbnail);
    }


    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            userrating = (TextView) view.findViewById(R.id.userrating);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //on item click
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title", movieList.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                        intent.putExtra("overview", movieList.get(pos).getOverview());
                        intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVoteAverage()));
                        intent.putExtra("release_date", movieList.get(pos).getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}
