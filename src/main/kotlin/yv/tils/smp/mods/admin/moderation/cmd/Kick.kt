package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.entitySelectorArgumentOnePlayer
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import org.bukkit.entity.Player
import yv.tils.smp.mods.admin.moderation.handler.KickHandler
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class Kick {
    val command = commandTree("kick") {
        withPermission("yvtils.smp.command.moderation.kick")
        withUsage("kick <player> [reason]")

        entitySelectorArgumentOnePlayer("player") {
            greedyStringArgument("reason", true) {
                anyExecutor { sender, args ->
                    val target = args[0] as Player
                    val reason = args[1] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)

                    val kickHandler = KickHandler()

                    kickHandler.kickPlayer(target, sender, reason as String)
                }
            }
        }
    }
}