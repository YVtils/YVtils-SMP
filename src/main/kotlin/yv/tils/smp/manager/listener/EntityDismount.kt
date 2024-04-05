package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDismountEvent
import yv.tils.smp.mods.sit.DismountListener

class EntityDismount : Listener {
    @EventHandler
    fun onDismount(e: EntityDismountEvent) {
        DismountListener().onDismount(e)
    }
}