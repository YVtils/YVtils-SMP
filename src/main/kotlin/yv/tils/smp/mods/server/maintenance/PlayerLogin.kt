package yv.tils.smp.mods.server.maintenance

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent

class PlayerLogin {

    private var state = 1

    fun eventReceiver(e: PlayerLoginEvent) {

        println("PlayerLogin - Event Receiver")

        funcStarter(state, e)

        println("PlayerLogin - Event Receiver - End")
    }

    private fun funcStarter(state: Int, e: PlayerLoginEvent) {
        when (state) {
            1 -> checkPerm(e, e.player)
        }
    }

    private fun checkPerm(e: PlayerLoginEvent, player: Player, ) {
        //TODO: Add permission & check if maintenance mode is enabled
        if (false && !player.hasPermission("yvtils.bypass.maintenance")) {
            e.kickMessage(Component.text("You do not have permission to join this server."))
        } else {
            funcStarter(state++, e)
        }
    }
}