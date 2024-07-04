package yv.tils.smp.mods.fusionCrafting.creator

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.mods.fusionCrafting.FusionLoader.Companion.fusionThumbnails
import yv.tils.smp.utils.color.ColorUtils

class FusionOverview {
    fun openOverview(player: Player) {
        val questSlots: List<Int> =
            listOf(11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42)

        val inv = Bukkit.createInventory(player, 54, ColorUtils().convert("<red>Fusion Management"))

        loop@ for (quest in fusionThumbnails) {
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
                    nextPageMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                    nextPage.itemMeta = nextPageMeta
                    inv.setItem(53, nextPage)

                    val lastPage = ItemStack(Material.TIPPED_ARROW)
                    val lastPageMeta = nextPage.itemMeta as PotionMeta
                    lastPageMeta.displayName(ColorUtils().convert("<red>Last Page"))
                    lastPageMeta.color = Color.fromRGB(150, 85, 95)
                    lastPageMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                    lastPage.itemMeta = lastPageMeta
                    inv.setItem(45, lastPage)

                    val pageCount = ItemStack(Material.TIPPED_ARROW, 1)
                    val pageCountMeta = nextPage.itemMeta as PotionMeta
                    pageCountMeta.displayName(ColorUtils().convert("<yellow>Page: 1"))
                    pageCountMeta.color = Color.fromRGB(200, 175, 20)
                    pageCountMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                    pageCount.itemMeta = pageCountMeta
                    inv.setItem(49, pageCount)

                    break@loop
                }
            }
        }

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
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

        for (i in 0 until inv.size) {
            val item = inv.getItem(i) ?: continue
            item.itemMeta.persistentDataContainer.set(FusionKeys.FUSION_GUI.key, PersistentDataType.STRING, "fusion")
            inv.setItem(i, item)
        }

        player.openInventory(inv)
    }
}