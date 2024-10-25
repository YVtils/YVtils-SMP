package yv.tils.smp.mods.status

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.entity.Player
import yv.tils.smp.mods.status.StatusHandler as handler

class StatusCommand {
    val command = commandTree("status") {
        withPermission("yvtils.smp.command.status")
        withUsage("status <set/default/clear> [status/player]")
        withAliases("prefix", "role")

        literalArgument("set", false) {
            withPermission("yvtils.smp.command.status.set")
            greedyStringArgument("status", false) {
                playerExecutor { player, args ->
                    handler().setStatus(player, args[0] as String)
                }
            }
        }

        literalArgument("default", false) {
            greedyStringArgument("status", false) {
                val suggestions = handler().generateDefaultStatus()
                replaceSuggestions(ArgumentSuggestions.strings(suggestions))
                playerExecutor { player, args ->
                    handler().setDefaultStatus(player, args[0] as String, suggestions)
                }
            }
        }

        literalArgument("clear", false) {
            playerArgument("player", true) {
                withPermission("yvtils.smp.command.status.clear.others")
                anyExecutor { sender, args ->
                    if (args[0] is Player) {
                        handler().clearStatus(sender as Player, args[0] as Player)
                    } else {
                        handler().clearStatus(sender as Player)
                    }
                }
            }
        }
    }


}