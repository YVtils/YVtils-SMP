package yv.tils.smp.mods.admin.moderation.handler

import dev.jorel.commandapi.kotlindsl.*
import io.papermc.paper.event.player.AsyncChatEvent
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
import java.util.*

class MuteHandler {
    fun mutePlayer(target: OfflinePlayer, sender: CommandSender, reason: String) {
        if (MuteHandler().checkMute(target)) {
            if (sender is Player) {
                sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.PLAYER_ALREADY_MUTED))
            } else {
                sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ALREADY_MUTED))
            }
            return
        }

        updateMute(target, reason)

        if (target.isOnline) {
            target.player?.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(target.uniqueId, LangStrings.PLAYER_GOT_MUTED),
                    listOf("prefix", "reason"),
                    listOf(Vars().prefix, reason)
                )
            )
        }

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

    fun playerChat(e: AsyncChatEvent) {
        val player = e.player

        if (checkMute(player)) {
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
                    Language().getMessage(player.uniqueId, LangStrings.MUTED_TRY_TO_WRITE),
                    listOf("prefix", "reason", "duration"),
                    listOf(
                        Vars().prefix,
                        mutedPlayers_yml.mutedPlayer[player.uniqueId]?.get(0)
                            ?: Language().getRawMessage(player.uniqueId, LangStrings.MOD_NO_REASON),
                        duration
                    )
                )
            )
        }
    }

    fun checkMute(target: OfflinePlayer): Boolean {
        refreshMutedPlayers()
        return mutedPlayers_yml.mutedPlayer.containsKey(target.uniqueId)
    }

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

    fun updateMute(target: OfflinePlayer, reason: String = "null", duration: String = "null") {
        val file = File(YVtils.instance.dataFolder.path, "admin/" + "mutedPlayers.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        mutedPlayers_yml.mutedPlayer[target.uniqueId] = listOf(reason, duration)

        ymlFile.set("${target.uniqueId}.reason", reason)
        ymlFile.set("${target.uniqueId}.duration", duration)

        ymlFile.save(file)
    }
}