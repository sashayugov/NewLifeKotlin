package com.example.newlifekotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newlifekotlin.databinding.MovieNotesListCardItemBinding
import com.example.newlifekotlin.databinding.NewMovieCardItemBinding
import com.example.newlifekotlin.impl.moviesData.RoomMoviesData
import com.example.newlifekotlin.model.MovieCard
import com.squareup.picasso.Picasso

class MovieNotesCardAdapter(
    private var array: Array<MovieCard>,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<MovieNotesCardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieNotesListCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = array[position].title
        holder.filmRelease.text = array[position].year
        holder.filmScore.text = array[position].imdbRating
        Picasso.get()
            .load(array[position].poster?.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.movieImage)

        holder.movieTitle.setOnClickListener { listener.onTitleClick(position) }
        holder.movieImage.setOnClickListener { listener.onImageClick(position) }
    }

    override fun getItemCount(): Int = array.size


    class ViewHolder(itemBinding: MovieNotesListCardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var movieTitle: TextView = itemBinding.noteMovieTitle
        var filmRelease: TextView = itemBinding.noteFilmRelease
        var filmScore: TextView = itemBinding.noteFilmScore
        var movieImage: ImageView = itemBinding.noteMovieImage

    }

    interface OnItemClickListener {
        fun onTitleClick(position: Int)
        fun onImageClick(position: Int)
    }
}

