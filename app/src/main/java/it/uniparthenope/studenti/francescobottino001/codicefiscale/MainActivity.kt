package it.uniparthenope.studenti.francescobottino001.codicefiscale

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import it.uniparthenope.studenti.francescobottino001.codicefiscale.CodiceFiscaleQuery.Companion.calcolaCodiceFiscale
import it.uniparthenope.studenti.francescobottino001.codicefiscale.utils.ConnectivityCheck
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var loading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        campo_data_nascita.setOnClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN)
            val calendar:Calendar = Calendar.getInstance()
            if (campo_data_nascita.text.toString() != "") {
                val currentDate = sdf.parse(campo_data_nascita.text.toString())
                if(currentDate != null) {
                    calendar.time = currentDate
                }
            }

            DatePickerDialog(
                this@MainActivity,
                OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    campo_data_nascita.setText(sdf.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        submit_button.setOnClickListener {
            if (!loading) {
                GlobalScope.launch {
                    if (ConnectivityCheck.hasInternetConnected(this@MainActivity)) {
                        try {
                            campo_codice_fiscale.setText(
                                calcolaCodiceFiscale(
                                    campo_nome.text.toString(),
                                    campo_cognome.text.toString(),
                                    campo_luogo_nascita.text.toString(),
                                    campo_data_nascita.text.toString(),
                                    campo_sesso.selectedItem.toString()
                                )
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(
                                this@MainActivity,
                                "HTTP Request error",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Internet connection required",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        campo_luogo_nascita.setAdapter(ComuneListAdapter(this, R.layout.item_comune, ArrayList()))
    }
}
