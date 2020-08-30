package cinema.app.cinemaapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import cinema.app.cinemaapp.*
import cinema.app.cinemaapp.model.MoviesApiResponse
import cinema.app.cinemaapp.view.adapter.MoviesListAdapter
import cinema.app.cinemaapp.view.decorator.GridSpacingDecoration
import cinema.app.cinemaapp.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movies.*


class MoviesListActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setListener()
        progressBar.visibility = View.VISIBLE
        val moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        if (isNetworkConnected()) {
            getMoviesList(moviesViewModel)
        } else {
            progressBar.visibility = View.GONE
            no_result.visibility = View.VISIBLE
            movies_list.visibility = View.GONE
            showToast("No Internet Connection")
        }
    }

    /*setting listener on ui*/
    private fun setListener() {
        image_search.setOnClickListener(this)
    }

    /*onclick event*/
    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.image_search -> {
                if (movies_list.adapter != null && (movies_list.adapter as MoviesListAdapter).movies?.size > 0) {
                    var intent = Intent(this, MoviesSearchActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("movie_list", (movies_list.adapter as MoviesListAdapter).movies)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    showToast("Nothing to Search")
                }
            }
        }
    }

    /*movies list repository called*/
    private fun getMoviesList(moviesViewModel: MoviesViewModel) {
        moviesViewModel.getNowPlaying(resources.getString(R.string.api_key))?.observe(this, Observer { t ->
            loadMoviesList(t)
        })
    }


    /*load movies list on ui*/
    private fun loadMoviesList(moviesApiResponse: MoviesApiResponse) {
        progressBar.visibility = View.GONE
        movies_list.visibility = View.VISIBLE
        val moviesListAdapter = MoviesListAdapter(this)
        moviesListAdapter.movies = moviesApiResponse.results!!
        movies_list.adapter = moviesListAdapter
        movies_list.layoutManager = GridLayoutManager(this, 3)
        movies_list.addItemDecoration(GridSpacingDecoration(dpToPx(5)))
    }
}
