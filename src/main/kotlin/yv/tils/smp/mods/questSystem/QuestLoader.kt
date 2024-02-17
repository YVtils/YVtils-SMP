package yv.tils.smp.mods.questSystem

import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.logger.Debugger
import java.io.File

class QuestLoader {
    companion object {
        val quests: MutableMap<String, MutableMap<String, String>> = mutableMapOf()
    }


    /*
        <name>:
            - ingredients:
                - <item>:
                    - amount: <amount>
                    - meta: <meta>
                - <item>:
                    - amount: <amount>
                    - meta: <meta>
                - ...
            - rewards:
                - <item>:
                    - name: <name>
                    - amount: <amount>
                    - lore: <lore>
                    - meta: <meta>
                    - effects:
                        - <effect>:
                            - ???
                        - ...
    */

    fun loadQuests() {
        val files = File(YVtils.instance.dataFolder.path, "quests").listFiles() ?: return

        for (file in files) {
            val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)
            if (file.extension != "yml") continue
            if (!ymlFile.getBoolean("enabled")) continue

            val name = file.nameWithoutExtension
            val displayItem: Material = Material.getMaterial(ymlFile.getString("displayItem")!!) ?: Material.DIRT

            quests[name] = mutableMapOf("DisplayItem" to displayItem.name)

            Debugger().log("Loaded quest", "Name: $name | File: ${file.path} | Map: ${quests[name]}", "yv/tils/smp/mods/questSystem/QuestLoader.kt")
        }
    }

}