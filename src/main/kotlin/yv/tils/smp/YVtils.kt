package yv.tils.smp

import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin
import yv.tils.smp.manager.startup.Configs
import yv.tils.smp.manager.startup.Summarizer
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Runtime
import yv.tils.smp.utils.logger.Debugger

class YVtils : JavaPlugin() {
    companion object {
        lateinit var instance: YVtils
    }

    val pluginVersion = "1.0.0"

    override fun onEnable() {
        instance = this

        Debugger().log(
            "YVtils SMP Start",
            Language().directFormat("This is the first action of the plugin!", "Dies ist die erste Aktion des Plugins!"),
            "yv.tils.smp.YVtils"
        )

        Runtime().loadedMods()

        Configs().language()

        Summarizer().startup()

        server.consoleSender.sendMessage(Component.text("YVtils SMP enabled!"))
    }

    override fun onDisable() {

    }
}
