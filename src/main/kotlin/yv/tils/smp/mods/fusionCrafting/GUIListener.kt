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
import yv.tils.smp.mods.fusionCrafting.manager.FusionCraftManage
import yv.tils.smp.mods.fusionCrafting.manager.FusionManagerGUI
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.logger.Debugger

class GUIListener {
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked
        val inv = e.inventory

        if (e.inventory.location != null) {
            return
        }

        when (player.openInventory.title()) {
            ColorUtils().convert("<gold>Fusion Crafting") -> {
                clickOverview(e, player)
            }
            ColorUtils().convert("<red>Fusion Management") -> {
                clickManagement(e, player)
            }
            ColorUtils().convert("<gold>Fusion Manager") -> {
                clickManager(e, player)
            }
            ColorUtils().convert("<gold>Edit Thumbnail") -> {
                editThumbnail(e, player)
            }
            else -> {
                if (ColorUtils().convert(player.openInventory.title()).startsWith("<gold>Fusion Crafting - ")) {
                    clickCrafting(e, player, inv)
                }
            }
        }
    }

    private fun clickOverview(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val slot = e.slot
        val currentTag = e.inventory.getItem(49)?.itemMeta?.persistentDataContainer?.get(FusionKeys.FUSION_CURRENT_FILTER.key, PersistentDataType.STRING) ?: ""

        when (slot) {
            11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.LIGHT_GRAY_STAINED_GLASS_PANE) return

                val fusionName = FusionLoader.component2name[e.currentItem!!.displayName()]!!
                val fusionMap = FusionLoader().loadFusion(fusionName)

                Debugger().log(
                    "Opened fusion",
                    "Name: $fusionName | Map: $fusionMap",
                    "yv.tils.smp.mods.fusionCrafting.GUIListener"
                )

                FusionCraftingGUI().fusionGUI(player, fusionMap)
            }

            45 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
                FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting", getCurrentPage(e.inventory) - 1, currentTag)
            }

            53 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
                FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting", getCurrentPage(e.inventory) + 1, currentTag)
            }

            49 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return

                var tags = FusionLoader.tagMap.keys.toMutableList()
                tags = tags.sorted().toMutableList()

                val clickType = e.click
                val index = tags.indexOf(currentTag)

                if (clickType.isLeftClick) {
                    if (index == tags.size - 1) {
                        FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting", 1)
                    } else {
                        FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting", 1, tags[index + 1])
                    }
                } else if (clickType.isRightClick) {
                    if (index == 0) {
                        FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting", 1)
                    } else {
                        FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting", 1, tags[index - 1])
                    }
                }
            }
        }
    }

    private fun clickManagement(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val slot = e.slot
        val currentTag = e.inventory.getItem(49)?.itemMeta?.persistentDataContainer?.get(FusionKeys.FUSION_CURRENT_FILTER.key, PersistentDataType.STRING) ?: ""

        when (slot) {
            11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42, 8 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.LIGHT_GRAY_STAINED_GLASS_PANE) return

                val fusionName: String = if (slot != 8) {
                    FusionLoader.component2name[e.currentItem!!.displayName()]!!
                } else {
                    "null"
                }

                Debugger().log(
                    "Opened fusion for management",
                    "Name: $fusionName | Player: ${player.name}",
                    "yv.tils.smp.mods.fusionCrafting.GUIListener"
                )

                FusionManagerGUI().openInventory(player as Player, fusionName)
            }

            45 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
                FusionOverview().openOverview(player as Player, "<red>Fusion Management", getCurrentPage(e.inventory) - 1, currentTag)
            }

            53 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
                FusionOverview().openOverview(player as Player, "<red>Fusion Management", getCurrentPage(e.inventory) + 1, currentTag)
            }

            49 -> {
                if (e.currentItem == null) return
                if (e.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return

                var tags = FusionLoader.tagMap.keys.toMutableList()
                tags = tags.sorted().toMutableList()

                val clickType = e.click
                val index = tags.indexOf(currentTag)

                if (clickType.isLeftClick) {
                    if (index == tags.size - 1) {
                        FusionOverview().openOverview(player as Player, "<red>Fusion Management", 1)
                    } else {
                        FusionOverview().openOverview(player as Player, "<red>Fusion Management", 1, tags[index + 1])
                    }
                } else if (clickType.isRightClick) {
                    if (index == 0) {
                        FusionOverview().openOverview(player as Player, "<red>Fusion Management", 1)
                    } else {
                        FusionOverview().openOverview(player as Player, "<red>Fusion Management", 1, tags[index - 1])
                    }
                }
            }
        }
    }

    private fun clickCrafting(e: InventoryClickEvent, player: HumanEntity, inv: Inventory) {
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
                FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting")
            }
        }
    }

    private fun clickManager(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val slot = e.slot

        val toggleSlot = 0
        val displayItemSlot = 11
        val nameSlot = 12
        val descriptionSlot = 13
        val tagsSlot = 14
        val fusionInvSlot = 15
        val backSlot = 18
        val deleteSlot = 26

        val thumbnailItem = FusionManagerGUI.playerManager[player.uniqueId]?.thumbnail

        when (slot) {
            toggleSlot -> {
                val currentState = e.currentItem?.type == Material.LIME_DYE

                if (currentState) {
                    e.currentItem = ItemStack(Material.RED_DYE)
                    e.currentItem?.itemMeta?.displayName(ColorUtils().convert("<green>ENABLE FUSION"))
                } else {
                    e.currentItem = ItemStack(Material.LIME_DYE)
                    e.currentItem?.itemMeta?.displayName(ColorUtils().convert("<red>DISABLE FUSION"))
                }
            }

            displayItemSlot -> {
                FusionCraftManage().editThumbnailItem(player as Player, thumbnailItem ?: ItemStack(Material.DIRT))
            }

            nameSlot -> {
                FusionCraftManage().editName(player as Player, FusionManagerGUI.playerManager[player.uniqueId]?.name ?: "")
            }

            descriptionSlot -> {
                FusionCraftManage().editDescription(player as Player, FusionManagerGUI.playerManager[player.uniqueId]?.description ?: "")
            }

            tagsSlot -> {
                println("This would open a GUI to change the tags")
                /*
                Each tag own Slot or Anvil GUI and Tags need to be seperated with space, comma or semicolon
                 */
            }

            fusionInvSlot -> {
                println("This would open the fusion inventory")
            }

            deleteSlot -> {
                println("This would delete the fusion")
            }

            backSlot -> {
                FusionOverview().openOverview(player as Player, "<red>Fusion Management")
            }
        }
    }

    private fun editThumbnail(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val slot = e.slot
        val rawSlot = e.rawSlot

        if (rawSlot > 8) {
            e.isCancelled = false
            return
        }

        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return
        val fusionName = fusion.fileName

        when (slot) {
            0 -> {
                val thumbnailItem = e.inventory.getItem(4) ?: return

                fusion.thumbnail = thumbnailItem

                FusionManagerGUI().openInventory(player as Player, fusionName)
            }
            4 -> {
                 e.isCancelled = false
            }
        }
    }

    private fun getCurrentPage(inv: Inventory): Int {
        val item = inv.getItem(4) ?: return 1
        val meta = item.itemMeta ?: return 1

        return meta.displayName()?.let { ColorUtils().convert(it) }?.split(" ")?.get(1)?.toInt() ?: 1
    }

    private fun addToInventory(player: HumanEntity, item: ItemStack) {
        if (item.type == Material.AIR) return

        var itemClone = item.clone()

        itemClone = handlePersistentData(itemClone)

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