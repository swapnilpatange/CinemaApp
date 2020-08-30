package cinema.app.cinemaapp.data.repository

import cinema.app.cinemaapp.data.retrofit.RetrofitService
import cinema.app.cinemaapp.model.*
import io.realm.Realm
import io.realm.RealmResults

class MoviesRepository {
    val retrofitService = RetrofitService()
    suspend fun getNowPlaying(apiKey: String): MoviesApiResponse? {
        return retrofitService.getNowPlaying(apiKey)
    }

    suspend fun getSimilar(apiKey: String, movieId: String): MoviesApiResponse? {
        return retrofitService.getSimilar(apiKey, movieId)
    }

    suspend fun getCredits(apiKey: String, movieId: String): CreditApiResponse? {
        return retrofitService.getCredits(apiKey, movieId)
    }

    suspend fun getSynopsis(apiKey: String, movieId: String): Movies? {
        return retrofitService.getSynopsis(apiKey, movieId)
    }

    suspend fun getReviews(apiKey: String, movieId: String): ReviewApiResponse? {
        return retrofitService.getReviews(apiKey, movieId)
    }

    fun saveRecentSearch(movies: Movies?) {
        var recentMovies = RecentMovies(movies?.id, movies?.title, System.currentTimeMillis())
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val rowQuery: RealmResults<RecentMovies> =
                realm.where<RecentMovies>(RecentMovies::class.java).equalTo("id", movies?.id).findAll()
            rowQuery.deleteAllFromRealm()
            realm.copyToRealm(recentMovies)

        }
    }


}