package yv.tils.smp.mods.admin.moderation

import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer
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

                    banPlayer(target, sender, reason as String)
                }
            }
        }
    }

    private fun banPlayer(target: OfflinePlayer, sender: CommandSender, reason: String) {
        if (target.isBanned) {
            if (sender is Player) {
                sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.PLAYER_ALREADY_BANNED))
            } else {
                sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ALREADY_BANNED))
            }
            return
        }

        target.banPlayer(reason, sender.name)

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("yvtils.smp.command.moderation.announcement")) {
                player.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_BAN),
                        listOf("prefix", "player", "moderator", "reason"),
                        listOf(Vars().prefix, target.name ?: "null", sender.name, reason)
                    )
                )
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(
            StringReplacer().listReplacer(
                Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_BAN),
                listOf("prefix", "player", "moderator", "reason"),
                listOf(Vars().prefix, target.name ?: "null", sender.name, reason)
            )
        )
    }
}