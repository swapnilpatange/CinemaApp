package cinema.app.cinemaapp.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cinema.app.cinemaapp.R
import cinema.app.cinemaapp.model.Review
import kotlinx.android.synthetic.main.review_list_item.view.*

class ReviewListAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var reviews: List<Review>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, null)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.setLayoutParams(lp)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews?.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReviewViewHolder) {
            holder.bind(reviews[position], activity)
        }
    }

    internal class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: Review, activity: Activity) {
            itemView?.author_name.text = review.author
            itemView?.content.text = review.content
        }
    }
}