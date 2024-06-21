package yv.tils.smp.utils.internalAPI

import yv.tils.smp.YVtils

class Runtime {
    val mods: MutableList<String> = ArrayList()

    fun loadedMods(): List<String> {
        if (YVtils.instance.config.getBoolean("modules.discord")) mods.add("Discord")
        if (YVtils.instance.config.getBoolean("modules.status")) mods.add("Status")
        if (YVtils.instance.config.getBoolean("modules.sit")) mods.add("Sit")
        if (YVtils.instance.config.getBoolean("modules.multiMine")) mods.add("MultiMine")
        if (YVtils.instance.config.getBoolean("modules.fusion")) mods.add("Fusion")
        if (YVtils.instance.config.getBoolean("modules.server")) mods.add("Server")
        if (YVtils.instance.config.getBoolean("modules.admin")) mods.add("Admin")

        return mods
    }
}