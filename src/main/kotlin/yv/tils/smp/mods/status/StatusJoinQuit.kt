package yv.tils.smp.mods.status

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.configs.status.StatusConfig
import yv.tils.smp.utils.internalAPI.Placeholder

// TODO: Test if somehow still replicable: Wenn man sich einen Status gibt, den aber in der YML komplett rauslöscht & neu startet, hat man den Status in der Console trotzdem 😄

class StatusJoinQuit {
    fun loadPlayer(e: PlayerJoinEvent) {
        val player = e.player
        val status = StatusConfig.saves[player.uniqueId.toString()]

        if (status != null) {
            if (StatusCommand().setStatusDisplay(player, status as String)) {
                val display = StatusConfig.config["display"] as String

                val displayCompo = Placeholder().replacer(
                    ColorUtils().convert(display),
                    listOf("status", "playerName"),
                    listOf(status, player.name)
                )

                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.MODULE_STATUS_SELECTED_STATUS_JOIN_ANNOUNCEMENT),
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