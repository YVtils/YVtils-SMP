package yv.tils.smp.utils.updater

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jetbrains.annotations.Debug
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer
import yv.tils.smp.utils.internalAPI.Vars
import yv.tils.smp.utils.logger.Debugger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PluginVersion {

    companion object {
        var version = "x.x.x"
        var plVersion = "x.x.x"
    }

    fun onPlayerJoin(player: Player) {
        if ((player.hasPermission("yvtils.smp.update") || player.isOp) && plVersion != version) {
            player.sendMessage(StringReplacer().listReplacer(
                Language().getMessage(player.uniqueId, LangStrings.PLAYER_PLUGIN_UPDATE_AVAILABLE),
                listOf("%newversion%", "%oldversion%", "%prefix%", "%link%"),
                listOf(version, plVersion, Vars().prefix, "https://yvnetwork.de/yvtils-smp/modrinth")
            ))
        }
    }

    fun updateChecker(serverPluginVersion: String) {
        plVersion = serverPluginVersion
        webRequest()

        if (plVersion != version) {
            YVtils.instance.server.consoleSender.sendMessage(Component.text(StringReplacer().listReplacer(
                Language().getMessage(LangStrings.PLUGIN_UPDATE_AVAILABLE),
                listOf("%newversion%", "%oldversion%", "%prefix%", "%link%"),
                listOf(version, plVersion, Vars().prefix, "https://yvnetwork.de/yvtils-smp/modrinth")
            )))
        }else {
            YVtils.instance.server.consoleSender.sendMessage(Component.text(StringReplacer().listReplacer(
                Language().getMessage(LangStrings.PLUGIN_UP_TO_DATE),
                listOf("%prefix%"),
                listOf(Vars().prefix)
            )))
        }
    }

    private fun webRequest() {
        kotlin.runCatching {
            val url = "https://yvnetwork.de/yvtils/smp_paper.txt"
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String
                val content = StringBuilder()

                while ((reader.readLine().also { line = it }) != null) {
                    content.append(line)
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
                Debugger().log("Response Code: $responseCode")
            }
        }
    }
}