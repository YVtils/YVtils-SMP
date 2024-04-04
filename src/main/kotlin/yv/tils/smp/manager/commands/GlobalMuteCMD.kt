package yv.tils.smp.manager.commands

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.stringArgument
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer
import yv.tils.smp.utils.internalAPI.Vars

class GlobalMuteCMD {
    companion object {
        var globalMute: Boolean = false
    }

    var oldState: Boolean = false

    val command = commandTree("globalmute") {
        withPermission("yvtils.smp.command.globalmute")
        withUsage("globalmute [state]")

        stringArgument("state", true) {
            replaceSuggestions(ArgumentSuggestions.strings("true", "false", "toggle"))
            anyExecutor { sender, args ->
                globalMute(sender, args)
            }
        }

        anyExecutor { sender, _ ->
            globalMute(sender)
        }
    }

    private fun globalMute(sender: CommandSender, args: CommandArguments? = null) {
        val state = if (args?.get(0) == null) {
            "toggle"
        } else {
            args[0].toString()
        }

        when (state) {
            "toggle" -> {
                oldState = globalMute
                globalMute = !globalMute
            }

            "true" -> {
                oldState = globalMute
                globalMute = true
            }

            "false" -> {
                oldState = globalMute
                globalMute = false
            }
        }

        globalAnnouncement(state)
        senderAnnouncement(sender, state)
    }

    private fun globalAnnouncement(event: String) {

        if (oldState == globalMute) {
            return
        }

        for (player in Bukkit.getOnlinePlayers()) {
            when (event) {
                "true" -> {
                    player.sendMessage(
                        StringReplacer().listReplacer(
                            Language().getMessage(
                                player.uniqueId,
                                LangStrings.GLOBALMUTE_ENABLE_ANNOUNCEMENT
                            ),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        )
                    )
                }

                "false" -> {
                    player.sendMessage(
                        StringReplacer().listReplacer(
                            Language().getMessage(
                                player.uniqueId,
                                LangStrings.GLOBALMUTE_DISABLE_ANNOUNCEMENT
                            ),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        )
                    )
                }

                "toggle" -> {
                    if (globalMute) {
                        player.sendMessage(
                            StringReplacer().listReplacer(
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
                            StringReplacer().listReplacer(
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
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.GLOBALMUTE_DISABLE_ANNOUNCEMENT))
    }

    private fun senderAnnouncement(sender: CommandSender, event: String) {

        if (oldState == globalMute) {
            sender.sendMessage(
                StringReplacer().listReplacer(
                    Language().getMessage(
                        LangStrings.GLOBALMUTE_ALREADY_STATE
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
            return
        }

        when (event) {
            "true" -> {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(
                            LangStrings.GLOBALMUTE_ENABLE_FEEDBACK
                        ),
                        listOf("prefix"),
                        listOf(Vars().prefix)
                    )
                )
            }

            "false" -> {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(
                            LangStrings.GLOBALMUTE_DISABLE_FEEDBACK
                        ),
                        listOf("prefix"),
                        listOf(Vars().prefix)
                    )
                )
            }

            "toggle" -> {
                if (globalMute) {
                    sender.sendMessage(
                        StringReplacer().listReplacer(
                            Language().getMessage(
                                LangStrings.GLOBALMUTE_ENABLE_FEEDBACK
                            ),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        )
                    )
                } else {
                    sender.sendMessage(
                        StringReplacer().listReplacer(
                            Language().getMessage(
                                LangStrings.GLOBALMUTE_DISABLE_FEEDBACK
                            ),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        )
                    )
                }
            }
        }
    }

    fun playerChatEvent(e: AsyncChatEvent) {
        if (globalMute) {
            if (e.player.hasPermission("yvtils.smp.bypass.globalmute")) {
                return
            }

            e.isCancelled = true
            e.player.sendMessage(
                StringReplacer().listReplacer(
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