package yv.tils.smp.mods.admin.invSee

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.inventory.GUIFiller
import java.util.*

class InvSeeListener {
    companion object {
        var invSee: MutableMap<UUID, InvSeePlayer> = HashMap()
    }

    data class InvSeePlayer(
        var player: UUID,
        var inv: Inventory
    )

    /**
     * Get inventory of player
     * @param target Player to get inventory
     */
    fun getInv(target: Player): Inventory {
        var inv = Bukkit.createInventory(
            null, 54,
            Placeholder().replacer(
                Language().getMessage(
                    LangStrings.MODULE_INVSEE_INVENTORY
                ),
                listOf("player"),
                listOf(target.name)
            )
        )

        val armour = target.inventory.armorContents
        val invContent = target.inventory.contents
        val offhand = target.inventory.itemInOffHand

        var j = 3
        for (i in 1..4) {
            inv.setItem(i, armour[j])
            j--
        }

        inv.setItem(7, offhand)

        j = 0
        for (i in 18..53) {
            if (i < 45) {
                inv.setItem(i, invContent[i - 9])
            } else {
                inv.setItem(i, invContent[j])
                j++
            }
        }

        // Filler
        val fillerSlots = mutableListOf<Int>()
        fillerSlots.addAll(listOf(0, 5, 6))
        fillerSlots.addAll(8..17)

        inv = GUIFiller().fillInventory(inv, onlySlots = fillerSlots)

        return inv
    }

    /**
     * Cancel filler interact
     * @param e InventoryClickEvent to check
     */
    fun onFillerInteract(e: InventoryClickEvent) {
        val player = e.whoClicked

        val invsee_invRaw = Language().getRawMessage(LangStrings.MODULE_INVSEE_INVENTORY)
        val invsee_inv = ColorUtils().convert(invsee_invRaw).toString()

        if (player.openInventory.title().toString()
                .startsWith(invsee_inv.split("<")[0]) && e.inventory.location == null
        ) {
            val rawSlot = e.rawSlot
            val slots = mutableListOf<Int>()
            slots.addAll(listOf(0, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17))

            if (slots.contains(rawSlot)) e.isCancelled = true
        }
    }

    fun onInventoryClose(e: InventoryCloseEvent) {
        val player = e.player

        if (invSee.containsKey(player.uniqueId)) {
            invSee.remove(player.uniqueId)
        }
    }
}