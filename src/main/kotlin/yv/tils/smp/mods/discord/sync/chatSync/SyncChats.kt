package yv.tils.smp.mods.discord.sync.chatSync

import io.papermc.paper.event.player.AsyncChatEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.BotManager
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.discord.DiscordConfig
import yv.tils.smp.utils.configs.language.Language

class SyncChats : ListenerAdapter() {
    companion object {
        var active = DiscordConfig.config["chatSync.enabled"] as Boolean
        val channelID = DiscordConfig().readChannelID("chatSync.channel")
        val minecraftPermission = "yvtils.smp.chatSync"
        val discordPermission = DiscordConfig.config["chatSync.permission"] as String
    }

    // TODO Test if color codes are getting filtered
    fun minecraftToDiscord(e: AsyncChatEvent) {
        val message = e.message()

        if (!active) return
        if (!e.player.hasPermission(minecraftPermission)) return

        sendDiscordMessage(e.player, ColorUtils().strip(ColorUtils().convert(message)))
    }

    private fun sendDiscordMessage(sender: Player, message: String) {
        try {
            val channel = BotManager.jda.getTextChannelById(channelID) ?: return
            channel.sendMessageEmbeds(Embed().embed(sender, message).build()).queue()
        } catch (_: NumberFormatException) {
            YVtils.instance.server.consoleSender.sendMessage(Language().directFormat(
                "Invalid channel ID: '$channelID'! Make sure to put a valid channel ID in the config file or disable this feature! (plugins/YVtils-SMP/discord/config.yml/chatSync)",
                "Ungültige Kanal ID: '$channelID'! Kontrolliere das eine gültige Kanal ID in der Config steht oder deaktiviere dieses Feature! (plugins/YVtils-SMP/discord/config.yml/chatSync)"
            ))

            active = false
        }
    }

    fun discordToMinecraft(e: MessageReceivedEvent) {
        val author = e.author.name
        val message = e.message.contentDisplay

        if (!active) return
        if (e.author.isBot) return
        if (e.channel.id != channelID.toString()) return

        try {
            if (!e.member!!.hasPermission(Permission.valueOf(discordPermission))) return
        } catch (_: Exception) {
            if (!e.member!!.hasPermission(Permission.MESSAGE_SEND)) return
        }

        YVtils.instance.server.broadcast(ColorUtils().convert("<gray>[<#7289da>DISCORD<gray>]<white> $author<gray>:<white> $message"))
    }

    override fun onMessageReceived(e: MessageReceivedEvent) {
        discordToMinecraft(e)
    }
}