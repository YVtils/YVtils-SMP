package yv.tils.smp.utils.configs.server

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml_server {
    private var file = File(YVtils.instance.dataFolder.path, "server/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://docs.yvtils.net/smp/server/config.yml")

        ymlFile.addDefault("motd.top", topTemplateMOTD())
        ymlFile.addDefault("motd.bottom", bottomTemplateMOTD())
        ymlFile.addDefault("motd.maintenance", "<gradient:red:green>Server is in maintenance mode")

        ymlFile.addDefault("maxPlayers", -1)

        ymlFile.addDefault("playerCountHover", playerCountHover())

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }

    private fun topTemplateMOTD(): List<String> {
        return listOf(
            "<gradient:red:green>SMP Server",
            "<gradient:red:green>Version: <version>",
        )
    }

    private fun bottomTemplateMOTD(): List<String> {
        return listOf(
            "<gradient:blue:light_purple>Visit our website: https://yvtils.net/yvtils-smp",
            "<gradient:blue:light_purple>Join our Discord: https://yvtils.net/yvtils/support"
        )
    }

    private fun playerCountHover(): List<String> {
        return listOf(
            "<gradient:blue:light_purple>+----------------------------+",
            "<gradient:blue:light_purple>|         <serverName>         |",
            "<gradient:blue:light_purple>|     Version: <version>     |",
            "<gradient:blue:light_purple>| Players: <players> / <maxPlayers> |",
            "<gradient:blue:light_purple>|   Website: https://yvtils.net   |",
            "<gradient:blue:light_purple>+----------------------------+"
        )
    }
}