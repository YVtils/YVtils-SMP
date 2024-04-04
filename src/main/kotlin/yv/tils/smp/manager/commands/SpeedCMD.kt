package yv.tils.smp.manager.commands

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer

class SpeedCMD {
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
                        speedSwitch(target, args[0].toString(), sender)
                    } else {
                        speedSwitch(sender as Player, args[0].toString())
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
                        speedReset(target, sender)
                    } else {
                        speedReset(sender as Player)
                    }
                }
            }
        }
    }

    private fun speedSwitch(player: Player, speed: String, sender: CommandSender = player) {
        val floatSpeed = speed.toFloat()

        player.walkSpeed = floatSpeed / 10
        player.flySpeed = floatSpeed / 10

        player.sendMessage(
            StringReplacer().listReplacer(
                Language().getMessage(player.uniqueId, LangStrings.SPEED_CHANGE_SELF),
                listOf("speed"),
                listOf(speed)
            )
        )

        if (sender != player) {
            if (sender is Player) {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(sender.uniqueId, LangStrings.SPEED_CHANGE_OTHER),
                        listOf("speed", "player"),
                        listOf(speed, player.name)
                    )
                )
            } else {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(LangStrings.SPEED_CHANGE_OTHER),
                        listOf("speed", "player"),
                        listOf(speed, player.name)
                    )
                )
            }
        }
    }

    private fun speedReset(player: Player, sender: CommandSender = player) {
        player.walkSpeed = 0.2F
        player.flySpeed = 0.1F

        player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.SPEED_RESET_SELF))

        if (sender != player) {
            if (sender is Player) {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(sender.uniqueId, LangStrings.SPEED_RESET_OTHER),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            } else {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(LangStrings.SPEED_RESET_OTHER),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            }
        }
    }
}