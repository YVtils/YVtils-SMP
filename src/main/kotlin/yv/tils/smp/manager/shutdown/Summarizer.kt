package yv.tils.smp.manager.shutdown

import dev.jorel.commandapi.CommandAPI
import yv.tils.smp.mods.discord.BotManager

class Summarizer {
    fun shutdown() {
        CommandAPI.onDisable()
        BotManager().stopBot()
    }
}