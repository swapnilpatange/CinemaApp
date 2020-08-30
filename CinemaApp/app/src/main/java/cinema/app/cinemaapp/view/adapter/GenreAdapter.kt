package cinema.app.cinemaapp.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cinema.app.cinemaapp.R
import cinema.app.cinemaapp.model.Genre

class GenreAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var genres: List<Genre>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, null)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genres?.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is GenreViewHolder) {
            holder.bind(genres[position], activity)
        }
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(genre: Genre, activity: Activity) {
            (itemView as TextView).text = genre.name
        }
    }
}