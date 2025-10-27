package yv.tils.smp.mods.admin.moderation.handler

import com.destroystokyo.paper.profile.PlayerProfile
import io.papermc.paper.event.player.AsyncChatEvent
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
import java.util.*

class MuteHandler {
    /**
     * Mute player
     * @param targets Player to mute
     * @param sender CommandSender to send messages
     * @param reason String of mute reason
     */
    fun mutePlayer(targets: List<PlayerProfile>, sender: CommandSender, reason: String, silent: Boolean = false) {
        for (target in targets) {
            try {
                if (target.id == null) {
                    // This should never happen, but just in case
                    sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to mute the player."))
                    return
                }

                val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(target.id!!)

                if (MuteHandler().checkMute(target)) {
                    sender.sendMessage(Language().getMessage(sender, LangStrings.PLAYER_ALREADY_MUTED))
                    return
                }

                updateMute(target, reason)

                if (offlinePlayer.isOnline) {
                    offlinePlayer.player?.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(target.id!!, LangStrings.PLAYER_GOT_MUTED),
                            listOf("prefix", "reason"),
                            listOf(Vars().prefix, reason)
                        )
                    )
                }

                if (!silent) {
                    for (player in Bukkit.getOnlinePlayers()) {
                        if (player.hasPermission("yvtils.smp.command.moderation.announcement")) {
                            player.sendMessage(
                                Placeholder().replacer(
                                    Language().getMessage(player.uniqueId, LangStrings.MOD_ANNOUNCEMENT_MUTE),
                                    listOf("prefix", "player", "moderator", "reason"),
                                    listOf(Vars().prefix, target.name ?: "null", sender.name, reason)
                                )
                            )
                        }
                    }

                    YVtils.instance.server.consoleSender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(LangStrings.MOD_ANNOUNCEMENT_MUTE),
                            listOf("prefix", "player", "moderator", "reason"),
                            listOf(Vars().prefix, target.name ?: "null", sender.name, reason)
                        )
                    )
                }
            } catch (_: Exception) {
                sender.sendMessage(ColorUtils().convert("<red>An error occurred while trying to mute the player."))
                continue
            }
        }
    }

    /**
     * Catch messages from muted players
     * @param e AsyncChatEvent
     */
    fun playerChat(e: AsyncChatEvent) {
        val player = e.player

        if (checkMute(player.playerProfile)) {
            e.isCancelled = true

            val rawDuration = mutedPlayers_yml.mutedPlayer[player.uniqueId]?.get(1) ?: "null"
            val duration = if (rawDuration != "null") {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = rawDuration.toLong()
                calendar.time.toString()
            } else {
                "Permanent"
            }

            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(player.uniqueId, LangStrings.PLAYER_YOU_ARE_MUTED),
                    listOf("prefix", "reason", "duration"),
                    listOf(
                        Vars().prefix,
                        mutedPlayers_yml.mutedPlayer[player.uniqueId]?.get(0)
                            ?: Language().getRawMessage(player.uniqueId, LangStrings.MOD_NO_REASON),
                        duration
                    )
                )
            )

            YVtils.instance.server.consoleSender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.PLAYER_TRIED_TO_WRITE_WHILE_MUTED),
                    listOf("prefix", "player", "message", "reason", "duration"),
                    listOf(
                        Vars().prefix,
                        player.name,
                        ColorUtils().convert(e.originalMessage()),
                        mutedPlayers_yml.mutedPlayer[player.uniqueId]?.get(0) ?: Language().getRawMessage(player.uniqueId, LangStrings.MOD_NO_REASON),
                        duration
                    )
                )
            )
        }
    }

    /**
     * Check if player is muted
     * @param target PlayerProfile to check
     * @return Boolean if player is muted
     */
    fun checkMute(target: PlayerProfile): Boolean {
        refreshMutedPlayers()
        return mutedPlayers_yml.mutedPlayer.containsKey(target.id)
    }

    /**
     * Refresh muted players list
     */
    private fun refreshMutedPlayers() {
        val file = File(YVtils.instance.dataFolder.path, "admin/" + "mutedPlayers.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        mutedPlayers_yml.mutedPlayer.clear()

        for (uuid in ymlFile.getKeys(false)) {
            if (uuid == "documentation") continue

            val reason: String = ymlFile.getString("$uuid.reason") ?: "null"
            val duration: String = ymlFile.getString("$uuid.duration") ?: "null"

            if (duration != "null") {
                val durationLong = duration.toLong()
                val currentTime = System.currentTimeMillis()

                if (currentTime > durationLong) {
                    ymlFile.set(uuid, null)
                    ymlFile.save(file)
                    continue
                }
            }

            mutedPlayers_yml.mutedPlayer[UUID.fromString(uuid)] = listOf(reason, duration)
        }
    }

    /**
     * Update mute status for player
     * @param target PlayerProfile to update
     * @param reason String of mute reason
     * @param duration String of mute duration
     */
    fun updateMute(target: PlayerProfile, reason: String = "null", duration: String = "null") {
        val file = File(YVtils.instance.dataFolder.path, "admin/" + "mutedPlayers.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        mutedPlayers_yml.mutedPlayer[target.id!!] = listOf(reason, duration)

        ymlFile.set("${target.id}.reason", reason)
        ymlFile.set("${target.id}.duration", duration)

        ymlFile.save(file)
    }
}