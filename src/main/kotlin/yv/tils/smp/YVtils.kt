package yv.tils.smp

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import org.bukkit.plugin.java.JavaPlugin
import yv.tils.smp.manager.commands.GamemodeCMD
import yv.tils.smp.manager.startup.Configs
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Runtime
import yv.tils.smp.utils.internalAPI.StringReplacer
import yv.tils.smp.utils.internalAPI.Vars
import yv.tils.smp.utils.logger.Debugger

class YVtils : JavaPlugin() {
    companion object {
        lateinit var instance: YVtils
    }

    val pluginVersion = "1.0.0-ALPHA"

    override fun onLoad() {
        instance = this
        CommandAPI.onLoad(CommandAPIBukkitConfig(instance).silentLogs(true))
    }

    override fun onEnable() {
        Debugger().log(
            "YVtils SMP Start",
            Language().directFormat("This is the first action of the plugin!", "Dies ist die erste Aktion des Plugins!"),
            "yv.tils.smp.YVtils"
        )

        Configs().language()

        server.consoleSender.sendMessage(
            StringReplacer().listReplacer(
                Language().getMessage(LangStrings.START_MESSAGE),
                listOf("prefix"),
                listOf(Vars().prefix)
            )
        )

        Runtime().loadedMods()

        yv.tils.smp.manager.startup.Summarizer().startup()

        server.consoleSender.sendMessage(
            StringReplacer().listReplacer(
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
