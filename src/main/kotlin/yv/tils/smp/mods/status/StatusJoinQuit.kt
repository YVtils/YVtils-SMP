package yv.tils.smp.mods.status

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer

class StatusJoinQuit {
    fun loadPlayer(e: PlayerJoinEvent) {
        val player = e.player
        val status = StatusConfig.saves[player.uniqueId.toString()]

        if (status != null) {
            if (StatusCommand().setStatusDisplay(player, status as String)) {
                val display = StatusConfig.config["display"] as String

                val displayCompo = StringReplacer().listReplacer(
                    ColorUtils().convert(display),
                    listOf("status", "playerName"),
                    listOf(status, player.name)
                )

                player.sendMessage(StringReplacer().listReplacer(
                    Language().getMessage(LangStrings.MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT),
                    listOf("status"),
                    listOf(ColorUtils().convert(displayCompo))
                ))
            }
        }
    }

    fun savePlayer(e: PlayerQuitEvent) {
        val player = e.player
        val team = player.scoreboard.getTeam(player.name)

        if (team != null) {
            StatusTeamManager().removePlayer(player)
        }
    }
}