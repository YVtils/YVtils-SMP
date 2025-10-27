package yv.tils.smp.mods.admin.moderation.cmd

import com.destroystokyo.paper.profile.PlayerProfile
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import org.bukkit.OfflinePlayer
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.moderation.handler.UnbanHandler

class Unban {
    val command = commandTree("unban") {
        withPermission("yvtils.smp.command.moderation.unban")
        withUsage("unban <player>")
        withAliases("pardon")

        offlinePlayerArgument("player") {
            replaceSuggestions(ArgumentSuggestions.strings { _ ->
                val bannedPlayers: MutableSet<OfflinePlayer> = YVtils.instance.server.bannedPlayers
                val bannedPlayersNames: MutableList<String> = mutableListOf()

                for (player in bannedPlayers) {
                    if (player.name == null) {
                        continue
                    }

                    bannedPlayersNames.add(player.name!!)
                }

                val suggestions = bannedPlayersNames.toTypedArray()
                suggestions
            })

            anyExecutor { sender, args ->
                val target = args[0] as List<PlayerProfile>

                val unbanHandler = UnbanHandler()
                unbanHandler.unbanPlayer(target, sender)
            }
        }
    }
}