package yv.tils.smp.mods.admin.moderation.handler

import com.destroystokyo.paper.profile.PlayerProfile
import io.papermc.paper.ban.BanListType
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*

class BanHandler {
    /**
     * Ban player
     * @param targets Player to ban
     * @param sender CommandSender to send messages
     * @param reason String of ban reason
     */
    fun banPlayer(targets: List<PlayerProfile>, sender: CommandSender, reason: String, silent: Boolean = false) {
        for (target in targets) {
            try {
                if (target.id == null) {
                    // This should never happen, but just in case
                    sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to ban the player."))
                    return
                }

                val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(target.id!!)

                if (offlinePlayer.isBanned) {
                    sender.sendMessage(Language().getMessage(sender, LangStrings.PLAYER_ALREADY_BANNED))
                    continue
                }

                Bukkit.getBanList(BanListType.PROFILE).addBan(target, reason, null as Date?, sender.name)

                if (offlinePlayer.isOnline) {
                    val player: Player? = offlinePlayer.player
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
            } catch (_: Exception) {
                sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to ban the player."))
                continue
            }
        }
    }
}