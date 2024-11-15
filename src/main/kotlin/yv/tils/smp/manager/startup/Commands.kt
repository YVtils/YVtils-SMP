package yv.tils.smp.manager.startup

import dev.jorel.commandapi.CommandAPI
import yv.tils.smp.manager.commands.register.*
import yv.tils.smp.mods.admin.invSee.EcSee
import yv.tils.smp.mods.admin.invSee.InvSee
import yv.tils.smp.mods.admin.moderation.cmd.*
import yv.tils.smp.mods.admin.vanish.VanishCMD
import yv.tils.smp.mods.fusionCrafting.FusionOverview
import yv.tils.smp.mods.message.MSGCommand
import yv.tils.smp.mods.message.ReplyCommand
import yv.tils.smp.mods.multiMine.MultiMineCommand
import yv.tils.smp.mods.server.maintenance.MaintenanceCMD
import yv.tils.smp.mods.sit.SitCommand
import yv.tils.smp.mods.status.StatusCommand
import yv.tils.smp.mods.waypoints.WaypointCommand
import yv.tils.smp.utils.configs.global.Config

class Commands {
    /**
     * Unregister vanilla commands
     */
    fun unregisterCommands() {
        CommandAPI.unregister("gamemode")
        CommandAPI.unregister("seed")

        if (Config.config["modules.admin"] as Boolean) {
            CommandAPI.unregister("ban")
            CommandAPI.unregister("pardon")
            CommandAPI.unregister("kick")
        }

        if (Config.config["modules.message"] as Boolean) {
            CommandAPI.unregister("w")
            CommandAPI.unregister("whisper")
            CommandAPI.unregister("msg")
            CommandAPI.unregister("tell")
        }
    }

    /**
     * Register custom commands
     */
    fun registerCommands() {
        GamemodeCMD()
        FlyCMD()
        SpeedCMD()
        HealCMD()
        GlobalMuteCMD()
        SeedCMD()
        GodCMD()

        modulesCommands()
    }

    /**
     * Register modules commands
     */
    private fun modulesCommands() {
        if (Config.config["modules.fusion"] as Boolean) {
            FusionOverview()
        }

        if (Config.config["modules.status"] as Boolean) {
            StatusCommand()
        }

        if (Config.config["modules.sit"] as Boolean) {
            SitCommand()
        }

        if (Config.config["modules.message"] as Boolean) {
            MSGCommand()
            ReplyCommand()
        }

        if (Config.config["modules.admin"] as Boolean) {
            MaintenanceCMD()

            VanishCMD()

            InvSee()
            EcSee()

            Kick()
            Ban()
            TempBan()
            Unban()
            Mute()
            TempMute()
            Unmute()
        }

        if (Config.config["modules.multiMine"] as Boolean) {
            MultiMineCommand()
        }


        if (Config.config["modules.waypoints"] as Boolean) {
            WaypointCommand()
        }
    }
}