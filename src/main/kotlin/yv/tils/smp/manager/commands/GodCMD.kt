package yv.tils.smp.manager.commands

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerArgument
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.util.*

class GodCMD {
    companion object {
        var god: MutableMap<UUID, Boolean> = HashMap()
    }

    val command = commandTree("god") {
        withPermission("yvtils.smp.command.god")
        withUsage("god")

        playerArgument("player", true) {
            anyExecutor { sender, args ->
                if (sender !is Player && args[0] == null) {
                    sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                    return@anyExecutor
                }

                if (args[0] is Player) {
                    val target = args[0] as Player
                    godSwitch(target, sender)
                } else {
                    godSwitch(sender as Player)
                }
            }
        }
    }

    private fun godSwitch(player: Player, sender: CommandSender = player) {
        val uuid = player.uniqueId

        if (god[uuid] == null || god[uuid] == false) {
            god[uuid] = true
            FlyCMD().flySwitch(player)
            player.sendMessage(Language().getMessage(uuid, LangStrings.GODMODE_COMMAND_ENABLE))
        } else {
            god[uuid] = false
            FlyCMD().flySwitch(player)
            player.sendMessage(Language().getMessage(uuid, LangStrings.GODMODE_COMMAND_DISABLE))
        }

        if (player != sender) {
            if (sender is Player) {
                if (god[uuid] == null || god[uuid] == false) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(sender.uniqueId, LangStrings.GODMODE_COMMAND_DISABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                } else {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(sender.uniqueId, LangStrings.GODMODE_COMMAND_ENABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                }
            } else {
                if (god[uuid] == null || god[uuid] == false) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(LangStrings.GODMODE_COMMAND_ENABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                } else {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(LangStrings.GODMODE_COMMAND_DISABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                }
            }
        }
    }

    fun onDamage(e: EntityDamageEvent) {
        if (e.entity is Player) {
            val player = e.entity as Player
            val uuid = player.uniqueId

            if (god[uuid] == true) {
                e.isCancelled = true
            }
        }
    }
}