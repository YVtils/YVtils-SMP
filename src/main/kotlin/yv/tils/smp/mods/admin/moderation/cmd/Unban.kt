package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.textArgument
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.moderation.handler.UnbanHandler
import yv.tils.smp.utils.MojangAPI

class Unban {
    val command = commandTree("unban") {
        withPermission("yvtils.smp.command.moderation.unban")
        withUsage("unban <player>")
        withAliases("pardon")

        textArgument("player") {
            val bannedPlayers: MutableSet<OfflinePlayer> = YVtils.instance.server.bannedPlayers
            val bannedPlayersNames: MutableList<String> = mutableListOf()
            for (player in bannedPlayers) {
                bannedPlayersNames.add(MojangAPI().uuid2name(player.uniqueId)!!)
            }

            replaceSuggestions(ArgumentSuggestions.strings(bannedPlayersNames))

            anyExecutor { sender, args ->
                val target = Bukkit.getOfflinePlayer(MojangAPI().name2uuid(args[0] as String)!!)

                val unbanHandler = UnbanHandler()

                unbanHandler.unbanPlayer(target, sender)
            }
        }
    }
}