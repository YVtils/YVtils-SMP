package yv.tils.smp.mods.other.forging

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareAnvilEvent
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.util.UUID

class AntiTooExpensive {
    companion object {
        var active = Config.config["disableTooExpensive"] as Boolean
        var messageCooldown: MutableMap<UUID, Int> = mutableMapOf()
    }

    fun playerForgeEvent(e: PrepareAnvilEvent) {
        if (!active) return
        if (e.inventory.type != InventoryType.ANVIL) return

        e.inventory.maximumRepairCost = Int.MAX_VALUE

        val inv = e.inventory

        val player = e.viewers[0]

        if (e.getResult() != null && inv.getRepairCost() >= 40 && (messageCooldown[player.uniqueId] == null || messageCooldown[player.uniqueId] == 0)) {
            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(player.uniqueId, LangStrings.FORGING_TOO_EXPENSIVE),
                    listOf("level"),
                    listOf(inv.getRepairCost().toString())
                )
            )
            messageCooldown[player.uniqueId] = 10

            Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
                messageCooldown[player.uniqueId] = 0
            }, 20 * 10)
        }
    }
}