package yv.tils.smp.utils.internalAPI

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.logger.Debugger

class StringReplacer {
    fun listReplacer(inPut: Component, old: List<String>, new: List<String>): Component {
        Debugger().log("InPut", inPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("Old", "$old", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("New", "$new", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        var text = MiniMessage.miniMessage().serialize(inPut)

        println(text)

        text = text.replace("\\<", "<")

        for (i in old.indices) {
            val oldString = "<${old[i]}>"
            text = text.replace(oldString, new[i])
        }

        text = text.drop(1)

        val outPut = ColorUtils().convert(text)

        Debugger().log("OutPut", outPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        return outPut
    }
}