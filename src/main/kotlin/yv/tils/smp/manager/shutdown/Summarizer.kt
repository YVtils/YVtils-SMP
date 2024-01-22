package yv.tils.smp.manager.shutdown

import dev.jorel.commandapi.CommandAPI

class Summarizer {
    fun shutdown() {
        CommandAPI.onDisable()
    }
}