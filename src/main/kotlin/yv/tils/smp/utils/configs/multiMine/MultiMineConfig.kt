package yv.tils.smp.utils.configs.multiMine

import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class MultiMineConfig {
    companion object {
        val config: MutableMap<String, Any> = mutableMapOf()
        val blockList: MutableList<Material> = mutableListOf()
        val saves: MutableMap<String, Any> = mutableMapOf()
    }

    fun loadConfig() {
        var file = File(YVtils.instance.dataFolder.path, "multiMine/" + "config.yml")
        var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            config[key] = ymlFile.get(key) as Any
        }

        loadBlockList()

        file = File(YVtils.instance.dataFolder.path, "multiMine/" + "save.yml")
        ymlFile = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            saves[key] = ymlFile.get(key) as Any
        }
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

    fun changePlayerSetting(uuid: String, value: Any) {
        val file = File(YVtils.instance.dataFolder.path, "multiMine/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        saves[uuid] = value

        ymlFile.set(uuid, value)
        ymlFile.save(file)
    }

    /**
     * Get the player activation state for the multiMine
     * @param uuid the player uuid
     * @return true if the player has multiMine activated
     */
    fun getPlayerSetting(uuid: String): Boolean {
        if (saves[uuid] != null) {
            return saves[uuid] as Boolean
        }
        return config["defaultState"] as Boolean
    }
}