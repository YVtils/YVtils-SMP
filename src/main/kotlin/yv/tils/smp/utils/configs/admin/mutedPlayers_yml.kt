package yv.tils.smp.utils.configs.admin

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.logger.Debugger
import java.io.File
import java.util.*

class mutedPlayers_yml {
    companion object {
        val mutedPlayer: MutableMap<UUID, List<String>> = mutableMapOf()
    }

    private val file = File(YVtils.instance.dataFolder.path, "admin/" + "mutedPlayers.yml")
    private val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    /*
        <UUID>:
          reason: <reason>
          duration: <duration>
    */

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }

    fun loadConfig() {
        mutedPlayer.clear()

        for (uuid in ymlFile.getKeys(false)) {
            if (uuid == "documentation") continue

            val reason: String = ymlFile.getString("$uuid.reason") ?: "null"
            val duration: String = ymlFile.getString("$uuid.duration") ?: "null"

            mutedPlayer[UUID.fromString(uuid)] = listOf(reason, duration)

            Debugger().log("Load Muted Player", "UUID: $uuid, Reason: $reason, Duration: $duration", "yv/tils/smp/utils/configs/admin/mutedPlayers_yml.kt")
        }
    }
}