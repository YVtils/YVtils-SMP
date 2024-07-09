package yv.tils.smp.mods.fusionCrafting.manager

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import yv.tils.smp.YVtils
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

    fun listenForEdit(e: AsyncChatEvent) {
        val player = e.player
        val message = e.message()
        val coloredMsg = ColorUtils().convertChatMessage(message)
        val stringMsg = ColorUtils().convert(coloredMsg)
        val strippedMsg = ColorUtils().strip(stringMsg)


        if (!playerListen.contains(player.uniqueId)) return

        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return

        e.isCancelled = true

        if (stringMsg.lowercase() == "c") {
            player.sendMessage(ColorUtils().convert("<red>Cancelled!"))
            playerListen.remove(player.uniqueId)
            reopenInventory(player, fusion.fileName)
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
            reopenInventory(player, fusion.fileName)
        } else if (playerListen[player.uniqueId] == "fusionDescription") {
            if (strippedMsg.length > 256) {
                player.sendMessage(ColorUtils().convert("<red>That description is too long!"))
                return
            }

            fusion.description = stringMsg
            player.sendMessage(ColorUtils().convert("<green>Updated description to: <white>$stringMsg"))
            playerListen.remove(player.uniqueId)
            reopenInventory(player, fusion.fileName)
        }
    }

    private fun reopenInventory(player: Player, fileName: String) {
        Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
            FusionManagerGUI().openInventory(player, fileName)
        }, 1)
    }
}