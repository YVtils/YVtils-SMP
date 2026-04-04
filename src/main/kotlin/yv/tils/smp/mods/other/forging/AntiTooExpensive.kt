package yv.tils.smp.mods.other.forging

import org.bukkit.GameMode
import org.bukkit.craftbukkit.inventory.CraftInventoryAnvil
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareAnvilEvent
import yv.tils.smp.utils.color.ColorUtils
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

        if (inv.getItem(0) == null || inv.getItem(1) == null || inv.result == null) return

        val player = (inv.viewers.firstOrNull())
        if (player != null && player.gameMode != GameMode.CREATIVE) {
            val price = inv.repairCost
            player.sendMessage(ColorUtils().convert(
                "<red>[AntiTooExpensive] <gray>Your anvil repair cost is <yellow>$price<gray> levels"
            ))
        }
    }
}