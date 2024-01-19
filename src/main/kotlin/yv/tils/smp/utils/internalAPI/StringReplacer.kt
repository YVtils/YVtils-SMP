package yv.tils.smp.utils.internalAPI

import yv.tils.smp.utils.logger.Debugger

class StringReplacer {
    fun listReplacer(inPut: String, old: List<String>, new: List<String>): String {
        Debugger().log("InPut", inPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("Old", "$old", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")
        Debugger().log("New", "$new", "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        var outPut = inPut
        for (i in old.indices) {
            outPut = outPut.replace(old[i], new[i])
        }

        Debugger().log("OutPut", outPut, "yv.tils.smp.utils.internalAPI.StringReplacer.listReplacer()")

        return outPut
    }
}