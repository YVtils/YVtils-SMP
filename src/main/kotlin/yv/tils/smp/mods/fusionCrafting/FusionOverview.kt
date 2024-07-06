package yv.tils.smp.mods.fusionCrafting

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.stringArgument
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.FusionLoader.Companion.fusionThumbnails
import yv.tils.smp.utils.color.ColorUtils
import java.util.*

class FusionOverview {
    val command = commandTree("fusion") {
        withPermission("yvtils.smp.command.fusion")
        withUsage("/fusion")
        withAliases("ccr", "fc")

        stringArgument("manage", true) {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "manage"
                )
            )
            withPermission("yvtils.smp.fusion.manage")

            playerExecutor { sender, args ->
                if (args[0] != "manage") {
                    openOverview(sender, "<gold>Fusion Crafting")
                } else {
                    openOverview(sender, "<red>Fusion Management")
                }
            }
        }
    }

    fun openOverview(player: Player, invTitle: String, page: Int = 1, tag: String = "") {
        if (page < 1) return
        if (fusionThumbnails.size - 24 * (page - 1) < 0) return

        val fusionSlots: List<Int> =
            listOf(11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42)

        val inv = Bukkit.createInventory(player, 54, ColorUtils().convert(invTitle))

        loop@ for (i in 24 * (page - 1) until fusionThumbnails.size) {
            val (key, value) = fusionThumbnails.entries.elementAt(i)
            for (slot in fusionSlots) {
                if (inv.getItem(slot) == null) {
                    if (FusionLoader.disabledFusions.contains(key)) {
                        break
                    }

                    if (tag != "" && !FusionLoader.tagMap[tag]!!.contains(key)) {
                        break
                    }

                    inv.setItem(slot, value)
                    break
                }
                if (slot == fusionSlots.last() || page != 1) {
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

                    val pageCount = ItemStack(Material.PLAYER_HEAD, 1)
                    val pageCountMeta = pageCount.itemMeta as SkullMeta
                    val gameProfile = GameProfile(UUID.randomUUID(), "PageHead")

                    if (page > 9) {
                        gameProfile.properties.put("textures", Property("textures", FusionGUIHeads.valueOf("PAGE_PLUS").texture))
                    } else {
                        gameProfile.properties.put("textures", Property("textures", FusionGUIHeads.valueOf("PAGE_${page}").texture))
                    }

                    try {
                        val profileField = pageCountMeta.javaClass.getDeclaredField("profile")
                        profileField.isAccessible = true
                        profileField.set(pageCountMeta, gameProfile)
                    } catch (e: NoSuchFieldException) {
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }

                    pageCountMeta.displayName(ColorUtils().convert("<yellow>Page: $page"))
                    pageCountMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                    pageCount.itemMeta = pageCountMeta
                    inv.setItem(4, pageCount)

                    if (slot == fusionSlots.last()) {
                        break@loop
                    }
                }
            }
        }

        val filter = ItemStack(Material.HOPPER)
        val filterMeta = filter.itemMeta
        filterMeta.displayName(ColorUtils().convert("<yellow>Filter"))
        filterMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)

        val filterLore = mutableListOf<Component>()
        filterLore.add(ColorUtils().convert("<gray>Click to filter by tag"))
        filterLore.add(ColorUtils().convert(" "))

        var tags = FusionLoader.tagMap.keys.toMutableList()
        tags = tags.sorted().toMutableList()

        if (tag != "") {
            filterLore.add(ColorUtils().convert("<gray>None"))
            for (tagEntry in tags) {
                if (tagEntry == tag) {
                    filterLore.add(ColorUtils().convert("<gold>${tagEntry}"))
                    continue
                }
                filterLore.add(ColorUtils().convert("<gray>${tagEntry}"))
            }

            filterMeta.persistentDataContainer.set(FusionKeys.FUSION_CURRENT_FILTER.key, PersistentDataType.STRING, tag)
        } else {
            filterLore.add(ColorUtils().convert("<gold>None"))
            for (tagEntry in tags) {
                filterLore.add(ColorUtils().convert("<gray>${tagEntry}"))
            }

            filterMeta.persistentDataContainer.set(FusionKeys.FUSION_CURRENT_FILTER.key, PersistentDataType.STRING, "None")
        }


        filterMeta.lore(filterLore)

        filter.itemMeta = filterMeta
        inv.setItem(49, filter)

        if (invTitle == "<red>Fusion Management") {
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

            pageCountMeta.displayName(ColorUtils().convert("<green>Create Fusion"))
            pageCountMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
            pageCount.itemMeta = pageCountMeta
            inv.setItem(8, pageCount)
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
                if (fusionSlots.contains(i)) {
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