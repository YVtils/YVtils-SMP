package yv.tils.smp.mods.status

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.configs.status.StatusConfig
import yv.tils.smp.utils.internalAPI.Placeholder

class StatusJoinQuit {
    fun loadPlayer(e: PlayerJoinEvent) {
        if (!(Config.config["modules.status"] as Boolean)) {
            return
        }

        val player = e.player
        val status = StatusConfig.saves[player.uniqueId.toString()]

        if (status != null) {
            if (StatusHandler().setStatusDisplay(player, status as String)) {
                val display = StatusConfig.config["display"] as String

                val displayCompo = Placeholder().replacer(
                    ColorUtils().convert(display),
                    listOf("status", "playerName"),
                    listOf(status, player.name)
                )

                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT),
                        listOf("status"),
                        listOf(ColorUtils().convert(displayCompo))
                    )
                )
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