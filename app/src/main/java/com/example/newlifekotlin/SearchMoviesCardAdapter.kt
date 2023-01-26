package com.example.newlifekotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newlifekotlin.databinding.SearchMovieCardBinding
import com.example.newlifekotlin.impl.moviesData.SearchMoviesDataImpl
import com.squareup.picasso.Picasso

class SearchMoviesCardAdapter(
    private var searchMoviesData: SearchMoviesDataImpl,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<SearchMoviesCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SearchMovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = searchMoviesData.getMoviesTitle(position)
        holder.filmRelease.text = searchMoviesData.getFilmRelease(position)
        holder.filmScore.text = searchMoviesData.getFilmScore(position)
        Picasso.get()
            .load(searchMoviesData.getFilmPoster(position))
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.movieImage);

        holder.searchMovieCard.setOnClickListener { listener.onItemClick(position) }
    }

    override fun getItemCount(): Int = searchMoviesData.size()

    class ViewHolder(itemBinding: SearchMovieCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var movieTitle: TextView = itemBinding.searchMovieTitle
        var filmRelease: TextView = itemBinding.searchFilmRelease
        var filmScore: TextView = itemBinding.searchFilmScore
        var movieImage: ImageView = itemBinding.searchMovieImage
        var searchMovieCard: CardView = itemBinding.searchCardMovie
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}

