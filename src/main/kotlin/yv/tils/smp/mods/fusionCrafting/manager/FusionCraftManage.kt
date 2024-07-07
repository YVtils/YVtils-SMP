package yv.tils.smp.mods.fusionCrafting.manager

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import yv.tils.smp.utils.color.ColorUtils

class FusionCraftManage {
    fun buildGUI(): Inventory {
        val inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Fusion Manager"))

        return inv
    }

    fun parseGUI(inv: Inventory): MutableMap<String, Any> {
        val fusionMap: MutableMap<String, Any> = mutableMapOf()

        return fusionMap
    }

    fun editThumbnailItem(player: Player, item: ItemStack) {
        val inv = Bukkit.createInventory(null, 9, ColorUtils().convert("<gold>Edit Thumbnail"))

        if (item.type != Material.DIRT) {
            inv.setItem(4, item)
        }

        val accept = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val acceptMeta = accept.itemMeta
        acceptMeta.displayName(ColorUtils().convert("<green>Change Thumbnail"))
        acceptMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)

        accept.itemMeta = acceptMeta
        inv.setItem(0, accept)

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        outerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, outerFiller)
            }
        }

        player.openInventory(inv)
    }
}