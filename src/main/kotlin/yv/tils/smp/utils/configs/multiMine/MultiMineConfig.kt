package yv.tils.smp.utils.configs.multiMine

import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class MultiMineConfig {
    companion object {
        val config: MutableMap<String, Any> = mutableMapOf()
        val blockList: MutableList<Material> = mutableListOf()
    }

    fun loadConfig() {
        val file = File(YVtils.instance.dataFolder.path, "multiMine/" + "config.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            config[key] = ymlFile.get(key) as Any
        }

        loadBlockList()
    }

    private fun loadBlockList() {
        val file = File(YVtils.instance.dataFolder.path, "multiMine/" + "config.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        val blocks = ymlFile.getList("blocks") as List<String>
        blocks.forEach {
            blockList.add(Material.getMaterial(it)!!)
        }
    }


    fun updateBlockList(blocks: MutableList<Material>) {
        val file = File(YVtils.instance.dataFolder.path, "multiMine/" + "config.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        val newBlocks = blocks.map { it.name }

        ymlFile.set("blocks", newBlocks)
        ymlFile.save(file)
    }
}