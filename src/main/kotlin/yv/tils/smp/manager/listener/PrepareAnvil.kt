package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareAnvilEvent
import yv.tils.smp.mods.other.forging.AntiTooExpensive

class PrepareAnvil : Listener {
    @EventHandler
    fun onEvent(e: PrepareAnvilEvent) {
        AntiTooExpensive().playerForgeEvent(e)
    }
}