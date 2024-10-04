package yv.tils.smp.mods.admin.moderation.handler

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class BanHandler {
    fun banPlayer(target: OfflinePlayer, sender: CommandSender, reason: String) {
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
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_BAN),
                        listOf("prefix", "player", "moderator", "reason"),
                        listOf(Vars().prefix, target.name ?: "null", sender.name, reason)
                    )
                )
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_BAN),
                listOf("prefix", "player", "moderator", "reason"),
                listOf(Vars().prefix, target.name ?: "null", sender.name, reason)
            )
        )
    }
}