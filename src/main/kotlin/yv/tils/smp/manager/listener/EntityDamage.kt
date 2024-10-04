package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import yv.tils.smp.manager.commands.handle.FlyHandler
import yv.tils.smp.manager.commands.handle.GodHandler
import yv.tils.smp.mods.other.SpawnElytra

class EntityDamage : Listener {
    @EventHandler
    fun onEvent(e: EntityDamageEvent) {
        FlyHandler().onLandingDamage(e)
        GodHandler().onDamage(e)
        SpawnElytra.getInstance().onLandDamage(e)
    }
}