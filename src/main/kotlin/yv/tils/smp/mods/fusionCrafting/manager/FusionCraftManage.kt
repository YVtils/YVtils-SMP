package yv.tils.smp.mods.fusionCrafting.manager

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.FusionCraftingGUI
import yv.tils.smp.mods.fusionCrafting.FusionGUIHeads
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.inventory.GUIFiller
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
            lore.add(ColorUtils().convert("<green>Left click to edit"))
            lore.add(ColorUtils().convert("<red>Right click to remove"))

            meta.lore(lore)
            item.itemMeta = meta
            inv.setItem(slot, item)
        }

        inv.setItem(removeAcceptSlot, GUIFiller().mainFillerItem())

        val infoHead = ItemStack(Material.PLAYER_HEAD)
        val infoHeadMeta = infoHead.itemMeta as SkullMeta
        val gameProfile = GameProfile(UUID.randomUUID(), "PageHead")
        gameProfile.properties.put("textures", Property("textures", FusionGUIHeads.INFO.texture))

        try {
            val profileField = infoHeadMeta.javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField.set(infoHeadMeta, gameProfile)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        infoHeadMeta.displayName(ColorUtils().convert("<aqua>Info Point"))
        infoHeadMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)

        val infoLore = mutableListOf<Component>()
        infoLore.add(ColorUtils().convert(" "))
        infoLore.add(ColorUtils().convert("<yellow> Modify fusion items:"))
        infoLore.add(ColorUtils().convert("<gray>  - Left click on item in bottom inventory to add to input"))
        infoLore.add(ColorUtils().convert("<gray>  - Right click on item in bottom inventory to add to output"))
        infoLore.add(ColorUtils().convert("<gray>  - Left click on item in top inventory to modify"))
        infoLore.add(ColorUtils().convert("<gray>  - Right click on item in top inventory to remove"))
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
        acceptMeta.displayName(ColorUtils().convert("<green>Change Thumbnail"))
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
        player.sendMessage(ColorUtils().convert(
            "<gold>Editing Fusion Name<newline>" +
                "<gray>Current Name: <aqua>$name<newline>" +
                "<red>'c' to cancel"
        ))

        playerListen[player.uniqueId] = "fusionName"
    }

    fun editDescription(player: Player, description: String) {
        player.closeInventory()
        player.sendMessage(ColorUtils().convert(
            "<gold>Editing Fusion Description<newline>" +
                "<gray>Current Description: <white>$description<newline>" +
                "<red>'c' to cancel<newline>" +
                "<gray><click:open_url:https://yvtils.net/yvtils/colorcodes>Color Code Guide</click>"
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

        val tagCreate = ItemStack(Material.PLAYER_HEAD, 1)
        val tagCreateMeta = tagCreate.itemMeta as SkullMeta
        val gameProfile = GameProfile(UUID.randomUUID(), "PageHead")
        gameProfile.properties.put("textures", Property("textures", FusionGUIHeads.ADD_FUSION.texture))

        try {
            val profileField = tagCreateMeta.javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField.set(tagCreateMeta, gameProfile)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        tagCreateMeta.displayName(ColorUtils().convert("<green>Add Tag"))
        tagCreateMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        tagCreate.itemMeta = tagCreateMeta
        inv.setItem(newTagSlot, tagCreate)

        for (tag in tags) {
            val item = ItemStack(Material.PAPER)
            val meta = item.itemMeta
            val lore = mutableListOf<Component>()
            meta.displayName(ColorUtils().convert("<gold>$tag"))

            lore.add(ColorUtils().convert(" "))
            lore.add(ColorUtils().convert("<green>Left click to edit"))
            lore.add(ColorUtils().convert("<red>Right click to remove"))

            meta.lore(lore)
            item.itemMeta = meta

            inv.addItem(item)
        }

        val backItem = ItemStack(Material.TIPPED_ARROW)
        val backMeta = backItem.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        backItem.itemMeta = backMeta
        inv.setItem(backSlot, backItem)

        inv = GUIFiller().fillInventory(inv, onlySlots = tailSlots)

        player.openInventory(inv)
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
                player.sendMessage(ColorUtils().convert("<red>Cancelled!"))
                reopenInventory(player, "manage", fusion.fileName)
            }

            playerListen[player.uniqueId] == "fusionName" -> {
                if (strippedMsg.length > 32) {
                    player.sendMessage(ColorUtils().convert("<red>That name is too long!"))
                } else {
                    fusion.name = stringMsg
                    player.sendMessage(ColorUtils().convert("<green>Updated name to: <aqua>$stringMsg"))
                    reopenInventory(player, "manage", fusion.fileName)
                }
            }

            playerListen[player.uniqueId] == "fusionDescription" -> {
                if (strippedMsg.length > 256) {
                    player.sendMessage(ColorUtils().convert("<red>That description is too long!"))
                } else {
                    fusion.description = stringMsg
                    player.sendMessage(ColorUtils().convert("<green>Updated description to: <white>$stringMsg"))
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