package yv.tils.smp.mods.fusionCrafting.manager

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.FusionGUIHeads
import yv.tils.smp.utils.color.ColorUtils
import java.util.*

class FusionCraftManage {
    companion object {
        val playerListen = mutableMapOf<UUID, String>()
    }

    fun buildGUI(): Inventory {
        val inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Fusion Manager"))

        return inv
    }

    fun editThumbnailItem(player: Player, item: ItemStack) {
        val inv = Bukkit.createInventory(null, 9, ColorUtils().convert("<gold>Edit Thumbnail"))

        if (item.type != Material.DIRT) {
            inv.setItem(4, item)
        }

        val accept = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val acceptMeta = accept.itemMeta
        acceptMeta.displayName(ColorUtils().convert("<green>Change Thumbnail"))
        acceptMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)

        accept.itemMeta = acceptMeta
        inv.setItem(0, accept)

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        outerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, outerFiller)
            }
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

        val siteSlots: MutableList<Int> = mutableListOf(
            tailSlots[0],
            tailSlots[8]
        )

        val newTagSlot = tailSlots[4]

        val inv = Bukkit.createInventory(null, guiSize, ColorUtils().convert("<gold>Filter Tags"))

        if (tags.size > 45) {
            val nextPage = ItemStack(Material.PLAYER_HEAD, 1)
            val nextPageMeta = nextPage.itemMeta as SkullMeta
            val nextPageGameProfile = GameProfile(UUID.randomUUID(), "PageHead")

            nextPageGameProfile.properties.put("textures", Property("textures", FusionGUIHeads.PAGE_NEXT.texture))

            try {
                val profileField = nextPageMeta.javaClass.getDeclaredField("profile")
                profileField.isAccessible = true
                profileField.set(nextPageMeta, nextPageGameProfile)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            nextPageMeta.displayName(ColorUtils().convert("<green>Next page"))
            nextPageMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
            nextPage.itemMeta = nextPageMeta
            inv.setItem(siteSlots[1], nextPage)

            val lastPage = ItemStack(Material.PLAYER_HEAD, 1)
            val lastPageMeta = lastPage.itemMeta as SkullMeta
            val lastPageGameProfile = GameProfile(UUID.randomUUID(), "PageHead")

            lastPageGameProfile.properties.put("textures", Property("textures", FusionGUIHeads.PAGE_BACK.texture))

            try {
                val profileField = lastPageMeta.javaClass.getDeclaredField("profile")
                profileField.isAccessible = true
                profileField.set(lastPageMeta, lastPageGameProfile)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            lastPageMeta.displayName(ColorUtils().convert("<red>Page back"))
            lastPageMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
            lastPage.itemMeta = lastPageMeta
            inv.setItem(siteSlots[0], lastPage)
        }

        val pageCount = ItemStack(Material.PLAYER_HEAD, 1)
        val pageCountMeta = pageCount.itemMeta as SkullMeta
        val gameProfile = GameProfile(UUID.randomUUID(), "PageHead")
        gameProfile.properties.put("textures", Property("textures", FusionGUIHeads.ADD_FUSION.texture))

        try {
            val profileField = pageCountMeta.javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField.set(pageCountMeta, gameProfile)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        pageCountMeta.displayName(ColorUtils().convert("<green>Add Tag"))
        pageCountMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        pageCount.itemMeta = pageCountMeta
        inv.setItem(newTagSlot, pageCount)

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

        if (stringMsg.lowercase() == "c" || stringMsg.lowercase() == "cancel") {
            player.sendMessage(ColorUtils().convert("<red>Cancelled!"))
            playerListen.remove(player.uniqueId)
            reopenInventory(player, "manage", fusion.fileName)
            return
        }

        if (playerListen[player.uniqueId] == "fusionName") {
            if (strippedMsg.length > 32) {
                player.sendMessage(ColorUtils().convert("<red>That name is too long!"))
                return
            }

            fusion.name = stringMsg
            player.sendMessage(ColorUtils().convert("<green>Updated name to: <aqua>$stringMsg"))
            playerListen.remove(player.uniqueId)
            reopenInventory(player, "manage", fusion.fileName)
        } else if (playerListen[player.uniqueId] == "fusionDescription") {
            if (strippedMsg.length > 256) {
                player.sendMessage(ColorUtils().convert("<red>That description is too long!"))
                return
            }

            fusion.description = stringMsg
            player.sendMessage(ColorUtils().convert("<green>Updated description to: <white>$stringMsg"))
            playerListen.remove(player.uniqueId)
            reopenInventory(player, "manage", fusion.fileName)
        } else if (playerListen[player.uniqueId]?.startsWith("fusionTagModify") == true) {
            val oldTag = playerListen[player.uniqueId]?.split(" - ")?.get(1)
            fusion.tags.remove(oldTag)
            fusion.tags.add(stringMsg)
            reopenInventory(player, "tags", fusion.fileName)
        } else if (playerListen[player.uniqueId] == "fusionTagAdd") {
            fusion.tags.add(stringMsg)
            reopenInventory(player, "tags", fusion.fileName)
        }
    }

    private fun reopenInventory(player: Player, inv: String, fileName: String) {
        Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
            if (inv == "manage") FusionManagerGUI().openInventory(player, fileName)
            if (inv == "tags") filterTagsGUI(player, FusionManagerGUI.playerManager[player.uniqueId]?.tags ?: mutableListOf())
        }, 1)
    }
}