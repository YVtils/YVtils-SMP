package yv.tils.smp.mods.admin.moderation.handler

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Parser
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*

class TempMuteHandler {
    /**
     * Tempmute player
     * @param target Player to tempmute
     * @param sender CommandSender to send messages
     * @param duration Int of mute duration
     * @param unit String of time unit
     * @param reason String of mute reason
     */
    fun tempmutePlayer(
        target: OfflinePlayer,
        sender: CommandSender,
        duration: Int,
        unit: String,
        reason: String,
    ) {
        if (MuteHandler().checkMute(target)) {
            sender.sendMessage(Language().getMessage(sender, LangStrings.PLAYER_ALREADY_MUTED))
            return
        }

        val parsedTime = Parser().parseTime(unit, duration)
        if (parsedTime.error != null || parsedTime.answer == null) {
            sender.sendMessage(Language().getMessage(sender, parsedTime.error!!))
            return
        }
        val expireAfter = parsedTime.answer as Calendar

        MuteHandler().updateMute(target, reason, expireAfter.timeInMillis.toString())

        if (target.isOnline) {
            target.player?.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(target.uniqueId, LangStrings.PLAYER_GOT_TEMPMUTED),
                    listOf("prefix", "reason", "duration"),
                    listOf(Vars().prefix, reason, expireAfter.time.toString())
                )
            )
        }

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("yvtils.smp.command.moderation.announcement")) {
                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_TEMPMUTE),
                        listOf("prefix", "player", "moderator", "reason", "duration"),
                        listOf(Vars().prefix, target.name ?: "null", sender.name, reason, expireAfter.time.toString())
                    )
                )
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_TEMPMUTE),
                listOf("prefix", "player", "moderator", "reason", "duration"),
                listOf(Vars().prefix, target.name ?: "null", sender.name, reason, expireAfter.time.toString())
            )
        )
    }
}