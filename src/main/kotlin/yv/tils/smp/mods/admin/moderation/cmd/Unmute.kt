package yv.tils.smp.mods.admin.moderation.cmd

import com.destroystokyo.paper.profile.PlayerProfile
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerProfileArgument
import dev.jorel.commandapi.kotlindsl.textArgument
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.moderation.handler.UnmuteHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.admin.mutedPlayers_yml

class Unmute {
    val command = commandTree("unmute") {
        withPermission("yvtils.smp.command.moderation.unmute")
        withUsage("unmute <player>")

        playerProfileArgument("player") {
            replaceSuggestions(ArgumentSuggestions.strings { _ ->
                val mutedPlayersUUID = mutedPlayers_yml.mutedPlayer.keys
                val mutedPlayers: MutableList<String> = mutableListOf()
                for (uuid in mutedPlayersUUID) {
                    val player = Bukkit.getOfflinePlayer(uuid)

                    if (player.name == null) {
                        continue
                    }

                    mutedPlayers.add(player.name!!)
                }

                val suggestions = mutedPlayers.toTypedArray()
                suggestions
            })

            anyExecutor { sender, args ->
                val target = args[0] as List<PlayerProfile>

                val unmuteHandler = UnmuteHandler()

                unmuteHandler.unmutePlayer(target, sender)
            }
        }
    }
}