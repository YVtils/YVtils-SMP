package yv.tils.smp.mods.server.connect

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.internalAPI.StringReplacer
import yv.tils.smp.utils.logger.Debugger

class PlayerQuit {

    private var state = 1

    fun eventReceiver(event: PlayerQuitEvent) {
        Debugger().log("PlayerQuit - Event Receiver", "Player ${event.player.name} quit the server", "yv.tils.smp.mods.server.connect.PlayerQuit.eventReceiver()")
        funcStarter(state, event)
    }

    private fun funcStarter(state: Int, e: PlayerQuitEvent) {
        when (state) {
            1 -> vanishQuit(e, e.player)
            2 -> sendQuitMessage(e, e.player)
        }
    }

    private fun vanishQuit(e: PlayerQuitEvent, player: Player) {
        if (false) {
            e.quitMessage(null)
            state = -1
        } else {
            funcStarter(state++, e)
        }
    }

    private fun sendQuitMessage(e: PlayerQuitEvent, player: Player) {
        e.quitMessage(generateQuitMessage(player))
        funcStarter(state++, e)
    }

    fun generateQuitMessage(player: Player): Component {
        val messages = Config.config["leaveMessages"] as List<String>

        println(messages)
        println(messages.size)

        val random = messages.indices.random()

        Debugger().log("PlayerQuit - Generate Quit Message", "Generated Following Quit Message: ${messages[random]}", "yv.tils.smp.mods.server.connect.PlayerQuit.generateQuitMessage()")

        return StringReplacer().listReplacer(
            Component.text(messages[random]),
            listOf("player"),
            listOf(player.name)
        )
    }
}