package com.jh.juanhmoviesdb.feature.homeScreen.domain.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jh.data.feature.popularMovies.remote.response.Results
import com.jh.juanhmoviesdb.R
import com.jh.juanhmoviesdb.common.constant.BASE_IMAGE_URL

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesAdapter.MyViewHolder>() {
    private var mList = emptyList<Results>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val moviesData = mList[position]
        holder.moviesTitle.text = moviesData.title
        holder.moviesRate.text = moviesData.voteAverage.toString()

        Glide.with(holder.imagecm.context)
            .load(BASE_IMAGE_URL + moviesData.image)
            .into(holder.imagecm)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setPopularMoviesData(popularMovies: List<Results>) {
        this.mList = popularMovies
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviesTitle: TextView = itemView.findViewById(R.id.txttitle)
        var moviesRate: TextView = itemView.findViewById(R.id.mrate)
        var imagecm: ImageView = itemView.findViewById(R.id.imgimage)
    }
}