package yv.tils.smp.manager.listener

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import yv.tils.smp.YVtils
import yv.tils.smp.manager.commands.GlobalMuteCMD
import yv.tils.smp.mods.admin.moderation.Mute
import yv.tils.smp.mods.discord.sync.chatSync.SyncChats
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.global.Config

class PlayerChat : Listener {
    @EventHandler
    fun onEvent(e: AsyncChatEvent) {
        GlobalMuteCMD().playerChatEvent(e)
        Mute().playerChat(e)
        SyncChats().minecraftToDiscord(e)
        colorizeChatMessage(e)
    }

    private fun colorizeChatMessage(e: AsyncChatEvent) {
        if (Config.config["allowChatColors"] as Boolean) return

        val message = ColorUtils().convertChatMessage(e.originalMessage())
        val sender = e.player

        e.isCancelled = true

        YVtils.instance.server.consoleSender.sendMessage(sender.displayName().append(ColorUtils().convert("<white>: ").append(message)))

        e.player.server.onlinePlayers.forEach { player ->
            player.sendMessage(sender.displayName().append(ColorUtils().convert("<white>: ").append(message)))
        }
    }
}