package yv.tils.smp.mods.server.maintenance

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.stringArgument
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.player.PlayerKickEvent
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import kotlin.toString

class MaintenanceCMD {
    companion object {
        var maintenance: Boolean = false
    }

    var oldState: Boolean = false

    val command = commandTree("maintenance") {
        withPermission("yvtils.smp.command.maintenance")
        withUsage("maintenance [state]")

        stringArgument("state", true) {
            replaceSuggestions(ArgumentSuggestions.strings("true", "false", "toggle"))
            anyExecutor { sender, args ->
                maintenance(sender, args)
            }
        }

        anyExecutor { sender, _ ->
            maintenance(sender)
        }
    }

    private fun maintenance(sender: CommandSender, args: CommandArguments? = null) {
        val state = if (args?.get(0) == null) {
            "toggle"
        } else {
            args[0].toString()
        }

        when (state) {
            "toggle" -> {
                oldState = maintenance
                maintenance = !maintenance
            }

            "true" -> {
                oldState = maintenance
                maintenance = true
            }

            "false" -> {
                oldState = maintenance
                maintenance = false
            }
        }

        globalAnnouncement()
        senderAnnouncement(sender, state)
        saveState()
    }

    private fun globalAnnouncement() {
        if (oldState == maintenance) {
            return
        }

        for (player in Bukkit.getOnlinePlayers()) {
            if (maintenance) {
                if (!player.hasPermission("yvtils.smp.bypass.maintenance")) {
                    player.kick(
                        Language().getMessage(LangStrings.MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE),
                        PlayerKickEvent.Cause.PLUGIN
                    )
                }
            }
        }

        YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.GLOBALMUTE_DISABLE_ANNOUNCEMENT))
    }

    private fun senderAnnouncement(sender: CommandSender, event: String) {
        if (oldState == maintenance) {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        LangStrings.MAINTENANCE_ALREADY_STATE
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
                    Placeholder().replacer(
                        Language().getMessage(
                            LangStrings.MAINTENANCE_COMMAND_ACTIVATE
                        ),
                        listOf("prefix"),
                        listOf(Vars().prefix)
                    )
                )
            }

            "false" -> {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(
                            LangStrings.MAINTENANCE_COMMAND_DEACTIVATE
                        ),
                        listOf("prefix"),
                        listOf(Vars().prefix)
                    )
                )
            }

            "toggle" -> {
                if (maintenance) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(
                                LangStrings.MAINTENANCE_COMMAND_ACTIVATE
                            ),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        )
                    )
                } else {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(
                                LangStrings.MAINTENANCE_COMMAND_DEACTIVATE
                            ),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        )
                    )
                }
            }
        }
    }

    private fun saveState() {
        Config().changeValue("maintenance", maintenance)
    }
}