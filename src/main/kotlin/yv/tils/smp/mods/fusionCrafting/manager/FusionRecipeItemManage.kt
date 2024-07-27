package yv.tils.smp.mods.fusionCrafting.manager

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.utils.color.ColorUtils

class FusionRecipeItemManage {
    companion object {
        val fusionRecipeItemEdit = mutableMapOf<Player, FusionRecipeItem>()
    }

    data class FusionRecipeItem(
        var material: Material,
        var name: String,
        var amount: Int,
        var lore: List<Component>,
        var data: MutableList<String>,
    )

    fun openInventory(player: Player, item: ItemStack, type: String) {
        var inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Edit Item"))

        val fusionItem = parseItem(item)

        inv = generateContent(inv, fusionItem, type)

        player.openInventory(inv)
    }

    fun tfIDK(): MutableMap<String, Any> { // TODO: Rename this function
        return mutableMapOf()
    }

    private fun generateContent(inv: Inventory, fusion: FusionRecipeItem, type: String): Inventory {
        var slots = listOf(11, 12, 13, 14, 15)

        if (type == "output") {
            val thumbnail = ItemStack(fusion.material)
            val displayMeta: ItemMeta = thumbnail.itemMeta
            displayMeta.displayName(ColorUtils().convert("<gold>Display Item"))

            thumbnail.itemMeta = displayMeta
            inv.setItem(slots[0], thumbnail)
            slots = slots.drop(1)
        }

        if (type == "input") {
            val acceptedItems = ItemStack(Material.BARREL)
            val acceptedItemsMeta = acceptedItems.itemMeta
            val acceptedItemsLore = mutableListOf<Component>()

            acceptedItemsMeta.displayName(ColorUtils().convert("<gold>Usable Items"))

            acceptedItemsLore.add(ColorUtils().convert(" "))
            acceptedItemsLore.add(ColorUtils().convert("<gray>Click to view"))

            acceptedItemsMeta.lore(acceptedItemsLore)
            acceptedItems.itemMeta = acceptedItemsMeta
            inv.setItem(slots[0], acceptedItems)
            slots = slots.drop(1)
        }

        val displayName = ItemStack(Material.NAME_TAG)
        val displayNameMeta = displayName.itemMeta
        val displayNameContent = fusion.name
        val displayNameLore = mutableListOf<Component>()

        displayNameMeta.displayName(ColorUtils().convert("<gold>Display Name"))

        displayNameLore.add(ColorUtils().convert(" "))
        displayNameLore.add(ColorUtils().convert("<aqua>$displayNameContent"))

        displayNameMeta.lore(displayNameLore)
        displayName.itemMeta = displayNameMeta
        inv.setItem(slots[0], displayName)
        slots = slots.drop(1)

        val amountItem = ItemStack(Material.PAPER)
        val amountItemMeta = amountItem.itemMeta
        val amountItemLore = mutableListOf<Component>()

        amountItemMeta.displayName(ColorUtils().convert("<gold>Amount"))

        amountItemLore.add(ColorUtils().convert(" "))
        amountItemLore.add(ColorUtils().convert("<aqua>${fusion.amount}"))

        amountItemMeta.lore(amountItemLore)
        amountItem.itemMeta = amountItemMeta
        inv.setItem(slots[0], amountItem)
        slots = slots.drop(1)

        if (type == "output") {
            val description = ItemStack(Material.MAP)
            val descriptionMeta = description.itemMeta
            val descriptionLore = mutableListOf<Component>()

            descriptionMeta.displayName(ColorUtils().convert("<gold>Item Lore"))

            descriptionLore.add(ColorUtils().convert(" "))
            for (descLine in fusion.lore) {
                descriptionLore.add(ColorUtils().convert("<white>${ColorUtils().convert(descLine)}"))
            }

            descriptionMeta.lore(descriptionLore)
            description.itemMeta = descriptionMeta
            inv.setItem(slots[0], description)
            slots = slots.drop(1)
        }

        val tags = ItemStack(Material.BOOK)
        val tagsMeta = tags.itemMeta
        val tagsLore = mutableListOf<Component>()

        tagsMeta.displayName(ColorUtils().convert("<gold>Data Tags"))

        tagsLore.add(ColorUtils().convert(" "))
        for (tag in fusion.data) {
            tagsLore.add(ColorUtils().convert("<gray>$tag"))
        }

        tagsMeta.lore(tagsLore)
        tags.itemMeta = tagsMeta
        inv.setItem(slots[0], tags)
        slots = slots.drop(1)

        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        back.itemMeta = backMeta

        inv.setItem(18, back)

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

        for (i in 0 until inv.size) {
            val item = inv.getItem(i) ?: continue
            item.itemMeta.persistentDataContainer.set(FusionKeys.FUSION_GUI.key, PersistentDataType.STRING, "fusion")
            inv.setItem(i, item)
        }

        return inv
    }

    private fun parseItem(item: ItemStack): FusionRecipeItem {
        val material = item.type
        val name = item.itemMeta.persistentDataContainer.get(FusionKeys.FUSION_ITEMNAME.key, PersistentDataType.STRING)
        val amount = item.amount
        val lore = item.itemMeta.lore()
        val data: MutableList<String> = mutableListOf()

        if (name == null || lore == null) {
            return FusionRecipeItem(Material.BARRIER, "null", 0, listOf(Component.text("null")), mutableListOf("null"))
        }

        for (line in lore) {
            val stringLine = ColorUtils().convert(line)

            if (stringLine.contains("Item Data:")) {
                val split = stringLine.split(": ")
                val dataTags = split[1].split(", ")

                for (tag in dataTags) {
                    data.add(tag)
                }
            }
        }

        return FusionRecipeItem(material, name, amount, lore, data)
    }
}