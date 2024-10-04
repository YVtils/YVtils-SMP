package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.textArgument
import org.bukkit.Bukkit
import yv.tils.smp.mods.admin.moderation.handler.UnmuteHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.admin.mutedPlayers_yml

class Unmute {
    val command = commandTree("unmute") {
        withPermission("yvtils.smp.command.moderation.unmute")
        withUsage("unmute <player>")

        textArgument("player") {
            val mutedPlayersUUID = mutedPlayers_yml.mutedPlayer.keys
            val mutedPlayers: MutableList<String> = mutableListOf()
            for (uuid in mutedPlayersUUID) {
                MojangAPI().uuid2name(uuid)?.let { mutedPlayers.add(it) }
            }

            replaceSuggestions(ArgumentSuggestions.strings(mutedPlayers))

            anyExecutor { sender, args ->
                val target = Bukkit.getOfflinePlayer(MojangAPI().name2uuid(args[0] as String)!!)

                val unmuteHandler = UnmuteHandler()

                unmuteHandler.unmutePlayer(target, sender)
            }
        }
    }
}