package yv.tils.smp.manager.startup

import dev.jorel.commandapi.CommandAPI
import yv.tils.smp.manager.commands.*
import yv.tils.smp.mods.admin.invSee.EcSee
import yv.tils.smp.mods.admin.invSee.InvSee
import yv.tils.smp.mods.admin.vanish.Vanish

class Commands {
    fun unregisterCommands() {
        CommandAPI.unregister("gamemode", true)
        CommandAPI.unregister("seed", true)
    }

    fun registerCommands() {
        GamemodeCMD()
        FlyCMD()
        SpeedCMD()
        HealCMD()
        GlobalMuteCMD()
        MaintenanceCMD()
        SeedCMD()
        Vanish()
        InvSee()
        EcSee()
    }
}