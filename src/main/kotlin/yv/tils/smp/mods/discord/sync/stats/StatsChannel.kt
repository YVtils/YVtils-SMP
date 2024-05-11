package yv.tils.smp.mods.discord.sync.stats

import net.dv8tion.jda.api.Permission
import yv.tils.smp.mods.discord.BotManager.Companion.jda
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.serverStatusText
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.serverVersionText
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.lastPlayerCountText
import yv.tils.smp.mods.discord.sync.stats.CollectStats.Companion.lastRefreshedText
import java.util.EnumSet

class StatsChannel {
    companion object {
        val channelIDMap = mutableMapOf<String, MutableList<String>>()
    }

    fun createChannels() {
        for (guild in jda.guilds) {
            val channelIDList = mutableListOf<String>()

            guild.createVoiceChannel(serverStatusText).queue { channel ->
                channel.manager.putPermissionOverride(guild.publicRole, EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.of(Permission.VOICE_CONNECT)).queue()
                channelIDList.add(channel.id)
            }

            guild.createVoiceChannel(serverVersionText).queue { channel ->
                channel.manager.putPermissionOverride(guild.publicRole, EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.of(Permission.VOICE_CONNECT)).queue()
                channelIDList.add(channel.id)
            }

            guild.createVoiceChannel(lastPlayerCountText).queue { channel ->
                channel.manager.putPermissionOverride(guild.publicRole, EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.of(Permission.VOICE_CONNECT)).queue()
                channelIDList.add(channel.id)
            }

            guild.createVoiceChannel(lastRefreshedText).queue { channel ->
                channel.manager.putPermissionOverride(guild.publicRole, EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.of(Permission.VOICE_CONNECT)).queue()
                channelIDList.add(channel.id)
            }

            channelIDMap[guild.id] = channelIDList
        }
    }

    fun updateChannels() {
        if (channelIDMap.isEmpty()) {
            createChannels()
            return
        }

        for (key in channelIDMap.keys) {
            val guild = jda.getGuildById(key) ?: continue
            val channelList = channelIDMap[key] ?: continue

            for (i in channelList.indices) {
                val channel = guild.getVoiceChannelById(channelList[i]) ?: continue

                when (i) {
                    0 -> {
                        channel.manager.setName(serverStatusText).queue()
                    }
                    1 -> {
                        channel.manager.setName(serverVersionText).queue()
                    }
                    2 -> {
                        channel.manager.setName(lastPlayerCountText).queue()
                    }
                    3 -> {
                        channel.manager.setName(lastRefreshedText).queue()
                    }
                }
            }
        }
    }

    fun deleteChannels() {
        if (channelIDMap.isEmpty()) {
            return
        }

        for (key in channelIDMap.keys) {
            val guild = jda.getGuildById(key) ?: continue
            val channelList = channelIDMap[key] ?: continue

            for (channelID in channelList) {
                val channel = guild.getVoiceChannelById(channelID) ?: continue
                channel.delete().queue()
            }
        }
    }
}