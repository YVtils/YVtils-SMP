package yv.tils.smp

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin
import yv.tils.smp.manager.startup.Configs
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Runtime
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import yv.tils.smp.utils.logger.Debugger

class YVtils : JavaPlugin() {
    companion object {
        lateinit var instance: YVtils
        lateinit var key: NamespacedKey
    }

    val pluginVersion = "1.0.0-DEV"

    override fun onLoad() {
        instance = this
        CommandAPI.onLoad(CommandAPIBukkitConfig(instance).silentLogs(true))
        key = NamespacedKey(this, "yvtils")
    }

    override fun onEnable() {
        Debugger().log(
            "YVtils SMP Start",
            Language().directFormat(
                "This is the first action of the plugin!",
                "Dies ist die erste Aktion des Plugins!"
            ),
            "yv.tils.smp.YVtils"
        )

        Configs().language()

        server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.START_MESSAGE),
                listOf("prefix"),
                listOf(Vars().prefix)
            )
        )

        Runtime().loadedMods()

        yv.tils.smp.manager.startup.Summarizer().startup()

        server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.START_COMPLETED_MESSAGE),
                listOf("prefix"),
                listOf(Vars().prefix)
            )
        )
    }

    override fun onDisable() {
        yv.tils.smp.manager.shutdown.Summarizer().shutdown()
    }
}
