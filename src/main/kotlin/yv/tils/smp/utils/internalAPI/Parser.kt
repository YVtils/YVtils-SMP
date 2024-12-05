package yv.tils.smp.utils.internalAPI

import yv.tils.smp.utils.configs.language.LangStrings
import java.util.*

class Parser {
    data class ReturnData(
        var answer: Any,
        var error: LangStrings?
    )

    /**
     * Parse time from string to calendar
     * @param unit String of time unit
     * @param duration Int of time duration
     * @return ReturnData of parsed time
     * @see ReturnData
     */
    fun parseTime(unit: String, duration: Int): ReturnData {
        val expireAfter: Calendar = Calendar.getInstance()
        when (unit) {
            "s" -> expireAfter.add(Calendar.SECOND, duration)
            "m" -> expireAfter.add(Calendar.MINUTE, duration)
            "h" -> expireAfter.add(Calendar.HOUR, duration)
            "d" -> expireAfter.add(Calendar.DAY_OF_MONTH, duration)
            "w" -> expireAfter.add(Calendar.WEEK_OF_YEAR, duration)
            else -> {
                return ReturnData(0, LangStrings.UNKNOWN_TIME_FORMAT)
            }
        }

        return ReturnData(expireAfter.time, null)
    }
}