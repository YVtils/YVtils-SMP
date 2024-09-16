package yv.tils.smp.mods.fusionCrafting.manager

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.FusionCraftingGUI
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.mods.fusionCrafting.enchantments.DataTags
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.inventory.CustomHeads
import yv.tils.smp.utils.inventory.GUIFiller
import yv.tils.smp.utils.inventory.HeadUtils
import java.util.*

class FusionCraftManage {
    companion object {
        val playerListen = mutableMapOf<UUID, String>()
    }

    fun editFusionRecipe(player: Player, fusion: MutableMap<String, Any>) {
        var inv = Bukkit.createInventory(null, 54, ColorUtils().convert("<gold>Edit Fusion Recipe"))

        inv = FusionCraftingGUI().generateFusionGUI(inv, fusion)

        val inputSlots: List<Int> = listOf(10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31)
        val outputSlots: List<Int> = listOf(15, 16, 24, 25, 33, 34)
        val removeAcceptSlot = 52
        val infoSlot = 53

        for (slot in inputSlots) {
            val item = inv.getItem(slot)

            if (item == GUIFiller().secondaryFillerItem()) {
                inv.setItem(slot, ItemStack(Material.AIR))
            }
        }

        for (slot in outputSlots) {
            val item = inv.getItem(slot)

            if (item == GUIFiller().secondaryFillerItem()) {
                inv.setItem(slot, ItemStack(Material.AIR))
            }
        }

        for (slot in inputSlots + outputSlots) {
            val item = inv.getItem(slot) ?: continue
            val meta = item.itemMeta ?: continue
            val lore = meta.lore() ?: mutableListOf()

            lore.add(ColorUtils().convert(" "))
            lore.add(Language().getMessage(player.uniqueId, LangStrings.GUI_LEFT_CLICK_EDIT))
            lore.add(Language().getMessage(player.uniqueId, LangStrings.GUI_RIGHT_CLICK_REMOVE))

            meta.lore(lore)
            item.itemMeta = meta
            inv.setItem(slot, item)
        }

        inv.setItem(removeAcceptSlot, GUIFiller().mainFillerItem())

        val infoHead = HeadUtils().createCustomHead(CustomHeads.I_CHARACTER, "<aqua>Info Point")
        val infoHeadMeta = infoHead.itemMeta

        // TODO: Fix lore (newline tag gets ignored)
        val infoLore = mutableListOf<Component>()
//        infoLore.add(ColorUtils().convert(" "))
//        infoLore.add(ColorUtils().convert("<yellow> Modify fusion items:"))
//        infoLore.add(ColorUtils().convert("<gray>  - Left click on item in bottom inventory to add to input"))
//        infoLore.add(ColorUtils().convert("<gray>  - Right click on item in bottom inventory to add to output"))
//        infoLore.add(ColorUtils().convert("<gray>  - Left click on item in top inventory to modify"))
//        infoLore.add(ColorUtils().convert("<gray>  - Right click on item in top inventory to remove"))
//        infoLore.add(ColorUtils().convert(" "))
        infoLore.add(ColorUtils().convert(" "))
        infoLore.add(Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_INFO_POINT))
        infoLore.add(ColorUtils().convert(" "))


        infoHeadMeta.lore(infoLore)

        infoHead.itemMeta = infoHeadMeta
        inv.setItem(infoSlot, infoHead)

