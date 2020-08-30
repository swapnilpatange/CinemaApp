package cinema.app.cinemaapp.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cinema.app.cinemaapp.R
import cinema.app.cinemaapp.model.Movies
import cinema.app.cinemaapp.view.activity.MoviesDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movies_list_item.view.*

class MoviesListAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var movies: ArrayList<Movies>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_list_item, null)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies?.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoviesViewHolder) {
            holder.bind(movies[position], activity)
        }
    }

    internal class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies: Movies, activity: Activity) {
            Glide.with(activity)
                .load(activity.resources.getString(R.string.image_base_url) + movies.poster_path)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)).placeholder(R.color.placeholder_color).error(
                    R.color.placeholder_color
                ))
                .into(itemView?.poster_image)

            itemView?.release_date.text = movies.release_date
            itemView?.movie_name.text = movies.title
            itemView?.setOnClickListener({ t ->
                val intent = Intent(activity, MoviesDetailActivity::class.java)
                intent.putExtra("movies_id", movies.id)
                activity.startActivity(intent)
            })
        }
    }
}