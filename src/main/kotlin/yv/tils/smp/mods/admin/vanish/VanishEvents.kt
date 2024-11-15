package yv.tils.smp.mods.admin.vanish

import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerJoinEvent

class VanishEvents {
    fun onRejoin(e: PlayerJoinEvent) {
        val player = e.player
        if (!Vanish.vanish.containsKey(player.uniqueId)) return
        if (Vanish.vanish[player.uniqueId]?.vanish!!) {
            Vanish().enableVanish(player, silent = true)
        }
    }

    fun onOtherJoin(e: PlayerJoinEvent) {
        val target = e.player

        for ((_, value) in Vanish.vanish) {
            if (value.vanish) {
                Vanish().hidePlayer(value.player, target)
            }
        }
    }

    fun onGamemodeSwitch(e: PlayerGameModeChangeEvent) {
        val player = e.player
        if (!Vanish.vanish.containsKey(player.uniqueId)) return
        if (Vanish.vanish[player.uniqueId]?.vanish!!) {
            Vanish().refreshVanish(player)
        }
    }

    fun onWorldChange(e: PlayerChangedWorldEvent) {
        val player = e.player
        if (!Vanish.vanish.containsKey(player.uniqueId)) return
        if (Vanish.vanish[player.uniqueId]?.vanish!!) {
            Vanish().refreshVanish(player)
        }
    }

    fun playerTarget(e: EntityTargetEvent) {
        if (e.target == null) return
        if (e.target !is Player) return
        val target = e.target as Player
        if (!Vanish.vanish.containsKey(target.uniqueId)) return
        if (Vanish.vanish[target.uniqueId]?.vanish!! && Vanish.vanish[target.uniqueId]?.mobTarget!!) {
            e.isCancelled = true
        }
    }
}