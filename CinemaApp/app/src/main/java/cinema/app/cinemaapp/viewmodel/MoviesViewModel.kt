package cinema.app.cinemaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cinema.app.cinemaapp.data.repository.MoviesRepository
import cinema.app.cinemaapp.model.*
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val mutableSimilarLiveData: MutableLiveData<MoviesApiResponse>? = MutableLiveData()
    private val mutableMoviesLiveData: MutableLiveData<MoviesApiResponse>? = MutableLiveData()
    private val mutableCreditsLiveData: MutableLiveData<CreditApiResponse>? = MutableLiveData()
    private val mutableSynopsisLiveData: MutableLiveData<Movies>? = MutableLiveData()
    private val mutableReviewsLiveData: MutableLiveData<ReviewApiResponse>? = MutableLiveData()

    val moviesRepository = MoviesRepository()
    fun getNowPlaying(apiKey: String): MutableLiveData<MoviesApiResponse>? {

        viewModelScope.launch {
            val apiResponse = moviesRepository?.getNowPlaying(apiKey)
            mutableMoviesLiveData?.value = apiResponse
        }
        return mutableMoviesLiveData
    }

    fun getSimilar(apiKey: String, movieId: String): MutableLiveData<MoviesApiResponse>? {

        viewModelScope.launch {
            val apiResponse = moviesRepository?.getSimilar(apiKey, movieId)
            mutableSimilarLiveData?.value = apiResponse
        }
        return mutableSimilarLiveData
    }

    fun getCredits(apiKey: String, movieId: String): MutableLiveData<CreditApiResponse>? {

        viewModelScope.launch {
            val apiResponse = moviesRepository?.getCredits(apiKey, movieId)
            mutableCreditsLiveData?.value = apiResponse
        }
        return mutableCreditsLiveData
    }


    fun getSynopsis(apiKey: String, movieId: String): MutableLiveData<Movies>? {

        viewModelScope.launch {
            val apiResponse = moviesRepository?.getSynopsis(apiKey, movieId)
            mutableSynopsisLiveData?.value = apiResponse
        }
        return mutableSynopsisLiveData
    }

    fun getReviews(apiKey: String, movieId: String): MutableLiveData<ReviewApiResponse>? {

        viewModelScope.launch {
            val apiResponse = moviesRepository?.getReviews(apiKey, movieId)
            mutableReviewsLiveData?.value = apiResponse
        }
        return mutableReviewsLiveData
    }

    fun saveRecentSearch(movies: Movies?) {
        moviesRepository.saveRecentSearch(movies)

    }

}