package yv.tils.smp.mods.admin.moderation.handler

import com.destroystokyo.paper.profile.PlayerProfile
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.admin.mutedPlayers_yml
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.io.File

class UnmuteHandler {
    fun unmutePlayer(targets: List<PlayerProfile>, sender: CommandSender, silent: Boolean = false) {
        for (target in targets) {
            try {
                if (target.id == null) {
                    // This should never happen, but just in case
                    sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to unmute the player."))
                    return
                }

                val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(target.id!!)

                if (!MuteHandler().checkMute(target)) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(sender, LangStrings.MOD_PLAYER_NOT_MUTED),
                            listOf("prefix", "player"),
                            listOf(Vars().prefix, target.name ?: "null"),
                        )
                    )
                    return
                }

                removeMute(target)

                if (offlinePlayer.isOnline) {
                    offlinePlayer.player?.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(target.id!!, LangStrings.PLAYER_GOT_UNMUTED),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        )
                    )
                }

                if (!silent) {
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
            } catch (_: Exception) {
                sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to unmute the player."))
                continue
            }
        }
    }

    private fun removeMute(target: PlayerProfile) {
        mutedPlayers_yml.mutedPlayer.remove(target.id)

        val file = File(YVtils.instance.dataFolder.path, "admin/" + "mutedPlayers.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        ymlFile.set(target.id.toString(), null)
        ymlFile.save(file)
    }
}