package com.jh.juanhmoviesdb.feature.searchMedia.domain.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jh.data.feature.popularMovies.remote.response.Results
import com.jh.juanhmoviesdb.R
import com.jh.juanhmoviesdb.common.constant.BASE_IMAGE_URL
import android.os.Bundle
import android.widget.Button
import com.jh.juanhmoviesdb.common.network.ConnectionUtil

class SearchMoviesAdapter : RecyclerView.Adapter<SearchMoviesAdapter.MyViewHolder>() {
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

        holder.imagecm.rootView.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString("movieTitle", moviesData.title)
            bundle.putString("movieDescription", moviesData.overview)
            bundle.putString("movieImage", moviesData.posterimage)
            bundle.putInt("idMovies", moviesData.id)
            if (ConnectionUtil().isConnected(holder.imagecm.context)) {
                if (moviesData.id != null && moviesData.title != null && moviesData.overview != null && moviesData.posterimage != null) {
                    view.findNavController()
                        .navigate(R.id.action_searchMediaFragment_to_detailsFragment, bundle)
                } else {
                    showErrorMovieDialog(holder.imagecm.context)
                }
            } else {
                showConnectionDialog(holder.imagecm.context)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setPopularMoviesData(popularMovies: List<Results>) {
        this.mList = popularMovies
        notifyDataSetChanged()
    }

    fun showErrorMovieDialog(context: Context) {
        val dialog: AlertDialog = AlertDialog.Builder(context)
            .setTitle(R.string.smth_wrong)
            .setNeutralButton(R.string.accept, null)
            .create()

        dialog.setOnShowListener {
            val button: Button =
                (dialog).getButton(AlertDialog.BUTTON_NEUTRAL)
            button.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun showConnectionDialog(context: Context) {
        val dialog: AlertDialog = AlertDialog.Builder(context)
            .setTitle(R.string.no_internet)
            .setNeutralButton(R.string.accept, null)
            .create()

        dialog.setOnShowListener {
            val button: Button =
                (dialog).getButton(AlertDialog.BUTTON_NEUTRAL)
            button.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviesTitle: TextView = itemView.findViewById(R.id.txttitle)
        var moviesRate: TextView = itemView.findViewById(R.id.mrate)
        var imagecm: ImageView = itemView.findViewById(R.id.imgimage)
    }
}