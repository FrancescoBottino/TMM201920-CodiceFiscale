package it.uniparthenope.studenti.francescobottino001.codicefiscale.room

import android.content.Context
import androidx.room.Room
import it.uniparthenope.studenti.francescobottino001.codicefiscale.utils.SingletonHolder

class DatabaseRepo private constructor(context: Context) {
    companion object : SingletonHolder<DatabaseRepo, Context>(::DatabaseRepo) {
        private val DB_NAME = "nomi_comuni_italiani.db"
    }

    private var database: DatabaseComuni

    init {
        database = Room.databaseBuilder<DatabaseComuni>(
            context,
            DatabaseComuni::class.java,
            DB_NAME
        )
        .createFromAsset("nomi_comuni_italiani.db")
        .build()
    }

    fun getComuniSearch(comune: String): List<Comune> {
        return database.ComuneDAO().getComuniSearch("$comune%")
    }

    fun getComuni(): List<Comune> {
        return database.ComuneDAO().getComuni()
    }
}