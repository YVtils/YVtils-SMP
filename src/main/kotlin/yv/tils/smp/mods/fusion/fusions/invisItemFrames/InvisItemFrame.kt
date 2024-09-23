package yv.tils.smp.mods.fusionCrafting.fusions.invisItemFrames

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.mods.fusionCrafting.enchantments.DataTags

class InvisItemFrame {
    fun configFile(ymlFile: YamlConfiguration): YamlConfiguration {
        ymlFile.addDefault("enabled", true)
        ymlFile.addDefault("name", "Invisible Item Frame")
        ymlFile.addDefault("displayItem", "ITEM_FRAME")
        ymlFile.addDefault("description", "<white>Craft an <gold>invisible <white>Item Frame")
        ymlFile.addDefault("tags", "Decoration; Display; Aesthetic;")

        val inputItems = inputItems()
        val outputItems = outputItems()

        for (i in 0 until inputItems.size) {
            for (j in 0 until inputItems.values.elementAt(i).size) {
                ymlFile.addDefault("input.${inputItems.keys.elementAt(i)}.$j", inputItems.values.elementAt(i)[j])
            }
        }

        for (i in 0 until outputItems.size) {
            ymlFile.addDefault("output.$i", outputItems[i])
        }

        ymlFile.options().copyDefaults(true)

        return ymlFile
    }

    private fun inputItems(): MutableMap<String, MutableList<MutableList<MutableMap<String, String>>>> {
        val items = mutableMapOf<String, MutableList<MutableList<MutableMap<String, String>>>>()

        val itemFrame = mutableMapOf("item" to "ITEM_FRAME")
        val glowItemFrame = mutableMapOf("item" to "GLOW_ITEM_FRAME")
        val itemFrameAmount = mutableMapOf("amount" to "1")
        val itemFrameData = mutableMapOf("data" to "")

        items["(Glow) Item Frame"] = mutableListOf(
            mutableListOf(itemFrame, itemFrameAmount, itemFrameData),
            mutableListOf(glowItemFrame, itemFrameAmount, itemFrameData)
        )

        val glassPane = mutableMapOf("item" to "Glass_Pane")
        val whiteGlassPane = mutableMapOf("item" to "White_Stained_Glass_Pane")
        val orangeGlassPane = mutableMapOf("item" to "Orange_Stained_Glass_Pane")
        val magentaGlassPane = mutableMapOf("item" to "Magenta_Stained_Glass_Pane")
        val lightBlueGlassPane = mutableMapOf("item" to "Light_Blue_Stained_Glass_Pane")
        val yellowGlassPane = mutableMapOf("item" to "Yellow_Stained_Glass_Pane")
        val limeGlassPane = mutableMapOf("item" to "Lime_Stained_Glass_Pane")
        val pinkGlassPane = mutableMapOf("item" to "Pink_Stained_Glass_Pane")
        val grayGlassPane = mutableMapOf("item" to "Gray_Stained_Glass_Pane")
        val lightGrayGlassPane = mutableMapOf("item" to "Light_Gray_Stained_Glass_Pane")
        val cyanGlassPane = mutableMapOf("item" to "Cyan_Stained_Glass_Pane")
        val purpleGlassPane = mutableMapOf("item" to "Purple_Stained_Glass_Pane")
        val blueGlassPane = mutableMapOf("item" to "Blue_Stained_Glass_Pane")
        val brownGlassPane = mutableMapOf("item" to "Brown_Stained_Glass_Pane")
        val greenGlassPane = mutableMapOf("item" to "Green_Stained_Glass_Pane")
        val redGlassPane = mutableMapOf("item" to "Red_Stained_Glass_Pane")
        val blackGlassPane = mutableMapOf("item" to "Black_Stained_Glass_Pane")
        val glassPaneAmount = mutableMapOf("amount" to "4")
        val glassPaneData = mutableMapOf("data" to "")

        items["Any sort of Glass Pane"] = mutableListOf(
            mutableListOf(glassPane, glassPaneAmount, glassPaneData),
            mutableListOf(whiteGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(orangeGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(magentaGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(lightBlueGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(yellowGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(limeGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(pinkGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(grayGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(lightGrayGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(cyanGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(purpleGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(blueGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(brownGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(greenGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(redGlassPane, glassPaneAmount, glassPaneData),
            mutableListOf(blackGlassPane, glassPaneAmount, glassPaneData)
        )

        return items
    }

    private fun outputItems(): MutableList<MutableList<MutableMap<String, String>>> {
        val items: MutableList<MutableList<MutableMap<String, String>>> = mutableListOf()

        val item = mutableMapOf("item" to "ITEM_FRAME")
        val amount = mutableMapOf("amount" to "4")
        val name = mutableMapOf("name" to "<gold>Invisible Item Frame")
        val lore = mutableMapOf("lore" to "<white>Place this Item Frame and it will be invisible! <newline><red>Empty Item Frames will be destroyed and dropped after one minute!")
        val data = mutableMapOf("data" to DataTags.INVISIBLE.name)

        items.add(mutableListOf(item, amount, name, lore, data))

        return items
    }
}