package it.uniparthenope.studenti.francescobottino001.codicefiscale

import android.util.Log
import it.uniparthenope.studenti.francescobottino001.codicefiscale.utils.TAG
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class CodiceFiscaleQuery {

    companion object {
        private const val NAMESPACE = "http://webservices.dotnethell.it/CodiceFiscale"
        private const val METHOD_NAME = "CalcolaCodiceFiscale"
        private const val SOAP_ACTION = "$NAMESPACE/$METHOD_NAME"
        private const val URL = "http://webservices.dotnethell.it/CodiceFiscale.asmx?WSDL"

        fun calcolaCodiceFiscale(
            nome: String,
            cognome: String,
            comune: String,
            data: String,
            sesso: String
        ): String {

            val request = SoapObject(NAMESPACE, METHOD_NAME)
            request.addProperty("Nome", nome)
            request.addProperty("Cognome", cognome)
            request.addProperty("ComuneNascita", comune)
            request.addProperty("DataNascita", data)
            request.addProperty("Sesso", sesso)

            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER12)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)

            val httpTransport = HttpTransportSE(URL)
            try {
                httpTransport.call(SOAP_ACTION, envelope)
                return envelope.response.toString()
            } catch (e: Exception) {
                Log.e("$TAG, HTTP_ERROR", e.toString())
            }
            return "error"
        }
    }
}