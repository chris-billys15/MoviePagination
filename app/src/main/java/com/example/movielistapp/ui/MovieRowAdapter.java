package com.example.movielistapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistapp.R;
import com.example.movielistapp.data.server.model.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieRowAdapter extends PagedListAdapter<Film, MovieRowAdapter.ViewHolder> {
    private ArrayList<Film> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    //data is passed into the constructor

    protected MovieRowAdapter(Context context, ArrayList<Film> data) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.film_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Film film  = mData.get(position);
        holder.filmTitle.setText(film.title);
        holder.filmVote.setText(film.voteAverage);
        holder.filmReleaseDate.setText(film.releaseDate);
        Picasso.get()
                .load(getImagePath(film.posterPath))
                .into(holder.filmPoster);
    }

    public String getImagePath(String path) {
        return "https://image.tmdb.org/t/p/w500/"+path;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public void updateList(ArrayList<Film> movies) {
        mData = movies;
        notifyDataSetChanged();
    }

    public void addList(ArrayList<Film> movies) {
        mData.addAll(movies);
        notifyDataSetChanged();
    }
    private static DiffUtil.ItemCallback<Film> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Film>() {
                @Override
                public boolean areItemsTheSame(@NonNull Film oldItem, @NonNull Film newItem) {
                    return oldItem.filmID == newItem.filmID;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Film oldItem, @NonNull Film newItem) {
                    return oldItem.equals(newItem);
                }
            };
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView filmTitle;
        TextView filmVote;
        TextView filmReleaseDate;
        ImageView filmPoster;

        ViewHolder(View itemView) {
            super(itemView);
            filmTitle = itemView.findViewById(R.id.movie_name);
            filmVote = itemView.findViewById(R.id.vote_average);
            filmReleaseDate = itemView.findViewById(R.id.release_date);
            filmPoster = itemView.findViewById(R.id.poster_path);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
                //pindah ke detail
                Toast.makeText(context, filmTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}