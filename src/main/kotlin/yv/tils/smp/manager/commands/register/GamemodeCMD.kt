package yv.tils.smp.manager.commands.register

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.manager.commands.handle.GamemodeHandler
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

class GamemodeCMD {
    val command = commandTree("gm") {
        withPermission("yvtils.smp.command.gamemode")
        withUsage("gm <gamemode> [player]")
        withAliases("gamemode")

        stringArgument("gamemode", false) {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "survival",
                    "creative",
                    "adventure",
                    "spectator",
                    "0",
                    "1",
                    "2",
                    "3"
                )
            )
            playerArgument("player", true) {
                anyExecutor { sender, args ->

                    if (sender !is Player && args[1] == null) {
                        sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                        return@anyExecutor
                    }

                    val gmHandler = GamemodeHandler()

                    if (args[1] is Player) {
                        val target = args[1] as Player
                        gmHandler.gamemodeSwitch(target, args[0].toString(), sender)
                    } else {
                        gmHandler.gamemodeSwitch(sender as Player, args[0].toString())
                    }
                }
            }
        }
    }
}