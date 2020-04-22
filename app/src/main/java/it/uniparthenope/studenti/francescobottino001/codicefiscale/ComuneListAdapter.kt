package it.uniparthenope.studenti.francescobottino001.codicefiscale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import it.uniparthenope.studenti.francescobottino001.codicefiscale.room.Comune
import it.uniparthenope.studenti.francescobottino001.codicefiscale.room.DatabaseRepo


class ComuneListAdapter(mContext: Context, private val itemLayout: Int, private var dataList: List<Comune>? ) :
    ArrayAdapter<Any?>(mContext, itemLayout, dataList!!) {

    private val db = DatabaseRepo.getInstance(mContext)
    private val listFilter = ListFilter()

    override fun getCount(): Int {
        return dataList!!.size
    }

    override fun getItem(position: Int): Comune? {
        return dataList!![position]
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val v: View = view ?: LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)

        val nomeComune = v.findViewById(R.id.nome_comune) as TextView
        nomeComune.text = getItem(position)?.nome ?: "error"

        return v
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    inner class ListFilter : Filter() {
        private val lock = Any()

        override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()
            if (prefix == null || prefix.isEmpty()) {
                synchronized(lock) {
                    results.values = ArrayList<String>()
                    results.count = 0
                }
            } else {
                val searchComune = prefix.toString()

                //DB QUERY
                val matchValues: List<Comune> = db.getComuniSearch(searchComune)
                results.values = matchValues
                results.count = matchValues.size
            }
            return results
        }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults
        ) {
            dataList = results.values as List<Comune>?
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}