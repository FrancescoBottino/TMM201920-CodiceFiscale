package it.uniparthenope.studenti.francescobottino001.codicefiscale.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comuni")
class Comune {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var nome: String = ""

    override fun toString(): String {
        return nome
    }
}