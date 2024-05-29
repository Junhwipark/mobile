package com.example.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile.R;

import java.util.List;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MovieViewHolder> {

    private Context context;
    private List<String> posterUrls;
    private OnMovieSelectedListener listener;

    public interface OnMovieSelectedListener {
        void onMovieSelected(String posterUrl);
    }

    public MoviePosterAdapter(Context context, List<String> posterUrls, OnMovieSelectedListener listener) {
        this.context = context;
        this.posterUrls = posterUrls;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_poster, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String posterUrl = posterUrls.get(position);
        Glide.with(context).load(posterUrl).into(holder.posterImageView);
        holder.posterImageView.setOnClickListener(v -> listener.onMovieSelected(posterUrl));
    }

    @Override
    public int getItemCount() {
        return posterUrls.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
