package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.moderation.handler.BanHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class Ban {
    val command = commandTree("ban") {
        withPermission("yvtils.smp.command.moderation.ban")
        withUsage("ban <player> [reason]")

        offlinePlayerArgument("player") {
            greedyStringArgument("reason", true) {
                anyExecutor { sender, args ->
                    val targetArg = args[0] as OfflinePlayer
                    val target = Bukkit.getOfflinePlayer(MojangAPI().uuid2name(targetArg.uniqueId)!!)
                    val reason = args[1] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)

                    val banHandler = BanHandler()

                    banHandler.banPlayer(target, sender, reason as String)
                }
            }
        }
    }
}