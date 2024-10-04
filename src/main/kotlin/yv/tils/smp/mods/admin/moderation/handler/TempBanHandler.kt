package yv.tils.smp.mods.admin.moderation.handler

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*

class TempBanHandler {
    fun banPlayer(target: OfflinePlayer, sender: CommandSender, duration: Int, unit: String, reason: String) {
        if (target.isBanned) {
            if (sender is Player) {
                sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.PLAYER_ALREADY_BANNED))
            } else {
                sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ALREADY_BANNED))
            }
            return
        }

        val expireAfter: Calendar = Calendar.getInstance()
        when (unit) {
            "s" -> expireAfter.add(Calendar.SECOND, duration)
            "m" -> expireAfter.add(Calendar.MINUTE, duration)
            "h" -> expireAfter.add(Calendar.HOUR, duration)
            "d" -> expireAfter.add(Calendar.DAY_OF_MONTH, duration)
            "w" -> expireAfter.add(Calendar.WEEK_OF_YEAR, duration)
            else -> {
                if (sender is Player) {
                    sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.UNKNOWN_TIME_FORMAT))
                } else {
                    sender.sendMessage(Language().getMessage(LangStrings.UNKNOWN_TIME_FORMAT))
                }

                return
            }
        }

        target.banPlayer(reason, expireAfter.time, sender.name)

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("yvtils.smp.command.moderation.announcement")) {
                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_TEMPBAN),
                        listOf("prefix", "player", "moderator", "reason", "duration"),
                        listOf(Vars().prefix, target.name ?: "null", sender.name, reason, expireAfter.time.toString())
                    )
                )
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_TEMPBAN),
                listOf("prefix", "player", "moderator", "reason", "duration"),
                listOf(Vars().prefix, target.name ?: "null", sender.name, reason, expireAfter.time.toString())
            )
        )
    }
}