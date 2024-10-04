package yv.tils.smp.manager.listener

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import yv.tils.smp.YVtils
import yv.tils.smp.manager.commands.handle.GlobalMuteHandler
import yv.tils.smp.mods.admin.moderation.handler.MuteHandler
import yv.tils.smp.mods.discord.sync.chatSync.SyncChats
import yv.tils.smp.mods.fusionCrafting.manager.FusionCraftManage
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.global.Config

class PlayerChat : Listener {
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun onEvent(e: AsyncChatEvent) {
        SyncChats().minecraftToDiscord(e)
        colorizeChatMessage(e)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onEventHIGHEST(e: AsyncChatEvent) {
        FusionCraftManage().listenForEdit(e)
        MuteHandler().playerChat(e)
        GlobalMuteHandler().playerChatEvent(e)
    }

    private fun colorizeChatMessage(e: AsyncChatEvent) {
        if (!(Config.config["allowChatColors"] as Boolean)) return

        val message = ColorUtils().convertChatMessage(e.originalMessage())
        val sender = e.player

        e.isCancelled = true

        YVtils.instance.server.consoleSender.sendMessage(sender.displayName().append(ColorUtils().convert("<white>: ").append(message)))

        e.player.server.onlinePlayers.forEach { player ->
            player.sendMessage(sender.displayName().append(ColorUtils().convert("<white>: ").append(message)))
        }
    }
}