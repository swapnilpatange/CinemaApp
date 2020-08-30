package cinema.app.cinemaapp.data.retrofit

import cinema.app.cinemaapp.model.CreditApiResponse
import cinema.app.cinemaapp.model.Movies
import cinema.app.cinemaapp.model.MoviesApiResponse
import cinema.app.cinemaapp.model.ReviewApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("now_playing")
    suspend fun getNowPlaying(@Query("api_key") api_key: String): Response<MoviesApiResponse>

    @GET("{movie_id}/similar")
    suspend fun getSimilar(@Path("movie_id") movie_id: String, @Query("api_key") api_key: String): Response<MoviesApiResponse>

    @GET("{movie_id}/credits")
    suspend fun getCredits(@Path("movie_id") movie_id: String, @Query("api_key") api_key: String): Response<CreditApiResponse>

    @GET("{movie_id}")
    suspend fun getSynopsis(@Path("movie_id") movie_id: String, @Query("api_key") api_key: String): Response<Movies>

    @GET("{movie_id}/reviews")
    suspend fun getReviews(@Path("movie_id") movie_id: String, @Query("api_key") api_key: String): Response<ReviewApiResponse>
}