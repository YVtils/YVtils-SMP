package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import yv.tils.smp.mods.other.message.MSGCommand.Companion.chatSession
import yv.tils.smp.mods.server.connect.PlayerQuit
import yv.tils.smp.mods.status.StatusJoinQuit

class PlayerQuitServer : Listener {
    @EventHandler
    fun onEvent(e: PlayerQuitEvent) {
        PlayerQuit().eventReceiver(e)
        StatusJoinQuit().savePlayer(e)
        msgSessionRemove(e)
    }

    private fun msgSessionRemove(e: PlayerQuitEvent) {
        val player = e.player

        for (key in chatSession.keys) {
            if (chatSession[key] == player.uniqueId || key == player.uniqueId) {
                chatSession.remove(key)
                msgSessionRemove(e)
                break
            }
        }
    }
}