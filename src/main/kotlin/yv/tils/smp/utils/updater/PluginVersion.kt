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

        if ((player.hasPermission("yvtils.smp.update") || player.isOp)) {
            when (compareVersions()) {
                VersionState.OUTDATED_PATCH -> {
                    player.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(player.uniqueId, LangStrings.PLAYER_PLUGIN_UPDATE_AVAILABLE_PATCH),
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
                VersionState.OUTDATED_MINOR -> {
                    player.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(player.uniqueId, LangStrings.PLAYER_PLUGIN_UPDATE_AVAILABLE_MINOR),
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
                VersionState.OUTDATED_MAJOR -> {
                    player.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(player.uniqueId, LangStrings.PLAYER_PLUGIN_UPDATE_AVAILABLE_MAJOR),
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
                else -> {}
            }
        }
    }

    fun updateChecker(serverPluginVersion: String) {
        plVersion = serverPluginVersion
        webRequest()

        when (compareVersions()) {
            VersionState.UP_TO_DATE -> {
                Bukkit.getConsoleSender().sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.PLUGIN_UP_TO_DATE),
                        listOf("prefix"),
                        listOf(Vars().prefix)
                    )
                )
            }
            VersionState.OUTDATED_PATCH -> {
                Bukkit.getConsoleSender().sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.PLUGIN_UPDATE_AVAILABLE_PATCH),
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
            VersionState.OUTDATED_MINOR -> {
                Bukkit.getConsoleSender().sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.PLUGIN_UPDATE_AVAILABLE_MINOR),
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
            VersionState.OUTDATED_MAJOR -> {
                Bukkit.getConsoleSender().sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.PLUGIN_UPDATE_AVAILABLE_MAJOR),
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
            VersionState.UNKNOWN -> {
                Bukkit.getConsoleSender().sendMessage(
                    Language().directFormat(
                        "There occurred an error while searching for an update! Please contact the support!",
                        "Auf der Suche nach einem Plugin Update ist ein Fehler aufgetreten! Bitte kontaktiere den Support!"
                    )
                )
            }
        }
    }

    /**
     * Compare the versions of the plugin
     * @return VersionState
     * @see VersionState.OUTDATED_PATCH
     * @see VersionState.OUTDATED_MINOR
     * @see VersionState.OUTDATED_MAJOR
     * @see VersionState.UP_TO_DATE
     * @see VersionState.UNKNOWN
     */
    private fun compareVersions(): VersionState {
        val newVersion = version.split(".")
        val oldVersion = plVersion.split(".")

        if (newVersion.size != 3 || oldVersion.size != 3) {
            return VersionState.UNKNOWN
        } else if (newVersion[0] == "x" || newVersion[1] == "x" || newVersion[2] == "x") {
            return VersionState.UNKNOWN
        }

        var majorHigher = false
        var minorHigher = false
        var patchHigher = false

        if (newVersion[0].toInt() > oldVersion[0].toInt()) {
            majorHigher = true
        }

        if (newVersion[1].toInt() > oldVersion[1].toInt() && !majorHigher) {
            minorHigher = true
        }

        if (newVersion[2].toInt() > oldVersion[2].toInt() && !majorHigher && !minorHigher) {
            patchHigher = true
        }

        return when {
            majorHigher -> VersionState.OUTDATED_MAJOR
            minorHigher -> VersionState.OUTDATED_MINOR
            patchHigher -> VersionState.OUTDATED_PATCH
            else -> VersionState.UP_TO_DATE
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

enum class VersionState {
    UP_TO_DATE,
    OUTDATED_PATCH,
    OUTDATED_MINOR,
    OUTDATED_MAJOR,
    UNKNOWN
}