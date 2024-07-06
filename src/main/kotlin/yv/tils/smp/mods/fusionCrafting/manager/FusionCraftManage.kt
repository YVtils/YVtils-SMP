package yv.tils.smp.mods.fusionCrafting.manager

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
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
}