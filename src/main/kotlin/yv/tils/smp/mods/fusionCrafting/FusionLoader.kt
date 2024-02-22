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
        val file = File(YVtils.instance.dataFolder.path, "quests")
        if (!file.exists()) file.mkdirs()

        val lightBlockFile = File(YVtils.instance.dataFolder.path, "quests/lightBlock.yml")
        val lightBlockYML: YamlConfiguration = YamlConfiguration.loadConfiguration(lightBlockFile)
        LightBlock().configFile(lightBlockYML)
        lightBlockYML.save(lightBlockFile)

        Debugger().log("Generated default quests", "Generated default quests", "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt")
    }

    fun loadQuestThumbnail() {
        val files = File(YVtils.instance.dataFolder.path, "quests").listFiles() ?: return

        for (file in files) {
            val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)
            if (file.extension != "yml") continue
            if (!ymlFile.getBoolean("enabled")) continue

            val name = file.nameWithoutExtension
            val displayItem = ItemStack(Material.valueOf(ymlFile.getString("DisplayItem") ?: "DIRT"))
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
        val file = File(YVtils.instance.dataFolder.path, "quests/$quest.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        val questMap = mutableMapOf<String, Any>()
        // TODO: Load quest data into questMap

        Debugger().log("Loaded quest", "Name: $quest | File: ${file.path} | Map: $questMap", "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt")
        return questMap
    }
}