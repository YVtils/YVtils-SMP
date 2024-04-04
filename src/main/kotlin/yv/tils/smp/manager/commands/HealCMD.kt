package yv.tils.smp.manager.commands

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerArgument
import org.bukkit.attribute.Attribute
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer

class HealCMD {
    val command = commandTree("heal") {
        withPermission("yvtils.smp.command.heal")
        withUsage("heal [player]")

        playerArgument("player", true) {
            anyExecutor { sender, args ->
                if (sender !is Player && args[0] == null) {
                    sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                    return@anyExecutor
                }

                if (args[0] is Player) {
                    val target = args[0] as Player
                    playerHeal(target, sender)
                } else {
                    playerHeal(sender as Player)
                }
            }
        }
    }

    private fun playerHeal(player: Player, sender: CommandSender = player) {
        player.health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
        player.foodLevel = 20

        player.sendMessage(Language().getMessage(LangStrings.HEAL_PLAYER_HEALED))

        if (sender != player) {
            if (sender is Player) {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(sender.uniqueId, LangStrings.HEAL_OTHER_PLAYER_HEALED),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            } else {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(LangStrings.HEAL_OTHER_PLAYER_HEALED),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            }
        }

    }
}