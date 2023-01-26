package com.example.newlifekotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newlifekotlin.databinding.UpcomingMovieCardItemBinding
import com.example.newlifekotlin.impl.moviesData.UpcomingMoviesDataImpl
import com.squareup.picasso.Picasso

class UpcomingMovieCardAdapter(
    private var upcomingMoviesData: UpcomingMoviesDataImpl,
    var listener: OnItemClickListener
) :
    RecyclerView.Adapter<UpcomingMovieCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding =
            UpcomingMovieCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = upcomingMoviesData.getMoviesTitle(position)
        holder.filmRelease.text = upcomingMoviesData.getFilmRelease(position)
        Picasso.get()
            .load(upcomingMoviesData.getFilmPoster(position))
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.movieImage);

        holder.movieTitle.setOnClickListener { listener.onTitleClick(position) }
        holder.movieImage.setOnClickListener { listener.onImageClick(position) }
    }

    override fun getItemCount(): Int = upcomingMoviesData.size()

    class ViewHolder(itemBinding: UpcomingMovieCardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var movieTitle: TextView = itemBinding.upcomingMovieTitle
        var filmRelease: TextView = itemBinding.upcomingFilmRelease
        var movieImage: ImageView = itemBinding.upcomingMovieImage
    }

    interface OnItemClickListener {
        fun onTitleClick(position: Int)
        fun onImageClick(position: Int)
    }
}

