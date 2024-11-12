package yv.tils.smp.utils.updater

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import yv.tils.smp.utils.logger.Debugger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI

class PluginVersion {
    companion object {
        var version = "x.x.x"
        var plVersion = "x.x.x"
    }

    fun onPlayerJoin(player: Player) {
        if (!(Config.config["playerUpdateMessage"] as Boolean)) {
            return
        }

        if ((player.hasPermission("yvtils.smp.update") || player.isOp) && plVersion != version) {
            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(player.uniqueId, LangStrings.PLAYER_PLUGIN_UPDATE_AVAILABLE),
                    listOf("newVersion", "oldVersion", "prefix", "link"),
                    listOf(
                        version,
                        plVersion,
                        Vars().prefix,
                        "<click:open_url:https://yvtils.net/yvtils/modrinth/smp>https://yvtils.net/yvtils/modrinth/smp</click>"
                    )
                )
            )
        }
    }

    fun updateChecker(serverPluginVersion: String) {
        plVersion = serverPluginVersion
        webRequest()

        if (plVersion != version) {
            YVtils.instance.server.consoleSender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.PLUGIN_UPDATE_AVAILABLE),
                    listOf("newversion", "oldversion", "prefix", "link"),
                    listOf(
                        version,
                        plVersion,
                        Vars().prefix,
                        "<click:open_url:https://yvtils.net/yvtils/modrinth/smp>https://yvtils.net/yvtils/modrinth/smp</click>"
                    )
                )
            )
        } else {
            YVtils.instance.server.consoleSender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.PLUGIN_UP_TO_DATE),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
        }
    }

    private fun webRequest() {
        try {
            val url = "https://yvtils.net/yvtils/version/smp.txt"
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

                version = content.toString()
            } else {
                Bukkit.getConsoleSender().sendMessage(
                    Language().directFormat(
                        "There occurred an error while searching for an update! Please contact the support!",
                        "Auf der Suche nach einem Plugin Update ist ein Fehler aufgetreten! Bitte kontaktiere den Support!"
                    )
                )
                Debugger().log(
                    "Update Check Error",
                    "Response Code: $responseCode",
                    "yv.tils.smp.utils.updater.PluginVersion.webRequest()"
                )
            }
        } catch (e: Exception) {
            YVtils.instance.logger.warning("Update Check Error: ${e.message}")
        }
    }

    fun asyncUpdateChecker() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(YVtils.instance, Runnable {
            webRequest()
        }, 0, 3600 * 20)
    }
}