        player.openInventory(inv)
    }

    fun editThumbnailItem(player: Player, item: ItemStack) {
        var inv = Bukkit.createInventory(null, 9, ColorUtils().convert("<gold>Edit Thumbnail"))

        val accept = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val acceptMeta = accept.itemMeta
        acceptMeta.displayName(Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_CHANGE_THUMBNAIL))
        acceptMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)

        accept.itemMeta = acceptMeta
        inv.setItem(0, accept)

        inv = GUIFiller().fillInventory(inv)

        if (item.type != Material.BARRIER) {
            inv.setItem(4, item)
        } else {
            inv.setItem(4, ItemStack(Material.AIR))
        }

        player.openInventory(inv)
    }

    fun editName(player: Player, name: String) {
        player.closeInventory()
        player.sendMessage(Placeholder().replacer(
            Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_EDIT_NAME),
            mapOf(
                "name" to name
            )
        ))

        playerListen[player.uniqueId] = "fusionName"
    }

    fun editDescription(player: Player, description: String) {
        player.closeInventory()
        player.sendMessage(Placeholder().replacer(
            Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_EDIT_DESCRIPTION),
            mapOf(
                "description" to description
            )
        ))

        playerListen[player.uniqueId] = "fusionDescription"
    }

    fun filterTagsGUI(player: Player, tags: MutableList<String>) {
        val guiSize = when {
            tags.size <= 9 -> 18
            tags.size <= 18 -> 27
            tags.size <= 27 -> 36
            tags.size <= 36 -> 45
            else -> 54
        }

        val tailSlots = when {
            tags.size <= 9 -> mutableListOf(9, 10, 11, 12, 13, 14, 15, 16, 17)
            tags.size <= 18 -> mutableListOf(18, 19, 20, 21, 22, 23, 24, 25, 26)
            tags.size <= 27 -> mutableListOf(27, 28, 29, 30, 31, 32, 33, 34, 35)
            tags.size <= 36 -> mutableListOf(36, 37, 38, 39, 40, 41, 42, 43, 44)
            else -> mutableListOf(45, 46, 47, 48, 49, 50, 51, 52, 53)
        }

        val newTagSlot = tailSlots[4]
        val backSlot = tailSlots[0]

        var inv = Bukkit.createInventory(null, guiSize, ColorUtils().convert("<gold>Filter Tags"))

        val tagCreate = HeadUtils().createCustomHead(CustomHeads.PLUS_CHARACTER, "<green>Add Tag")
        inv.setItem(newTagSlot, tagCreate)

        for (tag in tags) {
            val item = ItemStack(Material.PAPER)
            val meta = item.itemMeta
            val lore = mutableListOf<Component>()
            meta.displayName(ColorUtils().convert("<gold>$tag"))

            lore.add(ColorUtils().convert(" "))
            lore.add(Language().getMessage(player.uniqueId, LangStrings.GUI_LEFT_CLICK_EDIT))
            lore.add(Language().getMessage(player.uniqueId, LangStrings.GUI_RIGHT_CLICK_REMOVE))

            meta.lore(lore)
            item.itemMeta = meta

            inv.addItem(item)
        }

        val backItem = ItemStack(Material.TIPPED_ARROW)
        val backMeta = backItem.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(Language().getMessage(player.uniqueId, LangStrings.GUI_PAGE_BACK))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        backItem.itemMeta = backMeta
        inv.setItem(backSlot, backItem)

        inv = GUIFiller().fillInventory(inv, onlySlots = tailSlots)

        player.openInventory(inv)
    }


    fun addInputItem(item: ItemStack, player: Player) {
        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return

        val itemMeta = item.itemMeta ?: return

        val name = ColorUtils().strip(item.displayName()).replace("[", "").replace("]", "")
        val display = item.type
        val amount = item.amount
        val data = mutableListOf<String>()

        val persisData = itemMeta.persistentDataContainer

        for (key in persisData.keys) {
            if (key.key.startsWith("fusion_")) {
                data.add(DataTags.getNameByKey(FusionKeys.valueOf(key.key.uppercase()))?.uppercase() ?: "")
            }
        }

        var prefix = "input.$name.0"
        prefix = generateInputPrefix(prefix, fusion.fusionInv)

        val configFormat = mutableListOf<MutableMap<String, Any>>()

        configFormat.add(mutableMapOf("item" to display.toString()))
        configFormat.add(mutableMapOf("amount" to amount.toString()))
        configFormat.add(mutableMapOf("data" to data.joinToString { it }))

        fusion.fusionInv[prefix] = configFormat

        reopenInv(player, fusion.fusionInv)
    }

    fun addOutputItem(item: ItemStack, player: Player) {
        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return

        val itemMeta = item.itemMeta ?: return

        val name = ColorUtils().strip(item.displayName()).replace("[", "").replace("]", "")
        val display = item.type
        val amount = item.amount
        val lore = mutableListOf<String>()
        val data = mutableListOf<String>()

        val loreData = itemMeta.lore()

        if (loreData != null) {
            for (line in loreData) {
                lore.add(ColorUtils().convert(line))
            }
        }

        val persisData = itemMeta.persistentDataContainer

        for (key in persisData.keys) {
            if (key.key.startsWith("fusion_")) {
                data.add(DataTags.getNameByKey(FusionKeys.valueOf(key.key.uppercase()))?.uppercase() ?: "")
            }
        }

        var prefix = "output.0"
        prefix = generateOutputPrefix(prefix, fusion.fusionInv)

        val configFormat = mutableListOf<MutableMap<String, Any>>()

        configFormat.add(mutableMapOf("item" to display.toString()))
        configFormat.add(mutableMapOf("amount" to amount.toString()))
        configFormat.add(mutableMapOf("name" to name))
        configFormat.add(mutableMapOf("lore" to lore.joinToString { it }))
        configFormat.add(mutableMapOf("data" to data.joinToString { it }))

        fusion.fusionInv[prefix] = configFormat

        reopenInv(player, fusion.fusionInv)
    }

    private fun reopenInv(player: Player, fusionInv: MutableMap<String, Any>) {
        Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
            FusionCraftManage().editFusionRecipe(player, fusionInv)
        }, 1)
    }

    private fun generateInputPrefix(prefix: String, fusion: MutableMap<String, Any>, i: Int = 1): String {
        if (fusion.containsKey(prefix)) {
            val splitPrefix = prefix.split(".")
            val prefix = splitPrefix[0] + "." + splitPrefix[1] + i + "." + (splitPrefix[2].toInt())
            return generateInputPrefix(prefix, fusion, i + 1)
        } else {
            return prefix
        }
    }

    private fun generateOutputPrefix(prefix: String, fusion: MutableMap<String, Any>, i: Int = 1): String {
        if (fusion.containsKey(prefix)) {
            val splitPrefix = prefix.split(".")
            val prefix = splitPrefix[0] + "." + i
            return generateOutputPrefix(prefix, fusion, i + 1)
        } else {
            return prefix
        }
    }

    fun listenForEdit(e: AsyncChatEvent) {
        val player = e.player
        val message = e.message()
        val coloredMsg = ColorUtils().convertChatMessage(message)
        val stringMsg = ColorUtils().convert(coloredMsg)
        val strippedMsg = ColorUtils().strip(stringMsg)

        if (!playerListen.contains(player.uniqueId)) return

        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return

        e.isCancelled = true

        when {
            stringMsg.lowercase() == "c" || stringMsg.lowercase() == "cancel" -> {
                player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.PROCESS_CANCELLED))
                reopenInventory(player, "manage", fusion.fileName)
            }

            playerListen[player.uniqueId] == "fusionName" -> {
                if (strippedMsg.length > 32) {
                    player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.INPUT_TOO_LONG))
                } else {
                    fusion.name = stringMsg
                    player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_UPDATED_NAME))
                    reopenInventory(player, "manage", fusion.fileName)
                }
            }

            playerListen[player.uniqueId] == "fusionDescription" -> {
                if (strippedMsg.length > 256) {
                    player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.INPUT_TOO_LONG))
                } else {
                    fusion.description = stringMsg
                    player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_UPDATED_DESCRIPTION))
                    reopenInventory(player, "manage", fusion.fileName)
                }
            }

            playerListen[player.uniqueId]?.startsWith("fusionTagModify - ") == true -> {
                val oldTag = playerListen[player.uniqueId]?.split(" - ")?.get(1)?.let { ColorUtils().strip(it) }
                fusion.tags.remove(oldTag)
                fusion.tags.add(stringMsg)
                reopenInventory(player, "tags", fusion.fileName)
            }
            playerListen[player.uniqueId] == "fusionTagNew" -> {
                fusion.tags.add(stringMsg)
                reopenInventory(player, "tags", fusion.fileName)
            }

            playerListen[player.uniqueId] == "fusionRecipeItemDisplayName" -> {
                val fusionItem = FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId] ?: return
                fusionItem.name = stringMsg

                reopenInventory(player, "recipe", fusion.fileName)
            }

            playerListen[player.uniqueId] == "fusionRecipeItemLore" -> {
                val fusionItem = FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId] ?: return

                val splitLore: MutableList<Component> = mutableListOf()

                for (line in stringMsg.split("<newline>")) {
                    splitLore.add(ColorUtils().convert(line))
                }

                fusionItem.lore = splitLore

                reopenInventory(player, "recipe", fusion.fileName)
            }
        }
    }

    private fun reopenInventory(player: Player, inv: String, fileName: String) {
        Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
            playerListen.remove(player.uniqueId)
            if (inv == "manage") FusionManagerGUI().openInventory(player, fileName)
            if (inv == "tags") filterTagsGUI(player, FusionManagerGUI.playerManager[player.uniqueId]?.tags ?: mutableListOf())
            if (inv == "recipe") FusionRecipeItemManage().openInventory(player, ItemStack(Material.DIRT), FusionRecipeItemManage.fusionRecipeItemEdit[player.uniqueId]?.type ?: "null")
        }, 1)
    }
}