package yv.tils.smp.mods.admin.moderation.cmd

import com.destroystokyo.paper.profile.PlayerProfile
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import yv.tils.smp.mods.admin.moderation.handler.TempMuteHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class TempMute {
    val command = commandTree("tempmute") {
        withPermission("yvtils.smp.command.moderation.tempmute")
        withUsage("tempmute <player> <duration> [reason]")

        playerProfileArgument("player") {
            integerArgument("duration") {
                textArgument("unit") {
                    replaceSuggestions(ArgumentSuggestions.strings("s", "m", "h", "d", "w"))

                    greedyStringArgument("reason", true) {
                        anyExecutor { sender, args ->
                            val target = args[0] as List<PlayerProfile>
                            val duration = args[1] as Int
                            val unit = args[2] as String
                            val reason = args[3] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)

                            val tempMuteHandler = TempMuteHandler()

                            tempMuteHandler.tempmutePlayer(target, sender, duration, unit, reason as String)
                        }
                    }
                }
            }
        }
    }
}