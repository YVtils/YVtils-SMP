package yv.tils.smp.manager.startup

import yv.tils.smp.utils.logger.Debugger

class Summarizer {
    fun startup() {

        Debugger().log("Starting up", "Configs loading", "yv.tils.smp.manager.startup.Summarizer")
        val configs = Configs()
        configs.register()

        Debugger().log("Starting up", "Listeners loading", "yv.tils.smp.manager.startup.Summarizer")

        val listeners = Listeners()
        listeners.register()

        Debugger().log("Starting up", "Commands loading", "yv.tils.smp.manager.startup.Summarizer")

        val commands = Commands()
        //commands.registerCommands()

        Debugger().log("Starting up", "Modules loading", "yv.tils.smp.manager.startup.Summarizer")

        val modules = Modules()
        //modules.registerModules()

        Debugger().log("Starting up", "Other loading", "yv.tils.smp.manager.startup.Summarizer")

        val other = Other()
        //other.registerOther()
    }
}