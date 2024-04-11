package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import yv.tils.smp.mods.multiMine.MultiMineHandler

class BlockBreak : Listener {
    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        MultiMineHandler().trigger(e)
    }
}