package yv.tils.smp.mods.fusionCrafting.fusions.lightBlock

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import yv.tils.smp.utils.color.ColorUtils

class LightBlock {
    fun configFile(ymlFile: YamlConfiguration): YamlConfiguration {
        ymlFile.addDefault("enabled", true)
        ymlFile.addDefault("name", "Light Block")
        ymlFile.addDefault("DisplayItem", "LIGHT")
        ymlFile.addDefault("description", "<white>Craft an <gold>invisible <white>Light Source")

        ymlFile.addDefault("Input", inputItems())
        ymlFile.addDefault("Output", outputItems())

        ymlFile.options().copyDefaults(true)

        return ymlFile
    }

    private fun inputItems(): MutableMap<String, MutableList<ItemStack>> {
        val items = mutableMapOf<String, MutableList<ItemStack>>()

        val lantern = ItemStack(Material.LANTERN)

        val soulLantern = ItemStack(Material.SOUL_LANTERN)

        items["Normal or Soul Lantern"] = mutableListOf(lantern, soulLantern)

        val glassPane = ItemStack(Material.GLASS_PANE)
        val whiteGlassPane = ItemStack(Material.WHITE_STAINED_GLASS_PANE)
        val orangeGlassPane = ItemStack(Material.ORANGE_STAINED_GLASS_PANE)
        val magentaGlassPane = ItemStack(Material.MAGENTA_STAINED_GLASS_PANE)
        val lightBlueGlassPane = ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
        val yellowGlassPane = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
        val limeGlassPane = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val pinkGlassPane = ItemStack(Material.PINK_STAINED_GLASS_PANE)
        val grayGlassPane = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val lightGrayGlassPane = ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
        val cyanGlassPane = ItemStack(Material.CYAN_STAINED_GLASS_PANE)
        val purpleGlassPane = ItemStack(Material.PURPLE_STAINED_GLASS_PANE)
        val blueGlassPane = ItemStack(Material.BLUE_STAINED_GLASS_PANE)
        val brownGlassPane = ItemStack(Material.BROWN_STAINED_GLASS_PANE)
        val greenGlassPane = ItemStack(Material.GREEN_STAINED_GLASS_PANE)
        val redGlassPane = ItemStack(Material.RED_STAINED_GLASS_PANE)
        val blackGlassPane = ItemStack(Material.BLACK_STAINED_GLASS_PANE)

        items["Glass Pane"] = mutableListOf(
            glassPane,
            whiteGlassPane,
            orangeGlassPane,
            magentaGlassPane,
            lightBlueGlassPane,
            yellowGlassPane,
            limeGlassPane,
            pinkGlassPane,
            grayGlassPane,
            lightGrayGlassPane,
            cyanGlassPane,
            purpleGlassPane,
            blueGlassPane,
            brownGlassPane,
            greenGlassPane,
            redGlassPane,
            blackGlassPane
        )

        return items
    }

    private fun outputItems(): MutableList<ItemStack> {
        val items = mutableListOf<ItemStack>()

        val lightBlock = ItemStack(Material.LIGHT)
        val lightBlockMeta = lightBlock.itemMeta
        val lightBlockLore = mutableListOf<Component>()
        lightBlockMeta.displayName(ColorUtils().convert("<white>Light Block"))
        lightBlockLore.add(ColorUtils().convert("<white>Place this block to create an invisible light source"))
        lightBlockMeta.lore(lightBlockLore)
        lightBlock.itemMeta = lightBlockMeta
        items.add(lightBlock)

        return items
    }
}