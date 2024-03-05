package yv.tils.smp.mods.fusionCrafting

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import yv.tils.smp.utils.color.ColorUtils

class GUIListener {
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked

        if (player.openInventory.title() == ColorUtils().convert("<gold>Fusion Crafting") && e.inventory.location == null){
            e.isCancelled = true

            val slot = e.slot

            when(slot) {
                11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42 -> {
                    // Quest
                    if (e.currentItem == null) return
                    if (e.currentItem!!.type == Material.LIGHT_GRAY_STAINED_GLASS_PANE) return

                    // Open Quest
                    val questName = FusionLoader.component2name[e.currentItem!!.displayName()]!!
                    val questMap = FusionLoader().loadQuest(questName)

                    println("This would open the quest $questName with this map: \n$questMap")

                    FusionCraftingGUI().fusionGUI(player, questMap)
                }
                45 -> {
                    // Last Page
                    if (e.currentItem == null) return
                    if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
                    println("This would go to the last page")
                }
                53 -> {
                    // Next Page
                    if (e.currentItem == null) return
                    if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
                    println("This would go to the next page")
                }
            }
        }
    }
}