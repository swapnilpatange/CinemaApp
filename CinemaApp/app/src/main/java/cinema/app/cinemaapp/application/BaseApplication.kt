package cinema.app.cinemaapp.application

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Realm library initiated
        Realm.init(getApplicationContext())


        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(realmConfig)
    }
}