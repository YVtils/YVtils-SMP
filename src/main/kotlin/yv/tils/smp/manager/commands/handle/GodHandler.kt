package yv.tils.smp.manager.commands.handle

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.util.*

class GodHandler {
    companion object {
        var god: MutableMap<UUID, Boolean> = HashMap()
    }

    fun godSwitch(player: Player, sender: CommandSender = player) {
        val uuid = player.uniqueId

        if (god[uuid] == null || god[uuid] == false) {
            god[uuid] = true
            FlyHandler().flySwitch(player)
            player.sendMessage(Language().getMessage(uuid, LangStrings.GODMODE_COMMAND_ENABLE))
        } else {
            god[uuid] = false
            FlyHandler().flySwitch(player)
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