package yv.tils.smp.manager.commands.handle

import org.bukkit.attribute.Attribute
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

class HealHandler {
    /**
     * Heal player
     * @param player Player to heal
     * @param sender CommandSender to send messages
     */
    fun playerHeal(player: Player, sender: CommandSender = player) {
        try {
            player.health = player.getAttribute(Attribute.MAX_HEALTH)!!.value
        } catch (_: NoSuchFieldError) {
            player.addPotionEffect(
                org.bukkit.potion.PotionEffect(
                    org.bukkit.potion.PotionEffectType.INSTANT_HEALTH,
                    3,
                    20,
                    false,
                    false,
                    false
                )
            )
        }


        player.foodLevel = 20

        player.sendMessage(Language().getMessage(player.uniqueId, LangStrings.HEAL_PLAYER_HEALED))

        if (sender != player) {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(sender, LangStrings.HEAL_OTHER_PLAYER_HEALED),
                    listOf("player"),
                    listOf(player.name)
                )
            )
        }
    }
}