package yv.tils.smp.manager.commands.handle

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

class SpeedHandler {
    fun speedSwitch(player: Player, speed: String, sender: CommandSender = player) {
        val floatSpeed = speed.toFloat()

        player.walkSpeed = floatSpeed / 10
        player.flySpeed = floatSpeed / 10

        player.sendMessage(
            Placeholder().replacer(
                Language().getMessage(player.uniqueId, LangStrings.SPEED_CHANGE_SELF),
                listOf("speed"),
                listOf(speed)
            )
        )

        if (sender != player) {
            if (sender is Player) {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(sender.uniqueId, LangStrings.SPEED_CHANGE_OTHER),
                        listOf("speed", "player"),
                        listOf(speed, player.name)
                    )
                )
            } else {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.SPEED_CHANGE_OTHER),
                        listOf("speed", "player"),
                        listOf(speed, player.name)
                    )
                )
            }
        }
    }

    fun speedReset(player: Player, sender: CommandSender = player) {
        player.walkSpeed = 0.2F
        player.flySpeed = 0.1F

        player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.SPEED_RESET_SELF))

        if (sender != player) {
            if (sender is Player) {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(sender.uniqueId, LangStrings.SPEED_RESET_OTHER),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            } else {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.SPEED_RESET_OTHER),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            }
        }
    }
}