package yv.tils.smp.mods.fusionCrafting

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.fusions.lightBlock.LightBlock
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.logger.Debugger
import java.io.File

class FusionLoader {
    companion object {
        val questThumbnails: MutableMap<String, ItemStack> = mutableMapOf()
        val component2name: MutableMap<Component, String> = mutableMapOf()
    }

    fun generateDefaultQuests() {
        val file = File(YVtils.instance.dataFolder.path, "fusions")
        if (!file.exists()) file.mkdirs()

        val lightBlockFile = File(YVtils.instance.dataFolder.path, "fusions/lightBlock.yml")
        val lightBlockYML: YamlConfiguration = YamlConfiguration.loadConfiguration(lightBlockFile)
        LightBlock().configFile(lightBlockYML)
        lightBlockYML.save(lightBlockFile)

        Debugger().log("Generated default fusion", "Generated default fusion", "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt")
    }

    fun loadQuestThumbnail() {
        val files = File(YVtils.instance.dataFolder.path, "fusions").listFiles() ?: return

        for (file in files) {
            val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)
            if (file.extension != "yml") continue
            if (!ymlFile.getBoolean("enabled")) continue

            val name = file.nameWithoutExtension
            val displayItem = ItemStack(Material.valueOf(ymlFile.getString("displayItem") ?: "DIRT"))
            val displayItemMeta = displayItem.itemMeta
            displayItemMeta.displayName(ColorUtils().convert("<aqua>" + ymlFile.getString("name")))
            displayItemMeta.persistentDataContainer.set(YVtils.key, PersistentDataType.STRING, "questGUIItem")
            val lore = mutableListOf<Component>()
            lore.add(ColorUtils().convert(("<white>" + ymlFile.getString("description"))))
            lore.add(ColorUtils().convert(" "))
            lore.add(ColorUtils().convert("<gray>Click to view quest"))
            displayItemMeta.lore(lore)
            displayItem.itemMeta = displayItemMeta

            questThumbnails[name] = displayItem
            component2name[displayItem.displayName()] = name

            Debugger().log("Loaded quest thumbnail", "Name: $name | File: ${file.path} | Map: ${questThumbnails[name]}", "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt")
        }
    }

    fun loadQuest(quest: String): MutableMap<String, Any> {
        val file = File(YVtils.instance.dataFolder.path, "fusions/$quest.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        val questMap = mutableMapOf<String, Any>()

        questMap["name"] = ymlFile.getString("name") ?: "Unknown"
        questMap["description"] = ymlFile.getString("description") ?: "Unknown"

        val inputItems = ymlFile.getConfigurationSection("input")?.getKeys(false)
        val outputItems = ymlFile.getConfigurationSection("output")?.getKeys(false)

        for (input in inputItems!!) {
            val inputSection = ymlFile.getConfigurationSection("input.$input")
            val inputSectionKeys = inputSection?.getKeys(false)
            for (key in inputSectionKeys!!) {
                val subinputSection = ymlFile.getMapList("input.$input.$key")
                questMap["input.$input.$key"] = subinputSection
            }
        }

        for (output in outputItems!!) {
            val suboutputSection = ymlFile.getMapList("output.$output")
            questMap["output.$output"] = suboutputSection
        }

        Debugger().log("Loaded quest", "Name: $quest | File: ${file.path} | Map: $questMap", "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt")
        return questMap
    }
}