package yv.tils.smp.mods.admin.moderation.handler

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.textArgument
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.admin.mutedPlayers_yml
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.io.File

class UnmuteHandler {
    fun unmutePlayer(target: OfflinePlayer, sender: CommandSender) {
        if (!MuteHandler().checkMute(target)) {
            if (sender is Player) {
                sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.MOD_PLAYER_NOT_MUTED))
            } else {
                sender.sendMessage(Language().getMessage(LangStrings.MOD_PLAYER_NOT_MUTED))
            }
            return
        }

        removeMute(target)

        if (target.isOnline) {
            target.player?.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(target.uniqueId, LangStrings.PLAYER_GOT_UNMUTED),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
        }

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("yvtils.smp.command.moderation.announcement")) {
                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_UNMUTE),
                        listOf("prefix", "player", "moderator"),
                        listOf(Vars().prefix, target.name ?: "null", sender.name)
                    )
                )
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_UNMUTE),
                listOf("prefix", "player", "moderator"),
                listOf(Vars().prefix, target.name ?: "null", sender.name)
            )
        )
    }

    private fun removeMute(target: OfflinePlayer) {
        mutedPlayers_yml.mutedPlayer.remove(target.uniqueId)

        val file = File(YVtils.instance.dataFolder.path, "admin/" + "mutedPlayers.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        ymlFile.set(target.uniqueId.toString(), null)
        ymlFile.save(file)
    }
}