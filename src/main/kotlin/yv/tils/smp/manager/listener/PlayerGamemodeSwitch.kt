package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerGameModeChangeEvent
import yv.tils.smp.manager.commands.handle.FlyHandler
import yv.tils.smp.mods.admin.vanish.VanishEvents

class PlayerGamemodeSwitch : Listener {
    @EventHandler
    fun onEvent(e: PlayerGameModeChangeEvent) {
        FlyHandler().onGamemodeSwitch(e)
        VanishEvents().onGamemodeSwitch(e)
    }
}