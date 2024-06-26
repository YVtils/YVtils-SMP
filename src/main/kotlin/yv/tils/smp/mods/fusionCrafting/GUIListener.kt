package yv.tils.smp.mods.fusionCrafting

import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.enchantments.PlayerHeadLoad
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
                    if (e.currentItem == null) return
                    if (e.currentItem!!.type == Material.LIGHT_GRAY_STAINED_GLASS_PANE) return

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
                    if (e.currentItem == null) return
                    if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
                    println("This would go to the last page")
                }

                53 -> {
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
                    FusionCraftingGUI().generateGUI(player)
                }
            }
        }
    }

    private fun addToInventory(player: HumanEntity, item: ItemStack) {
        if (item.type == Material.AIR) return

        var itemClone = item.clone()

        println("Item Before: $itemClone")

        itemClone = handlePersistentData(itemClone)

        println("Item After: $itemClone")

        for (i in player.inventory.contents) {
            if (i != null && i.isSimilar(itemClone) && i.amount + itemClone.amount <= i.maxStackSize) {
                i.amount += itemClone.amount
                return
            } else if (i != null && i.isSimilar(itemClone)) {
                if (i.amount == i.maxStackSize) continue

                val remaining = i.maxStackSize - i.amount
                i.amount = i.maxStackSize
                itemClone.amount -= remaining
                addToInventory(player, itemClone)
                return
            }
        }

        if (player.inventory.firstEmpty() == -1) {
            val loc = player.location
            loc.y += 1.0

            player.location.world!!.dropItem(loc, itemClone)
        } else {
            player.inventory.addItem(itemClone)
        }
    }

    private fun handlePersistentData(item: ItemStack): ItemStack {
        var meta = item.itemMeta

        if (meta.persistentDataContainer.has(FusionKeys.FUSION_ITEMNAME.key, PersistentDataType.STRING)) {
            val normalItemName = meta.persistentDataContainer.get(FusionKeys.FUSION_ITEMNAME.key, PersistentDataType.STRING)
            meta.displayName(ColorUtils().convert(normalItemName!!))
            meta.persistentDataContainer.remove(FusionKeys.FUSION_ITEMNAME.key)
        }

        if (meta.persistentDataContainer.has(FusionKeys.FUSION_PLAYER_HEAD.key, PersistentDataType.STRING)) {
            for (fusionItem in FusionCheck.fusionItems) {
                if (fusionItem.type == Material.NAME_TAG) {
                    val newMeta = PlayerHeadLoad().loadPlayerHead(fusionItem, item, meta)

                    meta = newMeta
                }
            }
        }

        meta.persistentDataContainer.remove(FusionKeys.FUSION_GUI.key)
        item.itemMeta = meta

        return item
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

    fun onInventoryClose(e: InventoryCloseEvent) {
        val player = e.player
        val inv = e.inventory

        if (ColorUtils().convert(player.openInventory.title()).startsWith("<gold>Fusion Crafting - ") || player.openInventory.title() == ColorUtils().convert("<gold>Fusion Crafting")) {
            for (item in inv.contents) {
                if (item != null) {
                    val meta = item.itemMeta
                    if (meta.persistentDataContainer.has(FusionKeys.FUSION_GUI.key)) {
                        player.inventory.removeItem(item)
                    }
                }
            }
        }
    }
}