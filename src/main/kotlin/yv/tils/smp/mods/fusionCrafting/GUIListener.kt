package yv.tils.smp.mods.fusionCrafting

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.logger.Debugger

class GUIListener {
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked
        val inv = e.inventory

        if (player.openInventory.title() == ColorUtils().convert("<gold>Fusion Crafting") && e.inventory.location == null) {
            e.isCancelled = true

            val slot = e.slot

            when (slot) {
                11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42 -> {
                    // Quest
                    if (e.currentItem == null) return
                    if (e.currentItem!!.type == Material.LIGHT_GRAY_STAINED_GLASS_PANE) return

                    // Open Quest
                    val questName = FusionLoader.component2name[e.currentItem!!.displayName()]!!
                    val questMap = FusionLoader().loadQuest(questName)

                    Debugger().log(
                        "Opened quest",
                        "Name: $questName | Map: $questMap",
                        "yv.tils.smp.mods.fusionCrafting.GUIListener"
                    )

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
        } else if (ColorUtils().convert(player.openInventory.title())
                .startsWith("<gold>Fusion Crafting - ") && e.inventory.location == null
        ) {
            e.isCancelled = true

            val slot = e.slot

            val acceptSlots = listOf(47, 48, 49, 50, 51, 52)
            val backSlot = 45

            when (slot) {
                in acceptSlots -> {
                    // Accept

                    var i = 0
                    while (player.inventory.firstEmpty() != -1) {
                        when (i) {
                            0 -> player.inventory.setItem(player.inventory.firstEmpty(), checkOutput(15, inv))
                            1 -> player.inventory.setItem(player.inventory.firstEmpty(), checkOutput(16, inv))
                            2 -> player.inventory.setItem(player.inventory.firstEmpty(), checkOutput(24, inv))
                            3 -> player.inventory.setItem(player.inventory.firstEmpty(), checkOutput(25, inv))
                            4 -> player.inventory.setItem(player.inventory.firstEmpty(), checkOutput(33, inv))
                            5 -> player.inventory.setItem(player.inventory.firstEmpty(), checkOutput(34, inv))
                            else -> break
                        }
                        i++
                    }

                    println("This would accept the fusion")
                }

                backSlot -> {
                    // Back
                    println("This would go back to the main fusion crafting GUI")
                }
            }
        }
    }

    private fun checkOutput(slot: Int, inv: Inventory): ItemStack {
        val item = inv.getItem(slot) ?: return ItemStack(Material.AIR)
        if (
            item.type == Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
            item.itemMeta.displayName()?.let { ColorUtils().convert(it) } == " "
        ) {
            return ItemStack(Material.AIR)
        }

        return item
    }
}