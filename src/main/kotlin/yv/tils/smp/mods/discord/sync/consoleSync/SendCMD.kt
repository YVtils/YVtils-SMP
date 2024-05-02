package yv.tils.smp.mods.discord.sync.consoleSync

import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bukkit.Bukkit
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.sync.consoleSync.GetConsole.Companion.active
import yv.tils.smp.mods.discord.sync.consoleSync.GetConsole.Companion.channelID
import yv.tils.smp.utils.color.ColorUtils

class SendCMD : ListenerAdapter() {
    override fun onMessageReceived(e: MessageReceivedEvent) {
        if (!active) return
        if (e.author.isBot) return
        if (e.channelType.compareTo(ChannelType.TEXT) != 0) return

        val textChannel = e.channel.asTextChannel()
        if (textChannel.id != channelID) return

        val msg = e.message
        var content = msg.contentDisplay

        content = content.replace("/", "")

        Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
            e.message.addReaction(Emoji.fromUnicode("🖥️")).queue()
            YVtils.instance.server.consoleSender.sendMessage(ColorUtils().convert("<gray>[<#7289da>DC Console<gray>]<white> $content"))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), content)
        })
    }
}