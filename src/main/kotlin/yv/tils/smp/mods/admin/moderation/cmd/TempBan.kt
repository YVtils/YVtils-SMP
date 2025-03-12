package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import yv.tils.smp.mods.admin.moderation.handler.TempBanHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

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
                            val target = args[0] as OfflinePlayer
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