package yv.tils.smp.utils.configs.discord

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.Language
import java.io.File

class config_yml_discord {
    private var file = File(YVtils.instance.dataFolder.path, "discord/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")

        ymlFile.addDefault("botToken", ColorUtils().convert(Language().directFormat("YOUR TOKEN HERE", "DEINEN BOT TOKEN")))
        ymlFile.addDefault("mainGuild", "Guild ID")

        ymlFile.addDefault("botSettings.onlineStatus", "online")
        ymlFile.addDefault("botSettings.activity", "none")
        ymlFile.addDefault("botSettings.activityMessage", "Minecraft")

        ymlFile.addDefault("embedSettings.author", "YVtils SMP")
        ymlFile.addDefault("embedSettings.authorIconURL", "URL")

        ymlFile.addDefault("whitelistFeature.channel", ColorUtils().convert(Language().directFormat("CHANNEL ID", "KANAL ID")))
        ymlFile.addDefault(
            "whitelistFeature.role",
            ColorUtils().convert(Language().directFormat("ROLE ID 1, ROLE ID 2, ROLE ID ...", "ROLLEN ID 1, ROLLEN ID 2, ROLLEN ID ..."))
        )

        ymlFile.addDefault("serverInfoCommand.permission", ColorUtils().convert(Language().directFormat("PERMISSION", "BERECHTIGUNG")))

        ymlFile.addDefault("whitelistCommand.permission", ColorUtils().convert(Language().directFormat("PERMISSION", "BERECHTIGUNG")))

        ymlFile.addDefault("chatSync.enabled", true)
        ymlFile.addDefault("chatSync.permission", ColorUtils().convert(Language().directFormat("PERMISSION", "BERECHTIGUNG")))
        ymlFile.addDefault("chatSync.channel", ColorUtils().convert(Language().directFormat("CHANNEL ID", "KANAL ID")))

        ymlFile.addDefault("consoleSync.enabled", true)
        ymlFile.addDefault("consoleSync.channel", ColorUtils().convert(Language().directFormat("CHANNEL ID", "KANAL ID")))

        ymlFile.addDefault("logChannel", ColorUtils().convert(Language().directFormat("CHANNEL ID", "KANAL ID")))

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}