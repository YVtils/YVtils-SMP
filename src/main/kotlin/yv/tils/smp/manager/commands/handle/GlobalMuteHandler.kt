package yv.tils.smp.manager.commands.handle

import dev.jorel.commandapi.executors.CommandArguments
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.sync.chatSync.SyncChats
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class GlobalMuteHandler {
    companion object {
        var globalMute: Boolean = false
    }

    var oldState: Boolean = false

    /**
     * Toggle global mute
     * @param sender CommandSender to send messages
     * @param args CommandArguments to get state
     */
    fun globalMute(sender: CommandSender, args: CommandArguments? = null) {
        val state = if (args?.get(0) == null) {
            "toggle"
        } else {
            args[0].toString()
        }

        when (state) {
            "toggle" -> {
                oldState = globalMute
                globalMute = !globalMute

                SyncChats.active = !globalMute
            }

            "true" -> {
                oldState = globalMute
                globalMute = true

                SyncChats.active = false
            }

            "false" -> {
                oldState = globalMute
                globalMute = false

                SyncChats.active = true
            }
        }

        globalAnnouncement(state)
        senderAnnouncement(sender, state)
    }

    /**
     * Send global announcement
     * @param event String of event
     */
    private fun globalAnnouncement(event: String) {
        if (oldState == globalMute) {
            return
        }

        for (player in Bukkit.getOnlinePlayers()) {
            if (globalMute) {
                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(
                            player.uniqueId,
                            LangStrings.GLOBALMUTE_ENABLE_ANNOUNCEMENT
                        ),
                        listOf("prefix"),
                        listOf(Vars().prefix)
                    )
                )
            } else {
                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(
                            player.uniqueId,
                            LangStrings.GLOBALMUTE_DISABLE_ANNOUNCEMENT
                        ),
                        listOf("prefix"),
                        listOf(Vars().prefix)
                    )
                )
            }
        }

        if (globalMute) {
            YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.GLOBALMUTE_ENABLE_ANNOUNCEMENT))
        } else {
            YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.GLOBALMUTE_DISABLE_ANNOUNCEMENT))
        }
    }

    /**
     * Send sender announcement
     * @param sender CommandSender to send messages
     * @param event String of event
     */
    private fun senderAnnouncement(sender: CommandSender, event: String) {
        if (oldState == globalMute) {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        sender,
                        LangStrings.GLOBALMUTE_ALREADY_STATE
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
            return
        }

        if (globalMute) {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        sender,
                        LangStrings.GLOBALMUTE_ENABLE_FEEDBACK
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
        } else {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        sender,
                        LangStrings.GLOBALMUTE_DISABLE_FEEDBACK
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
        }
    }

    /**
     * Handle player chat event for global mute
     * @param e AsyncChatEvent
     */
    fun playerChatEvent(e: AsyncChatEvent) {
        if (globalMute) {
            if (e.player.hasPermission("yvtils.smp.bypass.globalmute")) {
                return
            }

            e.isCancelled = true
            e.player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        e.player.uniqueId,
                        LangStrings.GLOBALMUTE_TRY_TO_WRITE
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
        }
    }
}