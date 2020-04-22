package it.uniparthenope.studenti.francescobottino001.codicefiscale.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Comune::class], version = 1)
abstract class DatabaseComuni : RoomDatabase() {
    abstract fun ComuneDAO(): ComuneDAO
}