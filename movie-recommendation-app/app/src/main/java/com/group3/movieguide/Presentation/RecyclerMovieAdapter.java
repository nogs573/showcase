package com.group3.movieguide.Presentation;

import android.view.*;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.group3.movieguide.Object.*;
import com.group3.movieguide.R;

import java.util.List;

public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.ViewHolder>
{
    private List<MovieModel> movieList;
    private RecyclerOnClick listener;

    public RecyclerMovieAdapter(List<MovieModel> movieList, RecyclerOnClick listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    public void setMovieList(List<MovieModel> movieList) { this.movieList = movieList; }

    public List<MovieModel> getMovieList() { return movieList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.movie_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movie = movieList.get(position);

        TextView titleTextView = holder.titleTextView;
        titleTextView.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() { return movieList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movIteMovieTitleText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerOnClick{
        void onClick(View v, int position);
    }
}
