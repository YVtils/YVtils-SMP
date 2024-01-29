package yv.tils.smp.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MojangAPI {
    fun name2uuid(playerName: String): UUID? {
        try {
            val url = "https://api.mojang.com/users/profiles/minecraft/${playerName}"
            val map = getWebsite(url)

            map.plus(
                "id" to
                        map["id"]!!.substring(0, 8) + "-" +
                        map["id"]!!.substring(8, 12) + "-" +
                        map["id"]!!.substring(12, 16) + "-" +
                        map["id"]!!.substring(16, 20) + "-" +
                        map["id"]!!.substring(20)
            )

            return UUID.fromString(map["id"])
        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

    fun UUID2Name(uuid: UUID): String? {
        try {
            val url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")
            val map = getWebsite(url)

            map.plus(
                "name" to
                        map["name"]
            )

            return map["name"]
        } catch (e: java.lang.Exception) {
            println(e.message)
        }
        return null
    }

    private fun getWebsite(url: String): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val content = StringBuilder()

            reader.use {
                it.lines().forEach { line ->
                    content.append(line)
                }
            }

            reader.close()
            connection.disconnect()

            val websiteContent = content.toString()
            val list = websiteContent.split(",")

            for (string: String in list) {
                var s = string
                s = s.replace("{", "")
                s = s.replace("}", "")
                s = s.replace("\"", "")
                s = s.replace(" ", "")

                map[s.split(":")[0]] = s.split(":")[1]
            }
        }else {
            println("HTTP request failed with response code: $responseCode")
        }
        return map
    }
}
