package yv.tils.smp.mods.server.maintenance

import dev.jorel.commandapi.executors.CommandArguments
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class MaintenanceHandler {
    companion object {
        var maintenance: Boolean = false
    }

    private var oldState: Boolean = false

    fun maintenance(sender: CommandSender, args: CommandArguments? = null) {
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
        senderAnnouncement(sender)
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
                        Placeholder().replacer(
                            Language().getMessage(
                                player.uniqueId,
                                LangStrings.MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE
                            ),
                            listOf("prefix"),
                            listOf(Vars().prefix)
                        ),
                        PlayerKickEvent.Cause.PLUGIN
                    )
                }
            }
        }
    }

    private fun senderAnnouncement(sender: CommandSender) {
        if (oldState == maintenance) {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        sender,
                        LangStrings.MAINTENANCE_ALREADY_STATE
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
            return
        }

        if (maintenance) {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        sender,
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
                        sender,
                        LangStrings.MAINTENANCE_COMMAND_DEACTIVATE
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
        }
    }

    private fun saveState() {
        Config().changeValue("maintenance", maintenance)
    }

    fun loadState() {
        maintenance = Config.config["maintenance"] as Boolean
    }
}