package yv.tils.smp.mods.fusionCrafting

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.fusions.invisItemFrames.InvisItemFrame
import yv.tils.smp.mods.fusionCrafting.fusions.lightBlock.LightBlock
import yv.tils.smp.mods.fusionCrafting.fusions.playerHeads.PlayerHeads
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.logger.Debugger
import java.io.File

class FusionLoader {
    companion object {
        val fusionThumbnails: MutableMap<String, ItemStack> = mutableMapOf()
        val component2name: MutableMap<Component, String> = mutableMapOf()
        val tagMap: MutableMap<String, MutableList<String>> = mutableMapOf()
        val disabledFusions: MutableList<String> = mutableListOf()
    }

    fun generateDefaultFusions() {
        val file = File(YVtils.instance.dataFolder.path, "fusions")
        if (!file.exists()) file.mkdirs()

        val lightBlockFile = File(YVtils.instance.dataFolder.path, "fusions/lightBlock.yml")
        if (!lightBlockFile.exists()) {
            val lightBlockYML: YamlConfiguration = YamlConfiguration.loadConfiguration(lightBlockFile)
            LightBlock().configFile(lightBlockYML)
            lightBlockYML.save(lightBlockFile)

            Debugger().log(
                "Generated default fusion",
                "Generated default fusion: lightBlock",
                "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt"
            )
        }

        val invisItemFrameFile = File(YVtils.instance.dataFolder.path, "fusions/invisItemFrame.yml")
        if (!invisItemFrameFile.exists()) {
            val invisItemFrameYML: YamlConfiguration = YamlConfiguration.loadConfiguration(invisItemFrameFile)
            InvisItemFrame().configFile(invisItemFrameYML)
            invisItemFrameYML.save(invisItemFrameFile)

            Debugger().log(
                "Generated default fusion",
                "Generated default fusion: invisItemFrame",
                "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt"
            )
        }

        val playerHeadsFile = File(YVtils.instance.dataFolder.path, "fusions/playerHeads.yml")
        if (!playerHeadsFile.exists()) {
            val playerHeadsYML: YamlConfiguration = YamlConfiguration.loadConfiguration(playerHeadsFile)
            PlayerHeads().configFile(playerHeadsYML)
            playerHeadsYML.save(playerHeadsFile)

            Debugger().log(
                "Generated default fusion",
                "Generated default fusion: playerHeads",
                "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt"
            )
        }
    }

    fun loadFusionThumbnail() {
        disabledFusions.clear()
        fusionThumbnails.clear()
        component2name.clear()
        tagMap.clear()

        val files = File(YVtils.instance.dataFolder.path, "fusions").listFiles() ?: return

        for (file in files) {
            val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)
            if (file.extension != "yml") continue
            if (!ymlFile.getBoolean("enabled")) {
                disabledFusions.add(file.nameWithoutExtension)
            }

            val name = file.nameWithoutExtension
            val displayItem = ItemStack(Material.valueOf(ymlFile.getString("displayItem") ?: "DIRT"))
            val displayItemMeta = displayItem.itemMeta
            val tagList: MutableList<String> = mutableListOf()

            for (tag in ymlFile.getString("tags")!!.split(";")) {
                var newTag = tag

                newTag = newTag.replace(" ", "")
                newTag = newTag.replace(";", "")

                if (newTag == "") continue

                tagList.add(newTag)
            }

            displayItemMeta.displayName(ColorUtils().convert("<aqua>" + ymlFile.getString("name")))
            displayItemMeta.persistentDataContainer.set(FusionKeys.FUSION_GUI.key, PersistentDataType.STRING, "true")
            val lore = mutableListOf<Component>()
            lore.add(ColorUtils().convert(("<white>" + ymlFile.getString("description"))))
            lore.add(ColorUtils().convert("<gray>Click to view fusion"))
            lore.add(ColorUtils().convert(" "))
            lore.add(ColorUtils().convert("<white>Tags: <gray>" + tagList.joinToString(", ")))

            displayItemMeta.lore(lore)
            displayItem.itemMeta = displayItemMeta

            fusionThumbnails[name] = displayItem
            component2name[displayItem.displayName()] = name

            for (tag in tagList) {
                if (tagMap.containsKey(tag)) {
                    tagMap[tag]?.add(name)
                } else {
                    tagMap[tag] = mutableListOf(name)
                }
            }

            Debugger().log(
                "Loaded fusion thumbnail",
                "Name: $name | File: ${file.path} | Map: ${fusionThumbnails[name]}",
                "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt"
            )
        }
    }

    fun loadFusion(fusion: String): MutableMap<String, Any> {
        val file = File(YVtils.instance.dataFolder.path, "fusions/$fusion.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        val fusionMap = mutableMapOf<String, Any>()

        fusionMap["name"] = ymlFile.getString("name") ?: "Unknown"
        fusionMap["description"] = ymlFile.getString("description") ?: "Unknown"

        val inputItems = ymlFile.getConfigurationSection("input")?.getKeys(false)
        val outputItems = ymlFile.getConfigurationSection("output")?.getKeys(false)

        try {
            for (input in inputItems!!) {
                val inputSection = ymlFile.getConfigurationSection("input.$input")
                val inputSectionKeys = inputSection?.getKeys(false)
                for (key in inputSectionKeys!!) {
                    val subinputSection = ymlFile.getMapList("input.$input.$key")
                    fusionMap["input.$input.$key"] = subinputSection
                }
            }

            for (output in outputItems!!) {
                val suboutputSection = ymlFile.getMapList("output.$output")
                fusionMap["output.$output"] = suboutputSection
            }
        } catch (e: NullPointerException) {
            Debugger().log(
                "Failed to load fusion input/output",
                "Name: $fusion | File: ${file.path} | Error: ${e.message}",
                "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt"
            )
        }


        Debugger().log(
            "Loaded fusion",
            "Name: $fusion | File: ${file.path} | Map: $fusionMap",
            "yv/tils/smp/mods/fusionCrafting/FusionLoader.kt"
        )
        return fusionMap
    }
}