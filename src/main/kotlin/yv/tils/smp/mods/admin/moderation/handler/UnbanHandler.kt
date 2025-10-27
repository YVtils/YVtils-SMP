package yv.tils.smp.mods.admin.moderation.handler

import com.destroystokyo.paper.profile.PlayerProfile
import io.papermc.paper.ban.BanListType
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*

class UnbanHandler {
    fun unbanPlayer(targets: List<PlayerProfile>, sender: CommandSender, silent: Boolean = false) {
        for (target in targets) {
            try {
                if (target.id == null) {
                    // This should never happen, but just in case
                    sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to unban the player."))
                    return
                }

                val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(target.id!!)

                if (!offlinePlayer.isBanned) {
                    sender.sendMessage(Language().getMessage(sender, LangStrings.MOD_PLAYER_NOT_BANNED))
                    return
                }

                val date = Date()
                date.time = 0L

                Bukkit.getBanList(BanListType.PROFILE).addBan(target, null, date, sender.name)

                if (!silent) {
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
            } catch (_: Exception) {
                sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to unban the player."))
                continue
            }
        }
    }
}