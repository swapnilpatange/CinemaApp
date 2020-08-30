package cinema.app.cinemaapp.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cinema.app.cinemaapp.model.Cast
import cinema.app.cinemaapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.credit_item.view.*

class CreditListAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var cast: List<Cast>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.credit_item, null)
        return CreditViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cast?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CreditViewHolder) {
            holder.bind(cast[position], activity)
        }
    }
}

class CreditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(cast: Cast, activity: Activity) {
        Glide.with(activity)
            .load(activity.resources.getString(R.string.image_base_url) + cast.profile_path)
            .apply(RequestOptions().circleCrop().placeholder(R.drawable.placeholder_bg).error(
                R.drawable.placeholder_bg
            ))
            .into(itemView?.profile_image)
        itemView?.profile_name.text = cast.name
        cast.character?.let {
            itemView?.profile_detail.text = it
        } ?: run {
            cast.job?.let {
                itemView?.profile_detail.text = it
            } ?: run {
                itemView?.profile_detail.visibility = View.INVISIBLE
            }
        }


    }
}
