package cinema.app.cinemaapp.model

import io.realm.RealmObject

open class RecentMovies( var id: Long? = null, var name: String? = null, var time: Long? = null) :
    RealmObject()