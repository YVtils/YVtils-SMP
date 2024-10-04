package yv.tils.smp.manager.commands.register

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.entity.Player
import yv.tils.smp.manager.commands.handle.SpeedHandler
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class SpeedCMD {
    val speedHandler = SpeedHandler()

    val command = commandTree("speed") {
        withPermission("yvtils.smp.command.speed")
        withUsage("speed <speed> [player]")

        integerArgument("speed", -10, 10, false) {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "10",
                    "9",
                    "8",
                    "7",
                    "6",
                    "5",
                    "4",
                    "3",
                    "2",
                    "1",
                    "0",
                    "-1",
                    "-2",
                    "-3",
                    "-4",
                    "-5",
                    "-6",
                    "-7",
                    "-8",
                    "-9",
                    "-10"
                )
            )
            playerArgument("player", true) {
                anyExecutor { sender, args ->

                    if (sender !is Player && args[1] == null) {
                        sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                        return@anyExecutor
                    }


                    if (args[1] is Player) {
                        val target = args[1] as Player
                        speedHandler.speedSwitch(target, args[0].toString(), sender)
                    } else {
                        speedHandler.speedSwitch(sender as Player, args[0].toString())
                    }
                }
            }
        }

        literalArgument("reset", false) {
            playerArgument("player", true) {
                anyExecutor { sender, args ->

                    if (sender !is Player && args[1] == null) {
                        sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                        return@anyExecutor
                    }

                    if (args[1] is Player) {
                        val target = args[1] as Player
                        speedHandler.speedReset(target, sender)
                    } else {
                        speedHandler.speedReset(sender as Player)
                    }
                }
            }
        }
    }
}