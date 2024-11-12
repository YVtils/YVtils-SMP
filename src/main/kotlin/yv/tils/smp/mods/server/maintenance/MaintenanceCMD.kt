package yv.tils.smp.mods.server.maintenance

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.stringArgument
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.player.PlayerKickEvent
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class MaintenanceCMD {
    val maintenanceHandler = MaintenanceHandler()

    val command = commandTree("maintenance") {
        withPermission("yvtils.smp.command.maintenance")
        withUsage("maintenance [state]")

        stringArgument("state", true) {
            replaceSuggestions(ArgumentSuggestions.strings("true", "false", "toggle"))
            anyExecutor { sender, args ->
                maintenanceHandler.maintenance(sender, args)
            }
        }

        anyExecutor { sender, _ ->
            maintenanceHandler.maintenance(sender)
        }
    }
}