package yv.tils.smp.mods.fusionCrafting.creator

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import yv.tils.smp.utils.color.ColorUtils

class Inventory {
    fun openInventory(player: Player) {
        var inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Fusion Creator"))

        inv = generateContent(inv)

        player.openInventory(inv)
    }

    private fun generateContent(inv: Inventory): Inventory {
        // 0 = Toggle of fusion
        // 10 = Display Item in overview
        // 11 = Name of fusion
        // 12 = Description of fusion
        // 13 = TAGS of fusion
        // 14 = Fusion Inv to edit
        // 18 = Back to overview
        // 26 = Delete fusion
        // Rest = DARK_GLASS_PANE



        return inv
    }
}