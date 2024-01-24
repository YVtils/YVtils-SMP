package yv.tils.smp.manager.startup

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import yv.tils.smp.YVtils
import yv.tils.smp.utils.logger.Debugger

class Summarizer {
    fun startup() {


        val configs = Configs()
        configs.register()
        Debugger().log("Starting up", "Configs loaded", "yv.tils.smp.manager.startup.Summarizer")

        Debugger().log("Starting up", "Listeners loading", "yv.tils.smp.manager.startup.Summarizer")

        val listeners = Listeners()
        listeners.register()

        Debugger().log("Starting up", "Commands loading", "yv.tils.smp.manager.startup.Summarizer")

        val commands = Commands()
        CommandAPI.onEnable()
        commands.unregisterCommands()
        commands.registerCommands()

        Debugger().log("Starting up", "Modules loading", "yv.tils.smp.manager.startup.Summarizer")

        val modules = Modules()
        //modules.registerModules()

        Debugger().log("Starting up", "Other loading", "yv.tils.smp.manager.startup.Summarizer")

        val other = Other()
        other.register()
    }
}