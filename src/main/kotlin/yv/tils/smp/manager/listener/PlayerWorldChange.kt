package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import yv.tils.smp.manager.commands.handle.FlyHandler
import yv.tils.smp.mods.admin.vanish.VanishEvents
import yv.tils.smp.mods.other.SpawnElytra

class PlayerWorldChange : Listener {
    @EventHandler
    fun onEvent(e: PlayerChangedWorldEvent) {
        FlyHandler().onWorldChange(e)
        VanishEvents().onWorldChange(e)
        SpawnElytra.getInstance().onWorldChange(e)
    }
}