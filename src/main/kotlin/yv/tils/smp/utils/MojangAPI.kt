package yv.tils.smp.utils

import yv.tils.smp.YVtils
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.util.*

class MojangAPI {
    fun name2uuid(playerName: String): UUID? {
        try {
            val url = "https://api.mojang.com/users/profiles/minecraft/$playerName"
            val map: MutableMap<String, String> = getWebsite(url)

            map["id"] = map["id"]!!.substring(0, 8) + "-" +
                    map["id"]!!.substring(8, 12) + "-" +
                    map["id"]!!.substring(12, 16) + "-" +
                    map["id"]!!.substring(16, 20) + "-" +
                    map["id"]!!.substring(20)

            return UUID.fromString(map["id"])
        } catch (e: Exception) {
            YVtils.instance.logger.warning(e.message)
        }
        return null
    }

    fun uuid2name(uuid: UUID): String? {
        try {
            val url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")
            val map = getWebsite(url)

            return map["name"]
        } catch (e: Exception) {
            YVtils.instance.logger.warning(e.message)
        }
        return null
    }

    fun getSkinTextures(uuid: UUID? = null, name: String = ""): String? {
        if (uuid == null && name == "") return null

        val uniqueId = uuid ?: name2uuid(name)

        try {
            val url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uniqueId.toString().replace("-", "")
            val map = getWebsite(url)

            var value = map["value"]

            value = value?.replace("]", "")

            val decoded = Base64.getDecoder().decode(value)
            val decodedString = String(decoded)

            val splited = decodedString.split(",")

            var textureURL = ""

            for (s in splited) {
                if (s.contains("textures")) {
                    if (s.contains("SKIN")) {
                        val s1 = s.split("url")[1]
                        textureURL = s1.split("\"")[2]
                    }
                }
            }

            return textureURL
        } catch (e: Exception) {
            YVtils.instance.logger.warning(e.message)
        }
        return null
    }

    private fun getWebsite(url: String): MutableMap<String, String> {
        val map: MutableMap<String, String> = HashMap()

        val connection = URI(url).toURL().openConnection() as HttpURLConnection
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
        } else {
            YVtils.instance.logger.warning("HTTP request failed with response code: $responseCode")
        }
        return map
    }
}
