package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import dev.jorel.commandapi.kotlindsl.offlinePlayerArgument
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import yv.tils.smp.mods.admin.moderation.handler.MuteHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class Mute {
    val command = commandTree("mute") {
        withPermission("yvtils.smp.command.moderation.mute")
        withUsage("mute <player> [reason]")

        offlinePlayerArgument("player") {
            greedyStringArgument("reason", true) {
                anyExecutor { sender, args ->
                    val targetArg = args[0] as OfflinePlayer
                    val target = Bukkit.getOfflinePlayer(MojangAPI().uuid2name(targetArg.uniqueId)!!)
                    val reason = args[1] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)

                    val muteHandler = MuteHandler()

                    muteHandler.mutePlayer(target, sender, reason as String)
                }
            }
        }
    }
}