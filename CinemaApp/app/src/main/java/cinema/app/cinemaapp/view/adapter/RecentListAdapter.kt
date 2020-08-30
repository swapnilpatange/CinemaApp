package cinema.app.cinemaapp.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cinema.app.cinemaapp.R
import cinema.app.cinemaapp.model.RecentMovies
import cinema.app.cinemaapp.view.activity.MoviesDetailActivity
import kotlinx.android.synthetic.main.search_item.view.*

class RecentListAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var recentMovies: List<RecentMovies>
    override fun getItemCount(): Int {
        return recentMovies?.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecentViewHolder) {
            holder.bind(recentMovies[position], activity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, null)
        return RecentViewHolder(view)
    }

    class RecentViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun bind(recentMovies: RecentMovies, activity: Activity) {
            itemView?.search_movie.text = recentMovies.name
            itemView?.setOnClickListener({
                v ->
                val intent = Intent(activity, MoviesDetailActivity::class.java)
                intent.putExtra("movies_id", recentMovies?.id)
                activity.startActivity(intent)
            })
        }
    }
}