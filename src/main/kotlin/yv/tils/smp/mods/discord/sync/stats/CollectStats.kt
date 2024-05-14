package yv.tils.smp.mods.discord.sync.stats

import org.bukkit.Bukkit
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.discord.DiscordConfig
import java.text.SimpleDateFormat

class CollectStats {
    companion object {
        var active = DiscordConfig.config["serverStats.enabled"] as Boolean
        val statsMode = DiscordConfig.config["serverStats.mode"] as String
        val channelID = DiscordConfig().readChannelID("serverStats.channel")

        val layoutServerStatus = DiscordConfig.config["serverStats.layout.serverStatus.text"] as String
        val layoutServerVersion = DiscordConfig.config["serverStats.layout.serverVersion"] as String
        val layoutLastPlayerCount = DiscordConfig.config["serverStats.layout.lastPlayerCount"] as String
        val layoutLastRefreshed = DiscordConfig.config["serverStats.layout.lastRefreshed"] as String

        val serverStatusEmojiOnline = DiscordConfig.config["serverStats.layout.serverStatus.emoji.online"] as String
        val serverStatusEmojiOffline = DiscordConfig.config["serverStats.layout.serverStatus.emoji.offline"] as String

        var serverStatusText = ""
        var serverVersionText = ""
        var lastPlayerCountText = ""
        var lastRefreshedText = ""

        var serverStatus = "x"
        var serverVersion = "x.x.x"
        var lastPlayerCount = "x"
        var lastRefreshed = "xx/xx/xxxx xx:xx:xx"
    }

    fun collect() {
        if (active) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(YVtils.instance, Runnable {
                if (!active) {
                    return@Runnable
                }

                prepareLayout()

                when (statsMode) {
                    "channels" -> {
                        StatsChannel().updateChannels()
                    }

                    "description" -> {
                        StatsDescription().updateDescription()
                    }

                    "both" -> {
                        StatsChannel().updateChannels()
                        StatsDescription().updateDescription()
                    }

                    else -> {
                        active = false
                    }
                }
            }, 0, 20 * 60 * 10)
        }
    }

    fun prepareLayout(newServerStatus: String = "", newServerVersion: String = "", newLastPlayerCount: String = "") {
        if (newServerStatus.isEmpty()) {
            serverStatus = "ONLINE"
        }

        if (newServerVersion.isEmpty()) {
            serverVersion = YVtils.instance.server.minecraftVersion
            val viaVersion = YVtils.instance.server.pluginManager.getPlugin("ViaVersion") != null

            if (viaVersion) {
                serverVersion = "$serverVersion +"
            }
        }

        if (newLastPlayerCount.isEmpty()) {
            lastPlayerCount = Bukkit.getOnlinePlayers().size.toString()
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastRefreshed = dateFormat.format(System.currentTimeMillis())

        serverStatusText = layoutServerStatus.replace("<status>", serverStatus)
        serverStatusText = serverStatusText.replace(
            "<emoji>",
            if (serverStatus == "ONLINE") serverStatusEmojiOnline else serverStatusEmojiOffline
        )
        serverVersionText = layoutServerVersion.replace("<version>", serverVersion)
        lastPlayerCountText = layoutLastPlayerCount.replace("<count>", lastPlayerCount)
        lastRefreshedText = layoutLastRefreshed.replace("<time>", lastRefreshed)
    }
}