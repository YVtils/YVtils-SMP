package yv.tils.smp.mods.quests

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import yv.tils.smp.mods.questSystem.QuestLoader
import yv.tils.smp.mods.questSystem.QuestLoader.Companion.quests
import yv.tils.smp.utils.color.ColorUtils

class QuestGUICommand {
    val command = commandTree("quests") {
        withPermission("yvtils.smp.quests.command")
        withUsage("/quests")
        withAliases("q", "ccr")

        playerExecutor { sender, args ->
            generateGUI(sender)
        }
    }

    private fun generateGUI(player: Player) {
        val questSlots: List<Int> = listOf(11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 8, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42)

        val inv = Bukkit.createInventory(player, 54)

        val nextPage = ItemStack(Material.TIPPED_ARROW)
        val nextPageMeta = nextPage.itemMeta as PotionMeta
        nextPageMeta.color = Color.fromRGB(150, 85, 95)
        nextPageMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        nextPage.itemMeta = nextPageMeta
        inv.setItem(53, nextPage)

        val lastPage = ItemStack(Material.TIPPED_ARROW)
        val lastPageMeta = nextPage.itemMeta as PotionMeta
        lastPageMeta.color = Color.fromRGB(85, 150, 95)
        lastPageMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        lastPage.itemMeta = lastPageMeta
        inv.setItem(45, nextPage)

        for (quest in quests) {
            println(quest)
            //DisplayItem

            val item = ItemStack(Material.DIRT)
            val itemMeta = item.itemMeta
            itemMeta.displayName(ColorUtils().convert(quest.key))

            item.setItemMeta(itemMeta)

            for (slot in questSlots) {
                if (inv.getItem(slot) == null) {
                    inv.setItem(slot, item)
                }
            }
        }
    }
}