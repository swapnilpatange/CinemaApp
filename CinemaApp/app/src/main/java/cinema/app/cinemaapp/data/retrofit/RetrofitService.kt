package cinema.app.cinemaapp.data.retrofit

import android.util.Log
import cinema.app.cinemaapp.model.CreditApiResponse
import cinema.app.cinemaapp.model.Movies
import cinema.app.cinemaapp.model.MoviesApiResponse
import cinema.app.cinemaapp.model.ReviewApiResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {


    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            Log.e("retrofit", "create")
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

    suspend fun getNowPlaying(apiKey:String): MoviesApiResponse? {
        val retrofitCall = create().getNowPlaying(apiKey)
        if (retrofitCall.isSuccessful) {
            return retrofitCall?.body()
        }
        return null
    }


    suspend fun getSimilar(apiKey:String,movieId:String): MoviesApiResponse? {
        val retrofitCall = create().getSimilar(movieId,apiKey)
        if (retrofitCall.isSuccessful) {
            return retrofitCall?.body()
        }
        return null
    }

    suspend fun getCredits(apiKey:String,movieId:String): CreditApiResponse? {
        val retrofitCall = create().getCredits(movieId,apiKey)
        if (retrofitCall.isSuccessful) {
            return retrofitCall?.body()
        }
        return null
    }
    suspend fun getSynopsis(apiKey:String,movieId:String): Movies? {
        val retrofitCall = create()
            .getSynopsis(movieId,apiKey)
        if (retrofitCall.isSuccessful) {
            return retrofitCall?.body()
        }
        return null
    }

    suspend fun getReviews(apiKey:String,movieId:String): ReviewApiResponse? {
        val retrofitCall = create().getReviews(movieId,apiKey)
        if (retrofitCall.isSuccessful) {
            return retrofitCall?.body()
        }
        return null
    }

}