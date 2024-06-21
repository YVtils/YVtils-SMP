package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.hanging.HangingBreakEvent
import yv.tils.smp.mods.fusionCrafting.enchantments.Invisible

class HangingBreak : Listener {
    @EventHandler
    fun onHangingBreak(e: HangingBreakEvent) {
        Invisible().onHangingBreak(e)
    }
}