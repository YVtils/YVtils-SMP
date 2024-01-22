package yv.tils.smp.utils.configs.language

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.logger.Debugger
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class Language {

    companion object {
        var playerLang: Map<UUID, Locale> = HashMap()

        //Load langauge files
        private var config_en: Map<String, String> = HashMap()
        private var config_de: Map<String, String> = HashMap()
        private var config_global: Map<String, String> = HashMap()
    }

    private var yamlConfiguration: YamlConfiguration? = null

    private val langList = listOf("en", "de",)

    fun getLanguageFiles() {
        for (lang in langList) {
            val file = File(
                YVtils.instance.dataFolder.path + "/language",
                "$lang.yml"
            )
            if (!file.exists()) {
                file.createNewFile()
                Debugger().log("Language File created", "Language file $lang.yml created", "yv.tils.smp.utils.configs.language.Language.getLanguageFiles()")
            }
            yamlConfiguration = YamlConfiguration.loadConfiguration(file)
            registerStrings(lang)
        }

        val file = File(
            YVtils.instance.dataFolder.path + "/language",
            YVtils.instance.config.getString("Language") + ".yml"
        )
        if (file.exists()) {
            yamlConfiguration = YamlConfiguration.loadConfiguration(file)
            registerStrings("global")
        } else {
            Debugger().log("Language File not found", file.path, "yv.tils.smp.utils.configs.language.Language.getLanguageFiles()")
            Bukkit.getConsoleSender().sendMessage(
                directFormat(
                    "The set language value can't be used. Falling back to english",
                    "Die gesetzte Sprache kann nicht verwendet werden. Es wird als Alternative Englisch genutzt"
                )
            )
            config_global = config_en
        }
    }

    private fun registerStrings(lang: String) {
        val config: MutableMap<String, String> = HashMap()
        for (string in LangStrings.entries) {
            val message = yamlConfiguration!!.getString(string.name)
            config[string.name] = message.toString()
        }

        when (lang) {
            "en" -> {
                config_en = config
            }
            "de" -> {
                config_de = config
            }
            "global" -> {
                config_global = config
            }
        }
    }

    fun getMessage(uuid: UUID, message: LangStrings,): Component {
        val lang = playerLang[uuid]

        return when (lang) {
            Locale.GERMAN -> ColorUtils().convert(config_de[message.name].toString())
            Locale.ENGLISH -> ColorUtils().convert(config_en[message.name].toString())
            else -> ColorUtils().convert(config_global[message.name].toString())
        }
    }

    fun getMessage(message: LangStrings,): Component {
        if (config_global[message.name] == null) {
            Debugger().log("Language String not found", message.name, "yv.tils.smp.utils.configs.language.Language.getMessage()")
            return ColorUtils().convert(message.name)
        } else {
            return ColorUtils().convert(config_global[message.name].toString())
        }
    }

    fun directFormat(en: String, de: String,): Component {
        val s = if (Config().config["Language"] == "en") {
            en
        } else if (Config().config["Language"] == "de") {
            de
        } else {
            en
        }
        return ColorUtils().convert(s)
    }
}