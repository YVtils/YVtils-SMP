package yv.tils.smp.mods.admin.moderation.handler

import io.papermc.paper.ban.BanListType
import org.bukkit.BanList
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

class BanHandler {
    /**
     * Ban player
     * @param target Player to ban
     * @param sender CommandSender to send messages
     * @param reason String of ban reason
     */
    fun banPlayer(target: OfflinePlayer, sender: CommandSender, reason: String, silent: Boolean = false) {
        if (target.isBanned) {
            sender.sendMessage(Language().getMessage(sender, LangStrings.PLAYER_ALREADY_BANNED))
            return
        }

        val playerProfile = target.playerProfile

        Bukkit.getBanList(BanListType.PROFILE).addBan(playerProfile, reason, null as Date?, sender.name)

        if (target.isOnline) {
            val player: Player? = target.player
            KickHandler().kickPlayer(player!!, sender, reason, silent = true)
        }

        if (!silent) {
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
}