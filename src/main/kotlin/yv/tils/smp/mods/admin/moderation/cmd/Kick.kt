package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.moderation.handler.BanHandler
import yv.tils.smp.mods.admin.moderation.handler.KickHandler
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

                    val kickHandler = KickHandler()

                    kickHandler.kickPlayer(target, sender, reason as String)
                }
            }
        }
    }
}