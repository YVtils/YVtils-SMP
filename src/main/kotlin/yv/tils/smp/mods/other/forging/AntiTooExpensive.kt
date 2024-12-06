package yv.tils.smp.mods.other.forging

import org.bukkit.craftbukkit.inventory.CraftInventoryAnvil
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareAnvilEvent
import yv.tils.smp.utils.configs.global.Config

class AntiTooExpensive {
    companion object {
        var active = Config.config["disableTooExpensive"] as Boolean
    }

    fun playerForgeEvent(e: PrepareAnvilEvent) {
        if (!active) return
        if (e.inventory.type != InventoryType.ANVIL) return

        val inv = e.inventory as CraftInventoryAnvil

        inv.maximumRepairCost = Int.MAX_VALUE
    }
}