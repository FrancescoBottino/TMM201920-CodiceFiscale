package it.uniparthenope.studenti.francescobottino001.codicefiscale.room

import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class ComuneDAO {
    @Query("SELECT * FROM comuni WHERE nome like :nomeIn")
    abstract fun getComuniSearch(nomeIn: String): List<Comune>

    @Query("SELECT * FROM comuni")
    abstract fun getComuni(): List<Comune>
}
