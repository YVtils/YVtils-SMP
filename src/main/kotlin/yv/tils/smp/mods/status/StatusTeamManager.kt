package yv.tils.smp.mods.status

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

class StatusTeamManager {
    private fun checkTeam(name: String, player: Player): Team {
        val team = player.scoreboard.getTeam(name)
        if (team != null) {
            return team
        }
        return player.scoreboard.registerNewTeam(name)
    }

    fun addPlayer(player: Player, prefix: Component = Component.empty()) {
        val team = checkTeam(player.uniqueId.toString() + "UUID", player)
        team.prefix(prefix)
        team.addEntry(player.name)
    }

    fun removePlayer(player: Player) {
        val team = player.scoreboard.getEntryTeam(player.name)
        team?.removeEntry(player.name)
    }
}