package yv.tils.smp.mods.admin.invSee

import org.bukkit.event.inventory.InventoryClickEvent
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class InvSeeListener {
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
            if (e.slot == 0 || e.slot in 5..6 || e.slot in 8..17) e.isCancelled = true
        }
    }
}