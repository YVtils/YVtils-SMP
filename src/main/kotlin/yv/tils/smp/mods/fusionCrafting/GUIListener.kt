package yv.tils.smp.mods.fusionCrafting

import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
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
                    val questMap = FusionLoader().loadFusion(questName)

                    Debugger().log(
                        "Opened fusion",
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
                    FusionCheck().buildItemList(player = player as Player, inv = inv)

                    if (!FusionCheck.canAccept) return

                    for (item in FusionCheck.fusionItems) {
                        player.inventory.removeItem(item)
                    }

                    var i = 0
                    while (i < 6) {
                        when (i) {
                            0 -> addToInventory(player, checkOutput(15, inv))
                            1 -> addToInventory(player, checkOutput(16, inv))
                            2 -> addToInventory(player, checkOutput(24, inv))
                            3 -> addToInventory(player, checkOutput(25, inv))
                            4 -> addToInventory(player, checkOutput(33, inv))
                            5 -> addToInventory(player, checkOutput(34, inv))
                            else -> break
                        }
                        i++
                    }

                    FusionCheck().buildItemList(player = player, inv = inv)
                }

                backSlot -> {
                    // Back
                    FusionCraftingGUI().generateGUI(player)
                }
            }
        }
    }

    private fun addToInventory(player: HumanEntity, item: ItemStack) {
        for (i in player.inventory.contents) {
            if (i != null && i.isSimilar(item) && i.amount + item.amount <= i.maxStackSize) {
                i.amount += item.amount
                return
            } else if (i != null && i.isSimilar(item)) {
                if (i.amount == i.maxStackSize) continue

                val remaining = i.maxStackSize - i.amount
                i.amount = i.maxStackSize
                item.amount -= remaining
                addToInventory(player, item)
                return
            }
        }

        if (player.inventory.firstEmpty() == -1) {
            val loc = player.location
            loc.y += 1.0

            player.location.world!!.dropItem(loc, item)
        } else {
            player.inventory.addItem(item)
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