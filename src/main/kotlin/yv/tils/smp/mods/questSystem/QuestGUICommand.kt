package yv.tils.smp.mods.questSystem

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import yv.tils.smp.mods.questSystem.QuestLoader.Companion.questThumbnails
import yv.tils.smp.utils.color.ColorUtils

class QuestGUICommand {
    val command = commandTree("quest") {
        withPermission("yvtils.smp.quest.command")
        withUsage("/quest")
        withAliases("q", "ccr")

        playerExecutor { sender, args ->
            generateGUI(sender)
        }
    }

    private fun generateGUI(player: Player) {
        val questSlots: List<Int> = listOf(11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42)

        val inv = Bukkit.createInventory(player, 54, ColorUtils().convert("<gold>Quests"))

        loop@for (quest in questThumbnails) {
            for (slot in questSlots) {
                if (inv.getItem(slot) == null) {
                    inv.setItem(slot, quest.value)
                    break
                }
                if (slot == questSlots.last()) {
                    val nextPage = ItemStack(Material.TIPPED_ARROW)
                    val nextPageMeta = nextPage.itemMeta as PotionMeta
                    nextPageMeta.displayName(ColorUtils().convert("<green>Next Page"))
                    nextPageMeta.color = Color.fromRGB(85, 150, 95)
                    nextPageMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                    nextPage.itemMeta = nextPageMeta
                    inv.setItem(53, nextPage)

                    val lastPage = ItemStack(Material.TIPPED_ARROW)
                    val lastPageMeta = nextPage.itemMeta as PotionMeta
                    lastPageMeta.displayName(ColorUtils().convert("<red>Last Page"))
                    lastPageMeta.color = Color.fromRGB(150, 85, 95)
                    lastPageMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                    lastPage.itemMeta = lastPageMeta
                    inv.setItem(45, lastPage)

                    val pageCount = ItemStack(Material.TIPPED_ARROW, 1)
                    val pageCountMeta = nextPage.itemMeta as PotionMeta
                    pageCountMeta.displayName(ColorUtils().convert("<yellow>Page: 1"))
                    pageCountMeta.color = Color.fromRGB(200, 175, 20)
                    pageCountMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                    pageCount.itemMeta = pageCountMeta
                    inv.setItem(49, pageCount)

                    break@loop
                }
            }
        }

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        outerFiller.itemMeta = fillerMeta

        val innerFiller = ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
        innerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                if (questSlots.contains(i)) {
                    inv.setItem(i, innerFiller)
                    continue
                }

                inv.setItem(i, outerFiller)
            }
        }

        player.openInventory(inv)
    }

    fun questGUI(player: Player, quest: String) {
        val inv = Bukkit.createInventory(null, 54)

        val inputSlots: List<Int> = listOf(10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31)
        val outputSlots: List<Int> = listOf(15, 16, 24, 25, 33, 34)
        val acceptSlots = listOf(47, 48, 49, 50, 51, 52)
        val backSlot = 45

        val filler = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = filler.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        filler.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inputSlots.contains(i) || outputSlots.contains(i) || acceptSlots.contains(i) || i == backSlot) {
                continue
            }

            inv.setItem(i, filler)
        }

        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(85, 150, 95)
        backMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        back.itemMeta = backMeta
        inv.setItem(backSlot, back)

        val accept = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val acceptMeta = accept.itemMeta
        acceptMeta.displayName(ColorUtils().convert(" "))
        acceptMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        accept.itemMeta = acceptMeta

        for (slot in acceptSlots) {
            inv.setItem(slot, accept)
        }

        generateInput(inputSlots, quest, inv)
        generateOutput(quest, inv)

        player.openInventory(inv)
    }

    private fun generateInput(slots: List<Int>, quest: String, inv: Inventory) {
        val items = mutableListOf<ItemStack>()

        items.add(ItemStack(Material.DIRT))

        for (slot in slots) {
            if (items.isEmpty()) {
                break
            }

            val item = items[0]
            items.removeAt(0)

            val itemMeta = item.itemMeta
            itemMeta.displayName(ColorUtils().convert(" "))
            itemMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            item.itemMeta = itemMeta

            inv.setItem(slot, item)
        }
    }

    private fun generateOutput(quest: String, inv: Inventory) {
        val items = mutableListOf<ItemStack>()

        items.add(ItemStack(Material.DIRT))

        val neededSlots = items.size

        when(neededSlots) {
            1 -> {
                //Use slot 25
            }
            2 -> {
                //Use slots 24 and 25
            }
            3 -> {
                //Use slots 16, 25, and 34
            }
            4 -> {
                //Use slots 16, 24, 25, and 34
            }
            5 -> {
                //Use slots 15, 16, 25, 33, and 34
            }
            else -> {
                //Use slots 15, 16, 24, 25, 33, and 34
            }
        }
    }
}