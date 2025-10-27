package yv.tils.smp.manager.commands.register

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.entitySelectorArgumentOnePlayer
import dev.jorel.commandapi.kotlindsl.playerProfileArgument
import org.bukkit.entity.Player
import yv.tils.smp.manager.commands.handle.HealHandler
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class HealCMD {
    val command = commandTree("heal") {
        withPermission("yvtils.smp.command.heal")
        withUsage("heal [player]")

        entitySelectorArgumentOnePlayer("player", true) {
            anyExecutor { sender, args ->
                if (sender !is Player && args[0] == null) {
                    sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                    return@anyExecutor
                }

                val healHandler = HealHandler()

                if (args[0] is Player) {
                    val target = args[0] as Player
                    healHandler.playerHeal(target, sender)
                } else {
                    healHandler.playerHeal(sender as Player)
                }
            }
        }
    }
}