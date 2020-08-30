package cinema.app.cinemaapp.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import cinema.app.cinemaapp.R
import cinema.app.cinemaapp.model.Movies
import cinema.app.cinemaapp.model.RecentMovies
import cinema.app.cinemaapp.view.activity.MoviesDetailActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.search_item.view.*
import io.realm.RealmResults


class MoviesSearchAdapter(var activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    lateinit var movies: ArrayList<Movies>
    var moviesFiltered: ArrayList<Movies>? = ArrayList()
    lateinit var moviesSearchListener: MoviesSearchListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, null)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesFiltered?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is SearchViewHolder) {
            holder.bind(moviesFiltered!![position], activity, moviesSearchListener)
        }
    }


    /*filtering data for search results*/
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                moviesFiltered = filterResults?.values as ArrayList<Movies>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString().trim()
                if (charString.length < 1) {
                    moviesFiltered = ArrayList()
                } else {
                    var searchStr: String = charSequence.toString()
                    var contentfilter = ArrayList<Movies>()

                    var searchStrArray = searchStr.split(" ")

                    if (searchStrArray.size == 1) {

                        /*search query has only one word condition*/
                        for (row in movies!!) {
                            var movieStrArray = row.title?.split(" ")
                            for (item in movieStrArray!!) {
                                if (item?.toLowerCase().startsWith(searchStrArray[0]?.toLowerCase())) {
                                    /*search query matches with
                                     start of any of the words
                                      of the movie name*/
                                    contentfilter.add(row)
                                    break
                                }
                            }
                        }
                    } else {
                        /*search query has multiple words condition*/
                        for (row in movies!!) {
                            var movieStrArray = row.title?.split(" ")
                            var hashMap = HashMap<String, Any>()
                            var k = 0;
                            for ((i, item) in searchStrArray?.withIndex()) {
                                for ((j, movie) in movieStrArray!!.withIndex()) {
                                    if (movie?.toLowerCase().equals(item?.toLowerCase())
                                        || (i == searchStrArray.size - 1 && movie?.toLowerCase().startsWith(item?.toLowerCase()))
                                    ) {
                                        if (!(hashMap.containsKey(movie) && hashMap.get(movie)!!.equals(j))) {
                                            k++;
                                            //contentfilter.add(row)
                                            hashMap.put(movie, j);
                                            break
                                        }
                                    }

                                }

                            }
                            if (k == searchStrArray.size) {
                                /*all the words in the search
                                string matches with
                                 the words of movie name

                                 or

                                 all the words except last in the search
                                 string matches with words of movie name
                                 and start of the
                                 last word of search string matches
                                  with any other word of movie name*/
                                contentfilter.add(row)
                            }
                        }
                    }
                    moviesFiltered = contentfilter
                }

                val filterResults = FilterResults()
                filterResults.values = moviesFiltered
                return filterResults
            }

        }
    }
}

interface MoviesSearchListener {
    fun onSearchItemClicked(movies: Movies?)
}

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        movies: Movies?,
        activity: Activity,
        moviesSearchListener: MoviesSearchListener
    ) {
        itemView?.search_movie.text = movies?.title

        itemView?.setOnClickListener({ v ->
            moviesSearchListener.onSearchItemClicked(movies)

        })
    }

}
