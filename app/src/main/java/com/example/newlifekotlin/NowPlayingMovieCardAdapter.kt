package com.example.newlifekotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newlifekotlin.databinding.NewMovieCardItemBinding
import com.example.newlifekotlin.impl.moviesData.NowPlayingMoviesDataImpl
import com.squareup.picasso.Picasso

class NowPlayingMovieCardAdapter(
    private var nowMoviesData: NowPlayingMoviesDataImpl,
    var listener: OnItemClickListener
) :
    RecyclerView.Adapter<NowPlayingMovieCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            NewMovieCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = nowMoviesData.getMoviesTitle(position)
        holder.filmRelease.text = nowMoviesData.getFilmRelease(position)
        holder.filmScore.text = nowMoviesData.getFilmScore(position)
        Picasso.get()
            .load(nowMoviesData.getFilmPoster(position))
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.movieImage)

        holder.movieTitle.setOnClickListener { listener.onTitleClick(position) }
        holder.movieImage.setOnClickListener { listener.onImageClick(position) }
    }

    override fun getItemCount(): Int = nowMoviesData.size()

    class ViewHolder(itemBinding: NewMovieCardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var movieTitle: TextView = itemBinding.newMovieTitle
        var filmRelease: TextView = itemBinding.newFilmRelease
        var filmScore: TextView = itemBinding.newFilmScore
        var movieImage: ImageView = itemBinding.newMovieImage

    }

    interface OnItemClickListener {
        fun onTitleClick(position: Int)
        fun onImageClick(position: Int)
    }
}

