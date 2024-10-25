package yv.tils.smp.mods.server.connect

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerQuitEvent
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.mods.waypoints.WaypointPath
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.logger.Debugger

class PlayerQuit {

    private var state = 1

    fun eventReceiver(event: PlayerQuitEvent) {
        Debugger().log(
            "PlayerQuit - Event Receiver",
            "Player ${event.player.name} quit the server",
            "yv.tils.smp.mods.server.connect.PlayerQuit.eventReceiver()"
        )
        funcStarter(state, event)
    }

    private fun funcStarter(state: Int, e: PlayerQuitEvent) {
        when (state) {
            1 -> vanishQuit(e, e.player)
            2 -> sendQuitMessage(e, e.player)
            3 -> stopNavigation(e, e.player)
        }
    }

    private fun vanishQuit(e: PlayerQuitEvent, player: Player) {
        if (!(Config.config["modules.admin"] as Boolean)) {
            funcStarter(state++, e)
            return
        }

        if (Vanish.vanish.containsKey(player.uniqueId) && Vanish.vanish[player.uniqueId]!!) {
            e.quitMessage(null)
            Language.playerLang.remove(player.uniqueId)
            state = -1
        } else {
            funcStarter(state++, e)
        }
    }

    private fun sendQuitMessage(e: PlayerQuitEvent, player: Player) {
        if (!(Config.config["modules.server"] as Boolean)) {
            funcStarter(state++, e)
            return
        }

        e.quitMessage(generateQuitMessage(player))
        funcStarter(state++, e)
    }

    fun generateQuitMessage(player: Player): Component {
        val messages = Config.config["leaveMessages"] as List<String>

        val random = messages.indices.random()

        Debugger().log(
            "PlayerQuit - Generate Quit Message",
            "Generated Following Quit Message: ${messages[random]}",
            "yv.tils.smp.mods.server.connect.PlayerQuit.generateQuitMessage()"
        )

        return Placeholder().replacer(
            Component.text(messages[random]),
            listOf("player"),
            listOf(player.name)
        )
    }

    private fun stopNavigation(e: PlayerQuitEvent, player: Player) {
        if (!(Config.config["modules.waypoints"] as Boolean)) {
            funcStarter(state++, e)
            return
        }

        val navigations = WaypointPath.navigatingPlayers
        if (navigations.containsKey(player)) {
            navigations[player]?.task?.cancel()
            navigations[player]?.endCrystal?.remove()
            navigations.remove(player)
        }
        funcStarter(state++, e)
    }
}