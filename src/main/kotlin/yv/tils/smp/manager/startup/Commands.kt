package yv.tils.smp.manager.startup

import dev.jorel.commandapi.CommandAPI
import yv.tils.smp.manager.commands.GamemodeCMD

class Commands {
    fun unregisterCommands() {
        CommandAPI.unregister("gamemode", true);
    }

    fun registerCommands() {
        GamemodeCMD()
    }
}