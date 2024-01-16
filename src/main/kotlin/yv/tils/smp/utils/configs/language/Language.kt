package yv.tils.smp.utils.configs.language

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.logger.Debugger
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class Language {
    private var yamlConfiguration: YamlConfiguration? = null

    var config: Map<String, String> = HashMap()
    var playerLang: Map<UUID, Locale> = HashMap()


    fun langaugeFileGet() {
        val file = File(
            YVtils.instance.dataFolder.path + "/Language",
            YVtils.instance.config.getString("Language") + ".yml"
        )
        if (file.exists()) {
            yamlConfiguration = YamlConfiguration.loadConfiguration(file)
            registerStrings()
        } else {
            Debugger().log("Language File not found", file.path, "yv.tils.smp.utils.configs.language.Language.langaugeFileGet()")
            Bukkit.getConsoleSender().sendMessage(
                directFormat(
                    "The set language value can't be used",
                    "Die gesetzte Sprache kann nicht verwendet werden"
                )
            )
        }
    }

    private fun registerStrings() {
        for (string in LangStrings.entries) {
            val message = yamlConfiguration!!.getString(string.name)
            config.plus(string.name to message)
        }
    }

    fun getMessage(message: LangStrings,): String {
        if (config[message.name] == null) {
            Debugger().log("Language String not found", message.name, "yv.tils.smp.utils.configs.language.Language.getMessage()")
            return message.name
        } else {
            return config[message.name].toString()
        }
    }

    fun directFormat(en: String, de: String,): String {
        val s = if (Config().config["Language"] == "en") {
            en
        } else if (Config().config["Language"] == "de") {
            de
        } else {
            en
        }
        return s
    }

}