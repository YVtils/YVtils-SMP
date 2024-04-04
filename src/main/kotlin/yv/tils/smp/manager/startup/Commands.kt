package yv.tils.smp.manager.startup

import dev.jorel.commandapi.CommandAPI
import yv.tils.smp.manager.commands.*
import yv.tils.smp.mods.admin.invSee.EcSee
import yv.tils.smp.mods.admin.invSee.InvSee
import yv.tils.smp.mods.admin.moderation.*
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.mods.fusionCrafting.FusionCraftingGUI
import yv.tils.smp.mods.status.StatusCommand

class Commands {
    fun unregisterCommands() {
        CommandAPI.unregister("gamemode", true)
        CommandAPI.unregister("seed", true)
        CommandAPI.unregister("ban", true)
        CommandAPI.unregister("pardon", true)
        CommandAPI.unregister("kick", true)
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
        Kick()
        Ban()
        TempBan()
        Unban()
        Mute()
        TempMute()
        Unmute()
        modulesCommands()
    }

    private fun modulesCommands() {
        FusionCraftingGUI()
        StatusCommand()
    }
}