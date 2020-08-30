package cinema.app.cinemaapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cinema.app.cinemaapp.R
import cinema.app.cinemaapp.model.Movies
import cinema.app.cinemaapp.model.RecentMovies
import cinema.app.cinemaapp.view.adapter.MoviesSearchAdapter
import cinema.app.cinemaapp.view.adapter.MoviesSearchListener
import cinema.app.cinemaapp.view.adapter.RecentListAdapter
import cinema.app.cinemaapp.viewmodel.MoviesViewModel
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_movies_search.*

class MoviesSearchActivity : AppCompatActivity(), TextWatcher, View.OnClickListener, MoviesSearchListener {
    private var moviesViewModel: MoviesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_search)
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        setListener()
        loadSearchList()
    }

    /*load search list with movies*/
    private fun loadSearchList() {
        val movie_list: ArrayList<Movies> = intent.extras.getSerializable("movie_list") as ArrayList<Movies>
        val searchAdapter = MoviesSearchAdapter(this)
        searchAdapter.moviesSearchListener = this
        searchAdapter.movies = movie_list
        search_list.adapter = searchAdapter
        search_list.layoutManager = LinearLayoutManager(this)
    }

    /*on search item clicked*/
    override fun onSearchItemClicked(movies: Movies?) {
        moviesViewModel?.saveRecentSearch(movies)
        val intent = Intent(this, MoviesDetailActivity::class.java)
        intent.putExtra("movies_id", movies?.id)
        startActivity(intent)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.search_cancel -> {
                search_input.setText("")
            }
        }
    }

    /*calling loadrecentdata in onresume to make it instant refresh*/
    override fun onResume() {
        super.onResume()
        loadRecentData()
    }

    /*override text change event for search cancel*/
    override fun afterTextChanged(editable: Editable?) {
        (search_list?.adapter as MoviesSearchAdapter).filter.filter(editable.toString())
        if (editable.toString().length == 0) {
            if (recent_list.adapter != null && (recent_list.adapter as RecentListAdapter).recentMovies?.size > 0) {
                recent_list.visibility = View.VISIBLE
                recent_title.visibility = View.VISIBLE
                recent_divider.visibility = View.VISIBLE
            }
            search_cancel.visibility = View.GONE
            search_list.visibility = View.GONE
        } else if (search_list.visibility == View.GONE) {
            recent_list.visibility = View.GONE
            recent_title.visibility = View.GONE
            recent_divider.visibility = View.GONE
            search_list.visibility = View.VISIBLE
            search_cancel.visibility = View.VISIBLE
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    /*load recent data from local db*/
    private fun loadRecentData() {
        var realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm ->
            val movies =
                realm.where<RecentMovies>(RecentMovies::class.java).sort("time", Sort.DESCENDING).limit(5).findAll()
            if (movies?.size == 0) {
                recent_list.visibility = View.GONE
                recent_title.visibility = View.GONE
                recent_divider.visibility = View.GONE
            } else {
                if (search_input.text.toString().trim().length == 0) {
                    recent_list.visibility = View.VISIBLE
                    recent_title.visibility = View.VISIBLE
                    recent_divider.visibility = View.VISIBLE
                }
                val adapter = RecentListAdapter(this)
                adapter.recentMovies = movies
                recent_list.adapter = adapter
                recent_list.layoutManager = LinearLayoutManager(this)
            }

        }
    }

    /*setting listeners on ui*/
    private fun setListener() {
        search_input.addTextChangedListener(this)
        search_cancel.setOnClickListener(this)
    }
}
