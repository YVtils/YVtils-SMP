package yv.tils.smp.utils.internalAPI

import yv.tils.smp.YVtils

class Runtime {
    val mods: MutableList<String> = ArrayList()

    fun loadedMods(): List<String> {
        if (YVtils.instance.config.getBoolean("Modules.Discord")) mods.add("Discord")
        if (YVtils.instance.config.getBoolean("Modules.Status")) mods.add("Status")
        if (YVtils.instance.config.getBoolean("Modules.Sit")) mods.add("Sit")
        if (YVtils.instance.config.getBoolean("Modules.MultiMine")) mods.add("MultiMine")
        if (YVtils.instance.config.getBoolean("Modules.CCR")) mods.add("CCR")
        if (YVtils.instance.config.getBoolean("Modules.OldVersion")) mods.add("OldVersion")
        if (YVtils.instance.config.getBoolean("Modules.Server")) mods.add("Server")
        if (YVtils.instance.config.getBoolean("Modules.Admin")) mods.add("Admin")

        return mods
    }
}