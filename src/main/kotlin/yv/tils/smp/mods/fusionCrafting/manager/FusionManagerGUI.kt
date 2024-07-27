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
import yv.tils.smp.mods.fusionCrafting.FusionLoader
import yv.tils.smp.utils.color.ColorUtils
import java.io.File
import java.util.UUID

class FusionManagerGUI {
    companion object {
        val playerManager = mutableMapOf<UUID, Fusion>()
    }

    data class Fusion(
        var state: Boolean,
        var thumbnail: ItemStack,
        var name: String,
        var description: String,
        var tags: MutableList<String>,
        var fusionInv: MutableMap<String, Any>,
        var fileName: String
    )

    fun openInventory(player: Player, fusion: String) {
        var inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Fusion Manager"))

        val data = if (playerManager.containsKey(player.uniqueId)) {
            playerManager[player.uniqueId]!!
        } else {
            collectData(fusion)
        }

        inv = generateContent(inv, data)

        playerManager[player.uniqueId] = data
        player.openInventory(inv)
    }

    private fun collectData(fusionName: String): Fusion {
        var state = false
        var thumbnail = ItemStack(Material.ITEM_FRAME)
        var name = ""
        var description = ""
        val tags = mutableListOf<String>()
        var fusionInv = mutableMapOf<String, Any>()
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

            tags.add(newTag)
        }

        fusionInv = FusionLoader().loadFusion(fusionName)

        return Fusion(state, thumbnail, name, description, tags, fusionInv, fileName)
    }

    // TODO: Saves in file, but does not work in lifetime without restart
    fun setData(fusion: Fusion) {
        val file = File(YVtils.instance.dataFolder.path, "fusions/${fusion.fileName}.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        ymlFile.set("enabled", fusion.state)
        ymlFile.set("displayItem", fusion.thumbnail.type.toString())
        ymlFile.set("name", fusion.name)
        ymlFile.set("description", fusion.description)
        ymlFile.set("tags", fusion.tags.joinToString(";"))

        val fusionInv = fusion.fusionInv

        for (fusionItem in fusionInv) {
            val key = fusionItem.key
            val value = fusionItem.value

            // TODO: Implement logic to set data
            /*
            [23:15:36 INFO]: Input: [{item=ITEM_FRAME}, {amount=1}, {data=}]
[23:15:36 INFO]: Input: [{item=GLOW_ITEM_FRAME}, {amount=1}, {data=}]
[23:15:36 INFO]: Input: [{item=Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=White_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Orange_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Magenta_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Light_Blue_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Yellow_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Lime_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Pink_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Gray_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Light_Gray_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Cyan_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Purple_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Blue_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Brown_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Green_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Red_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Input: [{item=Black_Stained_Glass_Pane}, {amount=4}, {data=}]
[23:15:36 INFO]: Output: [{item=ITEM_FRAME}, {amount=4}, {name=<gold>Invisible Item Frame}, {lore=<white>Place this Item Frame and it will be invisible! <newline><red>Empty Item Frames will be destroyed and dropped after one minute!}, {data=Invisible}]

             */
            if (key.startsWith("input.")) {
                println("Input: $value")
//                ymlFile.set("input", value) // TODO: Should work like this but not sure
            } else if (key.startsWith("output.")) {
                println("Output: $value")
//                ymlFile.set("output", value) // TODO: Should work like this but not sure
            }
        }

        ymlFile.save(file)
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
        displayNameLore.add(ColorUtils().convert("<aqua>$displayNameContent"))

        displayNameMeta.lore(displayNameLore)
        displayName.itemMeta = displayNameMeta
        inv.setItem(12, displayName)

        val description = ItemStack(Material.MAP)
        val descriptionMeta = description.itemMeta
        val descriptionList: MutableList<String> = mutableListOf()
        val descriptionLore = mutableListOf<Component>()

        descriptionMeta.displayName(ColorUtils().convert("<gold>Description"))

        descriptionList.add(fusion.description)

        descriptionLore.add(ColorUtils().convert(" "))
        for (descLine in descriptionList) {
            descriptionLore.add(ColorUtils().convert("<white>$descLine"))
        }

        descriptionMeta.lore(descriptionLore)
        description.itemMeta = descriptionMeta
        inv.setItem(13, description)

        val tags = ItemStack(Material.BOOK)
        val tagsMeta = tags.itemMeta
        val tagsLore = mutableListOf<Component>()

        tagsMeta.displayName(ColorUtils().convert("<gold>Filter Tags"))

        tagsLore.add(ColorUtils().convert(" "))
        for (tag in fusion.tags) {
            tagsLore.add(ColorUtils().convert("<gray>$tag"))
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