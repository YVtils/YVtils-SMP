package yv.tils.smp.mods.fusionCrafting

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.enchantments.PlayerHeadLoad
import yv.tils.smp.mods.fusionCrafting.manager.FusionCraftManage
import yv.tils.smp.mods.fusionCrafting.manager.FusionManagerGUI
import yv.tils.smp.mods.fusionCrafting.manager.FusionRecipeItemManage
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
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
                invClickSound(player as Player)
                clickOverview(e, player)
            }
            ColorUtils().convert("<red>Fusion Management") -> {
                invClickSound(player as Player)
                clickManagement(e, player)
            }
            ColorUtils().convert("<gold>Fusion Manager") -> {
                invClickSound(player as Player)
                clickManager(e, player)
            }
            ColorUtils().convert("<gold>Edit Thumbnail") -> {
                invClickSound(player as Player)
                editThumbnail(e, player)
            }
            ColorUtils().convert("<gold>Filter Tags") -> {
                invClickSound(player as Player)
                editFilterTags(e, player)
            }
            ColorUtils().convert("<gold>Edit Fusion Recipe") -> {
                invClickSound(player as Player)
                editFusionRecipe(e, player)
            }
            ColorUtils().convert("<gold>Edit Item") -> {
                invClickSound(player as Player)
                editFusionRecipeItem(e, player)
            }
            ColorUtils().convert("<gold>Edit Display Item") -> {
                invClickSound(player as Player)
                editFusionRecipeDisplay(e, player)
            }
            ColorUtils().convert("<gold>Modify accepted items") -> {
                invClickSound(player as Player)
                editFusionRecipeInputItems(e, player)
            }
            ColorUtils().convert("<gold>Edit Data Tags") -> {
                invClickSound(player as Player)
                editFusionRecipeTags(e, player)
            }
            ColorUtils().convert("<gold>Append Data Tag") -> {
                invClickSound(player as Player)
                appendFusionRecipeTags(e, player)
            }
            else -> {
                if (ColorUtils().convert(player.openInventory.title()).startsWith("<gold>Fusion Crafting - ")) {
                    invClickSound(player as Player)
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
                    } else if (index == -1) {
                        FusionOverview().openOverview(player as Player, "<gold>Fusion Crafting", 1, tags[tags.size - 1])
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

        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return
        val thumbnailItem = fusion.thumbnail

        when (slot) {
            toggleSlot -> {
                val currentState = e.currentItem?.type == Material.LIME_DYE

                val item = ItemStack(if (currentState) Material.RED_DYE else Material.LIME_DYE)
                val meta = item.itemMeta
                meta.displayName(ColorUtils().convert(if (!currentState) "<red>DISABLE FUSION" else "<green>ENABLE FUSION"))
                item.itemMeta = meta

                fusion.state = !currentState

                e.inventory.setItem(toggleSlot, item)
            }

            displayItemSlot -> {
                FusionCraftManage().editThumbnailItem(player as Player, thumbnailItem)
            }

            nameSlot -> {
                FusionCraftManage().editName(player as Player, FusionManagerGUI.playerManager[player.uniqueId]?.name ?: "")
            }

            descriptionSlot -> {
                FusionCraftManage().editDescription(player as Player, FusionManagerGUI.playerManager[player.uniqueId]?.description ?: "")
            }

            tagsSlot -> {
                FusionCraftManage().filterTagsGUI(player as Player, FusionManagerGUI.playerManager[player.uniqueId]?.tags ?: mutableListOf())
            }

            fusionInvSlot -> {
                FusionCraftManage().editFusionRecipe(player as Player, FusionManagerGUI.playerManager[player.uniqueId]?.fusionInv ?: mutableMapOf())
            }

            deleteSlot -> {
                FusionManagerGUI().deleteFusion(player as Player)

                FusionManagerGUI.playerManager.remove(player.uniqueId)

                FusionOverview().openOverview(player, "<red>Fusion Management")
            }

            backSlot -> {
                FusionManagerGUI().setData(FusionManagerGUI.playerManager[player.uniqueId] ?: return)
                FusionManagerGUI.playerManager.remove(player.uniqueId)
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

    private fun editFilterTags(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val slot = e.slot
        val guiSize = e.inventory.size

        val tagSlots = when {
            guiSize <= 18 -> mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
            guiSize <= 27 -> mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17)
            guiSize <= 36 -> mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)
            guiSize <= 45 -> mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35)
            else -> mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44)
        }

        val tailSlots = when {
            guiSize <= 18 -> mutableListOf(9, 10, 11, 12, 13, 14, 15, 16, 17)
            guiSize <= 27 -> mutableListOf(18, 19, 20, 21, 22, 23, 24, 25, 26)
            guiSize <= 36 -> mutableListOf(27, 28, 29, 30, 31, 32, 33, 34, 35)
            guiSize <= 45 -> mutableListOf(36, 37, 38, 39, 40, 41, 42, 43, 44)
            else -> mutableListOf(45, 46, 47, 48, 49, 50, 51, 52, 53)
        }

        val newTagSlot = tailSlots[4]
        val backSlot = tailSlots[0]

        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return
        val itemName = e.currentItem?.itemMeta?.displayName()?.let { ColorUtils().convert(it) } ?: return

        when (slot) {
            in tagSlots -> {
                if (e.currentItem == null) return

                val clickType = e.click

                if (clickType.isLeftClick) {
                    player.closeInventory()
                    FusionCraftManage.playerListen[player.uniqueId] = "fusionTagModify - $itemName"
                    player.sendMessage(Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_EDIT_TAG),
                        listOf("tag"),
                        listOf(ColorUtils().convert(e.currentItem?.itemMeta?.displayName()!!))
                    ))
                } else if (clickType.isRightClick) {
                    val stripped = ColorUtils().strip(itemName)
                    fusion.tags.remove(stripped)
                    FusionCraftManage().filterTagsGUI(player as Player, fusion.tags)
                }
            }

            newTagSlot -> {
                player.closeInventory()
                FusionCraftManage.playerListen[player.uniqueId] = "fusionTagNew"
                player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_CREATE_TAG))
            }

            backSlot -> {
                FusionManagerGUI().openInventory(player as Player, fusion.fileName)
            }
        }
    }

    private fun editFusionRecipe(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val rawSlot = e.rawSlot

        val inputSlots: List<Int> = listOf(10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31)
        val outputSlots: List<Int> = listOf(15, 16, 24, 25, 33, 34)
        val bottomInvSlots: MutableList<Int> = mutableListOf()

        for (i in 54 until 89) {
            bottomInvSlots.add(i)
        }

        when (rawSlot) {
            in inputSlots -> {
                val clickType = e.click
                val item = e.currentItem ?: return

                if (item.type == Material.AIR) return

                if (clickType.isLeftClick) {
                    FusionRecipeItemManage().openInventory(player as Player, item, "input")
                } else if (clickType.isRightClick) {
                    FusionRecipeItemManage().deleteRecipeItem(player as Player, item, "input")
                }
            }

            in outputSlots -> {
                val clickType = e.click
                val item = e.currentItem ?: return

                if (item.type == Material.AIR) return

                if (clickType.isLeftClick) {
                    FusionRecipeItemManage().openInventory(player as Player, item, "output")
                } else if (clickType.isRightClick) {
                    FusionRecipeItemManage().deleteRecipeItem(player as Player, item, "output")
                }
            }

            in bottomInvSlots -> {
                val clickType = e.click
                val item = e.currentItem ?: return

                if (item.type == Material.AIR) return

                if (clickType.isLeftClick) {
                    FusionCraftManage().addInputItem(item, player as Player)
                } else if (clickType.isRightClick) {
                    FusionCraftManage().addOutputItem(item, player as Player)
                }
            }

            45, 47, 48, 49, 50, 51, 52 -> {
                FusionManagerGUI().openInventory(player as Player, FusionManagerGUI.playerManager[player.uniqueId]?.fileName ?: "")
            }
        }
    }

    private fun editFusionRecipeItem(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val rawSlot = e.rawSlot

        if (rawSlot > 9*3 - 1) {
            e.isCancelled = false
            return
        }

        val fusionItem = FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId] ?: return

        val displayItemSlot: Int
        val usableItemsSlot: Int
        val displayNameSlot: Int
        val amountSlot: Int
        val loreSlot: Int
        val dataSlot: Int
        val backSlot = 18

        when (fusionItem.type) {
            "input" -> {
                displayItemSlot = -1
                loreSlot = -1

                usableItemsSlot = 11
                displayNameSlot = 12
                amountSlot = 13
                dataSlot = 14
            }
            "output" -> {
                usableItemsSlot = -1

                displayItemSlot = 11
                displayNameSlot = 12
                amountSlot = 13
                loreSlot = 14
                dataSlot = 15
            }
            else -> {
                return
            }
        }


        when (rawSlot) {
            displayItemSlot -> {
                FusionRecipeItemManage().editDisplayItem(player as Player)
            }
            usableItemsSlot -> {
                FusionRecipeItemManage().editAcceptedItems(player as Player)
            }
            displayNameSlot -> {
                FusionRecipeItemManage().editDisplayName(player as Player)
            }
            amountSlot -> {
                val clickType = e.click
                var item = e.currentItem ?: return
                item = FusionRecipeItemManage().editAmount(player as Player, clickType, item)
                e.currentItem = item
            }
            loreSlot -> {
                FusionRecipeItemManage().editLore(player as Player)
            }
            dataSlot -> {
                FusionRecipeItemManage().editDataTags(player as Player)
            }
            backSlot -> {
                FusionRecipeItemManage().parseDataToMap(player as Player)
                FusionCraftManage().editFusionRecipe(player, FusionManagerGUI.playerManager[player.uniqueId]?.fusionInv ?: mutableMapOf())
            }
        }
    }

    private fun editFusionRecipeDisplay(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val rawSlot = e.rawSlot

        if (rawSlot > 8 || rawSlot == 4) {
            e.isCancelled = false
            return
        }

        if (rawSlot == 0) {
            val fusionRecipe = FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId] ?: return
            val displayItem = e.inventory.getItem(4) ?: return

            fusionRecipe.material = displayItem.type

            FusionRecipeItemManage().openInventory(player as Player, ItemStack(Material.DIRT), fusionRecipe.type)
        }
    }

    private fun editFusionRecipeInputItems(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val fusionRecipe = FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId] ?: return
        val rawSlot = e.rawSlot
        val backButton = 40
        val itemSlots = mutableListOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34)

        if (rawSlot > 9*5 - 1 || rawSlot in itemSlots) {
            e.isCancelled = false
            return
        }

        when (rawSlot) {
            backButton -> {
                FusionRecipeItemManage().saveItemList(player as Player, e.inventory)
                FusionRecipeItemManage().openInventory(player, ItemStack(Material.DIRT), fusionRecipe.type)
            }
        }
    }

    private fun editFusionRecipeTags(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val slot = e.slot

        val newTagSlot = 4
        val tagSlots = listOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25)
        val backSlot = 31

        val fusionRecipe = FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId] ?: return

        when (slot) {
            newTagSlot -> {
                FusionRecipeItemManage().appendDataTag(player as Player)
            }
            in tagSlots -> {
                val item = e.currentItem ?: return

                val innerFiller = ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                val fillerMeta = innerFiller.itemMeta
                fillerMeta.displayName(ColorUtils().convert(" "))
                fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                innerFiller.itemMeta = fillerMeta

                if (item.type == Material.AIR || item == innerFiller) return

                val dataTag = ColorUtils().strip(item.itemMeta.displayName() ?: return)

                fusionRecipe.data.remove(dataTag)

                FusionRecipeItemManage().editDataTags(player as Player)
            }
            backSlot -> {
                FusionRecipeItemManage().openInventory(player as Player, ItemStack(Material.DIRT), fusionRecipe.type)
            }
        }
    }

    private fun appendFusionRecipeTags(e: InventoryClickEvent, player: HumanEntity) {
        e.isCancelled = true

        val slot = e.slot

        val dataSlots = listOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25)
        val backSlot = 31

        when (slot) {
            in dataSlots -> {
                val item = e.currentItem ?: return
                val innerFiller = ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                val fillerMeta = innerFiller.itemMeta
                fillerMeta.displayName(ColorUtils().convert(" "))
                fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                innerFiller.itemMeta = fillerMeta

                if (item.type == Material.AIR || item == innerFiller) return

                val dataTag = ColorUtils().strip(item.itemMeta.displayName() ?: return)

                val fusionRecipe = FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId] ?: return

                fusionRecipe.data.add(dataTag)

                FusionRecipeItemManage().editDataTags(player as Player)
            }
            backSlot -> {
                FusionRecipeItemManage().editDataTags(player as Player)
            }
        }
    }

    private fun getCurrentPage(inv: Inventory): Int {
        val item = inv.getItem(4) ?: return 1
        val meta = item.itemMeta ?: return 1

        return meta.displayName()?.let { ColorUtils().convert(it) }?.split(" ")?.get(1)?.toInt() ?: 1
    }

    private fun invClickSound(player: Player) {
        player.playSound(player.location, Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 0.5f, 2.0f)
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
                    val newMeta = PlayerHeadLoad().loadPlayerHead(fusionItem, item)

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
        val reason = e.reason

        if (ColorUtils().convert(player.openInventory.title()).startsWith("<gold>Fusion Crafting - ") || player.openInventory.title() == ColorUtils().convert("<gold>Fusion Crafting")) {
            for (item in inv.contents) {
                if (item != null) {
                    val meta = item.itemMeta
                    if (meta.persistentDataContainer.has(FusionKeys.FUSION_GUI.key)) {
                        player.inventory.removeItem(item)
                    }
                }
            }
        } else if (FusionManagerGUI.playerManager.containsKey(player.uniqueId) && reason != InventoryCloseEvent.Reason.OPEN_NEW && reason != InventoryCloseEvent.Reason.PLUGIN) {
            FusionManagerGUI.playerManager.remove(player.uniqueId)
        }
    }
}