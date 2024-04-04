package yv.tils.smp.manager.listener

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import yv.tils.smp.manager.commands.GlobalMuteCMD
import yv.tils.smp.mods.admin.moderation.Mute

class PlayerChat : Listener {
    @EventHandler
    fun onEvent(e: AsyncChatEvent) {
        GlobalMuteCMD().playerChatEvent(e)
        Mute().playerChat(e)
    }
}