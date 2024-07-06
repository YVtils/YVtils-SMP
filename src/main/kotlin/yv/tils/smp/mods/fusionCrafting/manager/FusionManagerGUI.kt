package yv.tils.smp.mods.fusionCrafting.manager

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.mods.fusionCrafting.FusionOverview
import yv.tils.smp.utils.color.ColorUtils
import java.io.File

class FusionManagerGUI {
    data class Fusion(
        val state: Boolean,
        val thumbnail: ItemStack,
        val name: String,
        val description: String,
        val tags: MutableList<String>,
        val fusionInv: Inventory,
        val fileName: String
    )

    fun openInventory(player: Player, fusion: String) {
        var inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Fusion Manager"))

        println("Opening Fusion Manager for $fusion")

        inv = generateContent(inv, collectData(fusion))

        player.openInventory(inv)
    }

    private fun collectData(fusionName: String): Fusion {
        var state = false
        var thumbnail = ItemStack(Material.ITEM_FRAME)
        var name = ""
        var description = ""
        val tags: MutableList<String> = mutableListOf()
        var fusionInv = FusionCraftManage().buildGUI()
        var fileName = ""

        if (fusionName == "null") {
            return Fusion(state, thumbnail, name, description, tags, fusionInv, fileName)
        }

        val file = File(YVtils.instance.dataFolder.path, "fusions/$fusionName.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        fileName = fusionName
        state = ymlFile.getBoolean("enabled")
        thumbnail = ItemStack(Material.valueOf(ymlFile.getString("displayItem") ?: "ITEM_FRAME"))
        name = ymlFile.getString("name") ?: ""
        description = ymlFile.getString("description") ?: ""

        for (tag in ymlFile.getString("tags")!!.split(";")) {
            var newTag = tag

            newTag = newTag.replace(" ", "")
            newTag = newTag.replace(";", " ")

            if (newTag == "") continue

            tags.plus(newTag)
        }

        fusionInv = FusionCraftManage().buildGUI()

        return Fusion(state, thumbnail, name, description, tags, fusionInv, fileName)
    }

    fun setData(fusion: Fusion) {
        val file = File(YVtils.instance.dataFolder.path, "fusions/${fusion.fileName}.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        ymlFile.set("enabled", fusion.state)
        ymlFile.set("displayItem", fusion.thumbnail.type.toString())
        ymlFile.set("name", fusion.name)
        ymlFile.set("description", fusion.description)
        ymlFile.set("tags", fusion.tags.joinToString(";"))

        val fusionInv = FusionCraftManage().parseGUI(fusion.fusionInv)

        for (fusionItem in fusionInv) {
            val key = fusionItem.key
            val value = fusionItem.value

            // TODO: Implement logic to set data
            if (key.startsWith("input.")) {
                println("Input: $value")
            } else if (key.startsWith("output.")) {
                println("Output: $value")
            }
        }
    }

    private fun generateContent(inv: Inventory, fusion: Fusion): Inventory {
        val fusionToggled = fusion.state

        val toggleSwitch: ItemStack
        val toggleSwitchMeta: ItemMeta

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

        toggleSwitch.itemMeta = toggleSwitchMeta
        inv.setItem(0, toggleSwitch)


        val thumbnail: ItemStack = fusion.thumbnail
        val displayMeta: ItemMeta = thumbnail.itemMeta
        displayMeta.displayName(ColorUtils().convert("<gold>Display Item"))

        thumbnail.itemMeta = displayMeta
        inv.setItem(11, thumbnail)

        val displayName = ItemStack(Material.NAME_TAG)
        val displayNameMeta = displayName.itemMeta
        val displayNameContent = fusion.name
        val displayNameLore = mutableListOf<Component>()

        displayNameMeta.displayName(ColorUtils().convert("<gold>Display Name"))

        displayNameLore.add(ColorUtils().convert(" "))
        displayNameLore.add(ColorUtils().convert(displayNameContent))

        displayMeta.lore(displayNameLore)
        displayName.itemMeta = displayNameMeta
        inv.setItem(12, displayName)

        val description = ItemStack(Material.MAP)
        val descriptionMeta = description.itemMeta
        val descriptionList: MutableList<String> = mutableListOf()
        val descriptionLore = mutableListOf<Component>()

        descriptionMeta.displayName(ColorUtils().convert("<gold>Description"))

        descriptionList.add(fusion.description)

        for (descLine in descriptionList) {
            descriptionLore.add(ColorUtils().convert(descLine))
        }

        descriptionMeta.lore(descriptionLore)
        description.itemMeta = descriptionMeta
        inv.setItem(13, description)

        val tags = ItemStack(Material.BOOK)
        val tagsMeta = tags.itemMeta
        val tagsLore = mutableListOf<Component>()

        tagsMeta.displayName(ColorUtils().convert("<gold>Filter Tags"))

        for (tag in fusion.tags) {
            tagsLore.add(ColorUtils().convert(tag))
        }

        tagsMeta.lore(tagsLore)
        tags.itemMeta = tagsMeta
        inv.setItem(14, tags)

        val fusionInv = ItemStack(Material.BARREL)
        val fusionInvMeta = fusionInv.itemMeta

        fusionInvMeta.displayName(ColorUtils().convert("<gold>Edit Fusion"))

        fusionInv.itemMeta = fusionInvMeta
        inv.setItem(15, fusionInv)

        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        back.itemMeta = backMeta

        inv.setItem(18, back)

        val delete = ItemStack(Material.BARRIER)
        val deleteMeta = delete.itemMeta

        deleteMeta.displayName(ColorUtils().convert("<red>Delete Fusion"))

        delete.itemMeta = deleteMeta
        inv.setItem(26, delete)

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