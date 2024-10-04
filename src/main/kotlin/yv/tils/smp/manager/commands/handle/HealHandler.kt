package yv.tils.smp.manager.commands.handle

import org.bukkit.attribute.Attribute
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

class HealHandler {
    fun playerHeal(player: Player, sender: CommandSender = player) {
        player.health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
        player.foodLevel = 20

        player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.HEAL_PLAYER_HEALED))

        if (sender != player) {
            if (sender is Player) {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(sender.uniqueId, LangStrings.HEAL_OTHER_PLAYER_HEALED),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            } else {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.HEAL_OTHER_PLAYER_HEALED),
                        listOf("player"),
                        listOf(player.name)
                    )
                )
            }
        }

    }
}