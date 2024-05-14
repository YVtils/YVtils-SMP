package yv.tils.smp.mods.discord.sync.stats

import yv.tils.smp.mods.discord.BotManager.Companion.jda
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.channelID
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.lastPlayerCountText
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.lastRefreshedText
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.serverStatusText
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.serverVersionText

class StatsDescription {
    fun updateDescription() {
        jda.getTextChannelById(channelID)?.manager?.setTopic(
            "$serverStatusText\n" +
                    "$serverVersionText\n" +
                    "$lastPlayerCountText\n" +
                    lastRefreshedText
        )?.queue()
    }

    fun serverShutdown() {
        if (CollectStats.statsMode != "description" && CollectStats.statsMode != "both") {
            return
        }

        CollectStats.serverStatus = "OFFLINE"
        CollectStats.lastPlayerCount = "0"

        CollectStats().prepareLayout("OFFLINE", newLastPlayerCount = "0")

        jda.getTextChannelById(channelID)?.manager?.setTopic(
            "$serverStatusText\n" +
                    "$serverVersionText\n" +
                    "$lastPlayerCountText\n" +
                    lastRefreshedText
        )?.queue()
    }
}