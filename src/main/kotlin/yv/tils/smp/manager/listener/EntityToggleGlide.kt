package yv.tils.smp.manager.listener

import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleGlideEvent
import yv.tils.smp.mods.other.SpawnElytra

class EntityToggleGlide : Listener {
    fun onEvent(e: EntityToggleGlideEvent) {
        SpawnElytra.getInstance().onToggleGlide(e)
    }
}