package yv.tils.smp.mods.admin.moderation.cmd

import com.destroystokyo.paper.profile.PlayerProfile
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import dev.jorel.commandapi.kotlindsl.playerProfileArgument
import yv.tils.smp.mods.admin.moderation.handler.BanHandler
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class Ban {
    val command = commandTree("ban") {
        withPermission("yvtils.smp.command.moderation.ban")
        withUsage("ban <player> [reason]")

        playerProfileArgument("player") {
            greedyStringArgument("reason", true) {
                anyExecutor { sender, args ->
                    val target = args[0] as List<PlayerProfile>
                    val reason = args[1] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)

                    val banHandler = BanHandler()
                    banHandler.banPlayer(target, sender, reason as String)
                }
            }
        }
    }
}