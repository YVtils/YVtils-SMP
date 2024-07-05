package yv.tils.smp.mods.fusionCrafting.creator

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.utils.color.ColorUtils

class Inventory {
    fun openInventory(player: Player) {
        var inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Fusion Creator"))

        inv = generateContent(inv)

        player.openInventory(inv)
    }

    private fun generateContent(inv: Inventory): Inventory {
        // 0 = Toggle of fusion
        // 10 = Display Item in overview
        // 11 = Name of fusion
        // 12 = Description of fusion
        // 13 = TAGS of fusion
        // 14 = Fusion Inv to edit
        // 18 = Back to overview
        // 26 = Delete fusion
        // Rest = DARK_GLASS_PANE

        val fusionToggled = true // TODO: Implement method to get toggle state

        var toggleSwitch: ItemStack
        var toggleSwitchMeta: ItemMeta

        try {
            when (fusionToggled) {
                true -> {
                    toggleSwitch = ItemStack(Material.LIME_DYE)
                    toggleSwitchMeta = toggleSwitch.itemMeta
                    toggleSwitchMeta.displayName(ColorUtils().convert("<red>DISABLE FUSION"))
                }
                false -> {
                    toggleSwitch = ItemStack(Material.RED_DYE)
                    toggleSwitchMeta = toggleSwitch.itemMeta
                    toggleSwitchMeta.displayName(ColorUtils().convert("<green>ENABLE FUSION"))
                }
            }
        } catch (_: Exception) { // TODO: Change type of exception
            toggleSwitch = ItemStack(Material.GRAY_DYE)
            toggleSwitchMeta = toggleSwitch.itemMeta
            toggleSwitchMeta.displayName(ColorUtils().convert("<yellow>Failed to fetch toggle state"))
        }

        toggleSwitch.itemMeta = toggleSwitchMeta

        inv.setItem(0, toggleSwitch)

        var thumbnail: ItemStack
        var displayMeta: ItemMeta

        try {
            thumbnail = ItemStack(Material.DIAMOND) // TODO: Implement get method of thumbnail item
            displayMeta = thumbnail.itemMeta
            displayMeta.displayName(ColorUtils().convert("<gold>Display Item"))
        } catch (_: Exception) { // TODO: Change type of exception
            thumbnail = ItemStack(Material.ITEM_FRAME)
            displayMeta = thumbnail.itemMeta
            displayMeta.displayName(ColorUtils().convert("<yellow>Failed to fetch display item"))
        }

        thumbnail.itemMeta = displayMeta

        inv.setItem(10, thumbnail)

        val displayName = ItemStack(Material.NAME_TAG)
        val displayNameMeta = displayName.itemMeta
        var displayNameContent: String

        try {
            displayNameContent = "" // TODO: Implement logic
        } catch (_: Exception) {
            displayNameContent = ""
        }

        displayNameMeta.displayName(ColorUtils().convert(displayNameContent))
        displayName.itemMeta = displayNameMeta

        inv.setItem(11, displayName)

        val description = ItemStack(Material.MAP)
        val descriptionMeta = description.itemMeta
        val descriptionList: MutableList<String> = mutableListOf()

        descriptionMeta.displayName(ColorUtils().convert("<gold>Description"))

        try {
            descriptionList.plus(".") // TODO: Implement logic
        } catch (_: Exception) {}

        for (descLine in descriptionList) {
            descriptionMeta.lore()?.add(ColorUtils().convert(descLine))
        }

        description.itemMeta = descriptionMeta

        inv.setItem(12, description)

        val tags = ItemStack(Material.BOOK)
        val tagsMeta = tags.itemMeta
        val tagsContent = ""

        tagsMeta.displayName(ColorUtils().convert("<gold>Filter Tags"))

        tags.itemMeta = tagsMeta
        inv.setItem(13, tags)

        val fusionInv = ItemStack(Material.BARREL)
        val fusionInvMeta = fusionInv.itemMeta

        fusionInvMeta.displayName(ColorUtils().convert("<gold>Edit Fusion"))

        fusionInv.itemMeta = fusionInvMeta
        inv.setItem(14, fusionInv)

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
}