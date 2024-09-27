package yv.tils.smp.mods.fusionCrafting

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.stringArgument
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.FusionLoader.Companion.fusionThumbnails
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.inventory.CustomHeads
import yv.tils.smp.utils.inventory.GUIFiller
import yv.tils.smp.utils.inventory.HeadUtils

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
        if (fusionThumbnails.size - 24 * (page - 1) <= 0) return

        val fusionSlots: List<Int> =
            listOf(11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 40, 41, 42)

        var inv = Bukkit.createInventory(player, 54, ColorUtils().convert(invTitle))

        loop@ for (i in 24 * (page - 1) until fusionThumbnails.size) {
            val (key, value) = fusionThumbnails.entries.elementAt(i)
            for (slot in fusionSlots) {
                if (inv.getItem(slot) == null) {
                    if (FusionLoader.disabledFusions.contains(key) && invTitle != "<red>Fusion Management") {
                        break
                    }

                    if (tag != "" && !FusionLoader.tagMap[tag]!!.contains(key)) {
                        break
                    }

                    inv.setItem(slot, value)
                    break
                }
                if (slot == fusionSlots.last() || page != 1) {
                    val nextPage = HeadUtils().createCustomHead(CustomHeads.NEXT_PAGE, "<green>Next page")
                    inv.setItem(53, nextPage)

                    val lastPage = HeadUtils().createCustomHead(CustomHeads.PREVIOUS_PAGE, "<red>Page back")
                    inv.setItem(45, lastPage)

                    val pageCount = if (page > 9) {
                        HeadUtils().createCustomHead(CustomHeads.NUMBER_NINE_PLUS, "<yellow>Page: $page")
                    } else {
                        HeadUtils().createCustomHead(CustomHeads.valueOf("NUMBER_$page"), "<yellow>Page: $page")
                    }

                    inv.setItem(4, pageCount)

                    if (slot == fusionSlots.last()) {
                        break@loop
                    }
                }
            }
        }

        val filter = ItemStack(Material.HOPPER)
        val filterMeta = filter.itemMeta
        filterMeta.displayName(Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_FILTER))
        filterMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)

        val filterLore = mutableListOf<Component>()
        filterLore.addAll(ColorUtils().handleLore(Language().getMessage(player.uniqueId, LangStrings.MODULE_FUSION_FILTER_LORE)))
        filterLore.add(ColorUtils().convert(" "))

        var tags = FusionLoader.tagMap.keys.toMutableList()
        tags = tags.sorted().toMutableList()

        if (tag != "") {
            filterLore.addAll(ColorUtils().handleLore("<gray>" + Language().getRawMessage(player.uniqueId, LangStrings.NONE)))
            for (tagEntry in tags) {
                if (tagEntry == tag) {
                    filterLore.add(ColorUtils().convert("<gold>${tagEntry}"))
                    continue
                }
                filterLore.add(ColorUtils().convert("<gray>${tagEntry}"))
            }

            filterMeta.persistentDataContainer.set(FusionKeys.FUSION_CURRENT_FILTER.key, PersistentDataType.STRING, tag)
        } else {
            filterLore.addAll(ColorUtils().handleLore("<gold>" + Language().getRawMessage(player.uniqueId, LangStrings.NONE)))
            for (tagEntry in tags) {
                filterLore.add(ColorUtils().convert("<gray>${tagEntry}"))
            }

            filterMeta.persistentDataContainer.set(FusionKeys.FUSION_CURRENT_FILTER.key, PersistentDataType.STRING, "None")
        }


        filterMeta.lore(filterLore)

        filter.itemMeta = filterMeta
        inv.setItem(49, filter)

        if (invTitle == "<red>Fusion Management") {
            val pageCount = HeadUtils().createCustomHead(CustomHeads.PLUS_CHARACTER, "<green>Create Fusion")
            inv.setItem(8, pageCount)
        }

        val blockedSlots = mutableListOf<Int>()
        blockedSlots.addAll(fusionSlots)

        inv = GUIFiller().fillInventory(inv, blockedSlots)

        for (slot in blockedSlots) {
            if (inv.getItem(slot) == null) {
                inv.setItem(slot, GUIFiller().secondaryFillerItem())
                continue
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