package yv.tils.smp.utils.internalAPI

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.logger.Debugger

class Placeholder {
    fun replacer(inPut: Component, old: List<String>, new: List<String>): Component {
        Debugger().log("InPut", inPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("Old", "$old", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("New", "$new", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        var text = MiniMessage.miniMessage().serialize(inPut)

        text = text.replace("\\<", "<")

        for (i in old.indices) {
            val oldString = "<${old[i]}>"
            text = text.replace(oldString, new[i])
        }

        if (text.startsWith(" ")) {
            text = text.replaceFirst(" ", "")
        }

        val outPut = ColorUtils().convert(text)

        Debugger().log("OutPut", outPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        return outPut
    }

    fun replacer(inPut: Component, replace: Map<String, Any>): Component {
        Debugger().log("InPut", inPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("Replace", "$replace", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        var text = MiniMessage.miniMessage().serialize(inPut)

        text = text.replace("\\<", "<")

        for (i in replace.keys) {
            val oldString = "<$i>"
            text = text.replace(oldString, replace[i].toString())
        }

        if (text.startsWith(" ")) {
            text = text.replaceFirst(" ", "")
        }

        val outPut = ColorUtils().convert(text)

        Debugger().log("OutPut", outPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        return outPut
    }

    fun replacer(inPut: String, replace: Map<String, Any>): Component {
        Debugger().log("InPut", inPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("Replace", "$replace", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        var text = inPut

        text = text.replace("\\<", "<")

        for (i in replace.keys) {
            val oldString = "<$i>"
            text = text.replace(oldString, replace[i].toString())
        }

        if (text.startsWith(" ")) {
            text = text.replaceFirst(" ", "")
        }

        val outPut = ColorUtils().convert(text)

        Debugger().log("OutPut", outPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        return outPut
    }

    fun calcOnlinePlayers(): Int {
        var onlinePlayers = Bukkit.getOnlinePlayers().size

        for (player in Vanish.vanish) {
            if (player.value) {
                onlinePlayers--
            }
        }

        return onlinePlayers
    }
}