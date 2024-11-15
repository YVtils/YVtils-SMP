package yv.tils.smp.mods.admin.moderation.handler

import io.papermc.paper.ban.BanListType
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*

class UnbanHandler {
    fun unbanPlayer(target: OfflinePlayer, sender: CommandSender) {
        if (!target.isBanned) {
            sender.sendMessage(Language().getMessage(sender, LangStrings.MOD_PLAYER_NOT_BANNED))
            return
        }

        val date = Date()
        date.time = 0L

        val playerProfile = target.playerProfile
        Bukkit.getBanList(BanListType.PROFILE).addBan(playerProfile, null, date, sender.name)

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("yvtils.smp.command.moderation.announcement")) {
                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_UNBAN),
                        listOf("prefix", "player", "moderator"),
                        listOf(Vars().prefix, target.name ?: "null", sender.name)
                    )
                )
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_UNBAN),
                listOf("prefix", "player", "moderator"),
                listOf(Vars().prefix, target.name ?: "null", sender.name)
            )
        )
    }
}