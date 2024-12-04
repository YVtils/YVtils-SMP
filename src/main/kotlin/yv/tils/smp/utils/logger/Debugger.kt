package yv.tils.smp.utils.logger

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.internalAPI.Runtime

class Debugger {
    companion object {
        private var firstPrint = true
    }

    private val plugin = YVtils.instance

    private var log: String = ""

    fun log(title: String = "null", desc: String = "null", where: String = "null") {
        if (YVtils.instance.config.getBoolean("debug")) {
            log = ""

            if (firstPrint) {
                firstPrint()
                firstPrint = false
            }

            log = log.plus("\n----- DEBUG START -----\n")
            log = log.plus("YVtils-SMP Plugin\n")
            log = log.plus("$title: \n")
            log = log.plus("$desc\n")
            log = log.plus("Found here: $where\n")
            log = log.plus("----- DEBUG END -----")

            plugin.server.consoleSender.sendMessage(Component.text(log))
        }
    }

    fun log(title: String = "null", desc: Component = Component.text("null"), where: String = "null") {
        if (YVtils.instance.config.getBoolean("debug")) {
            log = ""

            if (firstPrint) {
                firstPrint()
                firstPrint = false
            }

            log = log.plus("\n----- DEBUG START -----\n")
            log = log.plus("YVtils-SMP Plugin\n")
            log = log.plus("$title: \n")
            log = log.plus("${MiniMessage.miniMessage().serialize(desc)}\n")
            log = log.plus("Found here: $where\n")
            log = log.plus("----- DEBUG END -----")

            plugin.server.consoleSender.sendMessage(ColorUtils().convert(log))
        }
    }

    private fun firstPrint() {
        log = log.plus("\n----- INCLUDE IN REPORTS -----\n")
        log = log.plus("Plugin: YVtils-SMP\n")
        log = log.plus("Plugin Version: ${plugin.yvtilsVersion}\n")
        log = log.plus("Server Version: ${Bukkit.getVersion()}\n")
        log = log.plus("Server Software: ${Bukkit.getName()}\n")
        log = log.plus("Loaded Mods: ${Runtime().loadedMods()}\n")
        log = log.plus("----- INCLUDE IN REPORTS -----\n")
    }
}
