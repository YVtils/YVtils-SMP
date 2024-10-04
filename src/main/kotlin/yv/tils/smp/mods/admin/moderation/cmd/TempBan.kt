package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.moderation.handler.TempBanHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*

class TempBan {
    val command = commandTree("tempban") {
        withPermission("yvtils.smp.command.moderation.tempban")
        withUsage("tempban <player> <duration> [reason]")
        withAliases("tban")

        offlinePlayerArgument("player") {
            integerArgument("duration") {
                textArgument("unit") {
                    replaceSuggestions(ArgumentSuggestions.strings("s", "m", "h", "d", "w"))

                    greedyStringArgument("reason", true) {
                        anyExecutor { sender, args ->
                            val targetArg = args[0] as OfflinePlayer
                            val target = Bukkit.getOfflinePlayer(MojangAPI().uuid2name(targetArg.uniqueId)!!)
                            val duration = args[1] as Int
                            val unit = args[2] as String
                            val reason = args[3] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)

                            val tempBanHandler = TempBanHandler()

                            tempBanHandler.banPlayer(target, sender, duration, unit, reason as String)
                        }
                    }
                }
            }
        }
    }
}