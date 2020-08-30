package cinema.app.cinemaapp.model
import cinema.app.cinemaapp.model.Genre
import java.io.Serializable

open class Movies(
    var release_date: String? = null,
    var poster_path: String? = null,
    var title: String? = null,
    var id: Long? = null,
    var overview: String? = null,
    var backdrop_path: String? = null,
    var genres: List<Genre>? = null
):Serializable