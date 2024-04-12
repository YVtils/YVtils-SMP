package yv.tils.smp.mods.admin.moderation

import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class Kick {
    val command = commandTree("kick") {
        withPermission("yvtils.smp.command.moderation.kick")
        withUsage("kick <player> [reason]")

        playerArgument("player") {
            greedyStringArgument("reason", true) {
                anyExecutor { sender, args ->
                    val target = args[0] as Player
                    val reason = args[1] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)
                    kickPlayer(target, sender, reason as String)
                }
            }
        }
    }

    private fun kickPlayer(target: Player, sender: CommandSender, reason: String) {
        if (!target.isOnline) {
            if (sender is Player) {
                sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.PLAYER_NOT_ONLINE))
            } else {
                sender.sendMessage(Language().getMessage(LangStrings.PLAYER_NOT_ONLINE))
            }
            return
        }

        target.kick(ColorUtils().convert(reason), PlayerKickEvent.Cause.KICK_COMMAND)

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("yvtils.smp.command.moderation.announcement")) {
                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_KICK),
                        listOf("prefix", "player", "moderator", "reason"),
                        listOf(Vars().prefix, target.name, sender.name, reason)
                    )
                )
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_KICK),
                listOf("prefix", "player", "moderator", "reason"),
                listOf(Vars().prefix, target.name, sender.name, reason)
            )
        )
    }
}