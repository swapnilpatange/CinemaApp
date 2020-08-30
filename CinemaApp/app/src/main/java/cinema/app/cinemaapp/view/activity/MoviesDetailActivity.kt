package cinema.app.cinemaapp.view.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cinema.app.cinemaapp.*
import cinema.app.cinemaapp.model.CreditApiResponse
import cinema.app.cinemaapp.model.Movies
import cinema.app.cinemaapp.model.Review
import cinema.app.cinemaapp.view.adapter.CreditListAdapter
import cinema.app.cinemaapp.view.adapter.GenreAdapter
import cinema.app.cinemaapp.view.adapter.MoviesListAdapter
import cinema.app.cinemaapp.view.adapter.ReviewListAdapter
import cinema.app.cinemaapp.view.decorator.GridSpacingDecoration
import cinema.app.cinemaapp.view.decorator.LinearSpacingDecoration
import cinema.app.cinemaapp.viewmodel.MoviesViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movies_detail.*
import kotlinx.android.synthetic.main.content_movies_detail.*

class MoviesDetailActivity : BaseActivity(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)
        setListener()
        val moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        if (isNetworkConnected()) {
            getSynopsisData(moviesViewModel)
            getCreditsData(moviesViewModel)
            getSimilarData(moviesViewModel)
            getReviewData(moviesViewModel)
        } else {
            showToast("No Internet Connection")
        }
    }


    /*setting listeners to view*/
    private fun setListener() {
        app_bar.addOnOffsetChangedListener(this)
        image_back.setOnClickListener(this)
    }

    /*overriding offsetchange for header title animation*/
    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (Math.abs(verticalOffset) - appBarLayout?.getTotalScrollRange() == 0) {
            movie_toolbar_title.visibility = View.VISIBLE
        } else if (movie_toolbar_title.visibility == View.VISIBLE) {
            movie_toolbar_title.visibility = View.GONE
        }
    }

    /*overriding onclick eventlistener*/
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.image_back ->
                onBackPressed()
        }
    }


    /*synopsis repository called*/
    private fun getSynopsisData(moviesViewModel: MoviesViewModel) {
        moviesViewModel.getSynopsis(
            resources.getString(R.string.api_key),
            intent.getLongExtra("movies_id", 0).toString()
        )
            ?.observe(this, Observer { t ->
                loadSynopsisData(t)
            })
    }

    /*load synopsis data on ui*/
    private fun loadSynopsisData(movies: Movies) {
        overview.visibility = View.VISIBLE
        movie_title.visibility = View.VISIBLE
        synopsis_divider.visibility = View.VISIBLE
        genre_list.visibility = View.VISIBLE
        image_calendar.visibility = View.VISIBLE
        release_date.visibility = View.VISIBLE
        release_date.text = movies.release_date
        Glide.with(this)
            .load(resources.getString(R.string.image_base_url) + movies.backdrop_path)
            .apply(RequestOptions().placeholder(R.color.placeholder_color).error(R.color.placeholder_color))
            .into(poster_image)

        movie_title.text = movies.title
        movie_toolbar_title.text = movies.title
        var genreAdapter = GenreAdapter(this)
        genreAdapter.genres = movies.genres!!
        genre_list.adapter = genreAdapter
        genre_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        genre_list.addItemDecoration(GridSpacingDecoration(dpToPx(5)))
        overview.text = movies.overview
    }

    /*credit(cast & crew) repository called*/
    private fun getCreditsData(moviesViewModel: MoviesViewModel) {

        moviesViewModel.getCredits(
            resources.getString(R.string.api_key),
            intent.getLongExtra("movies_id", 0).toString()
        )?.observe(this,
            Observer { t ->
                loadCredits(t)
            })
    }

    /*credits data loaded on ui*/
    private fun loadCredits(creditApiResponse: CreditApiResponse) {
        credit_divider.visibility = View.VISIBLE
        //loading cast
        cast_title.visibility = View.VISIBLE
        cast_list.visibility = View.VISIBLE
        val castListAdapter = CreditListAdapter(this)
        castListAdapter.cast = creditApiResponse?.cast!!
        cast_list.adapter = castListAdapter
        cast_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        cast_list.addItemDecoration(GridSpacingDecoration(dpToPx(10)))

        cast_list.isNestedScrollingEnabled = false
        //loading crew
        crew_title.visibility = View.VISIBLE
        crew_list.visibility = View.VISIBLE
        val crewListAdapter = CreditListAdapter(this)
        crewListAdapter.cast = creditApiResponse?.crew!!
        crew_list.adapter = crewListAdapter
        crew_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        crew_list.addItemDecoration(GridSpacingDecoration(dpToPx(10)))
        crew_list.isNestedScrollingEnabled = false
    }

    /*similar data repository called*/
    private fun getSimilarData(moviesViewModel: MoviesViewModel) {
        moviesViewModel.getSimilar(
            resources.getString(R.string.api_key),
            intent.getLongExtra("movies_id", 0).toString()
        )?.observe(this, Observer { t ->
            loadSimilarData(t?.results)
        })
    }

    /*similar data loaded on ui*/
    private fun loadSimilarData(results: ArrayList<Movies>?) {
        if (results?.size!! > 0) {
            similar_title.visibility = View.VISIBLE
            similar_list.visibility = View.VISIBLE
            var moviesListAdapter = MoviesListAdapter(this)
            moviesListAdapter.movies = results!!
            similar_list.adapter = moviesListAdapter
            similar_list.layoutManager = GridLayoutManager(this, 3)
            similar_list.addItemDecoration(GridSpacingDecoration(dpToPx(5)))
        }
    }

    /*review repository called*/
    private fun getReviewData(moviesViewModel: MoviesViewModel) {
        moviesViewModel.getReviews(
            resources.getString(R.string.api_key),
            intent.getLongExtra("movies_id", 0).toString()
        )?.observe(this, Observer { t ->
            loadReviewData(t?.results)
        })
    }


    /*load reviews on ui*/
    private fun loadReviewData(results: List<Review>?) {
        if (results?.size!! > 0) {
            review_divider.visibility = View.VISIBLE
            review_list.visibility = View.VISIBLE
            review_title.visibility = View.VISIBLE
            val reviewListAdapter = ReviewListAdapter(this)
            reviewListAdapter.reviews = results!!
            review_list.adapter = reviewListAdapter
            review_list.layoutManager = LinearLayoutManager(this)
            review_list.addItemDecoration(
                LinearSpacingDecoration(
                    dpToPx(10),
                    results?.size
                )
            )
        }
    }


}
