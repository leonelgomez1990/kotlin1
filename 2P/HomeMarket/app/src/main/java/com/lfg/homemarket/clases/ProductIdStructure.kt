package com.lfg.homemarket.clases

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class ProductIdStructure {
    companion object {
        private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        private fun getCurrentDateTime(): Date = Calendar.getInstance().time

        private fun stringFromDouble(db : Double, num : Int) : String =
            BigDecimal(db).setScale(num, RoundingMode.HALF_UP).toString()

        fun getFromId(id : String) : String {
            val date = getCurrentDateTime()
            val latitud = stringFromDouble(LocationCoordinates.latitud.toDouble(),4)
            val longitud = stringFromDouble(LocationCoordinates.longitud.toDouble(),4)
            var idStructure = date.toString("yyyyMMdd")
            idStructure = "${id},${idStructure},${latitud},${longitud}"
            return idStructure
        }
    }
}