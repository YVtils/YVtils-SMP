package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetEvent
import yv.tils.smp.mods.admin.vanish.VanishEvents

class EntityTarget : Listener {
    @EventHandler
    fun onEvent(e: EntityTargetEvent) {
        VanishEvents().playerTarget(e)
    }
}