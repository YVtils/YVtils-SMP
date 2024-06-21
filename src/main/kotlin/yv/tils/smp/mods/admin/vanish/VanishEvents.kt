package yv.tils.smp.mods.admin.vanish

import org.bukkit.Bukkit
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import yv.tils.smp.YVtils

class VanishEvents {
    fun onRejoin(e: PlayerJoinEvent) {
        val player = e.player
        if (!Vanish.vanish.containsKey(player.uniqueId)) return
        Vanish.oldVanish[player.uniqueId] = Vanish.vanish[player.uniqueId]!!
        if (Vanish.vanish[player.uniqueId]!!) {
            Vanish().enableVanish(player)

            Vanish().playerHide(player)
        } else {
            Vanish().disableVanish(player)
        }
    }

    fun onOtherJoin(e: PlayerJoinEvent) {
        val target = e.player

        for ((key, value) in Vanish.vanish) {
            if (value) {
                val player = Bukkit.getPlayer(key) ?: return

                target.hidePlayer(YVtils.instance, player)
                player.hidePlayer(YVtils.instance, target)

                if (!Vanish.vanish.containsKey(target.uniqueId)) player.showPlayer(YVtils.instance, target)

                if (Vanish.layer[player.uniqueId] != 4) {
                    for ((key1, value1) in Vanish.layer) {
                        if (value1 <= Vanish.layer[player.uniqueId]!!) {
                            for (viewer in Bukkit.getOnlinePlayers()) {
                                if (viewer.uniqueId == key1 && value1 != 4 && Vanish.vanish.containsKey(viewer.uniqueId) && Vanish.vanish[viewer.uniqueId]!!) {
                                    player.showPlayer(YVtils.instance, viewer)
                                    viewer.showPlayer(YVtils.instance, player)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun onGamemodeSwitch(e: PlayerGameModeChangeEvent) {
        val player = e.player
        if (!Vanish.vanish.containsKey(player.uniqueId)) return
        if (Vanish.vanish[player.uniqueId]!!) {
            Vanish().enableVanish(player)
        } else {
            Vanish().disableVanish(player)
        }
    }

    fun onWorldChange(e: PlayerChangedWorldEvent) {
        val player = e.player
        if (!Vanish.vanish.containsKey(player.uniqueId)) return
        if (Vanish.vanish[player.uniqueId]!!) {
            Vanish().enableVanish(player)
        } else {
            Vanish().disableVanish(player)
        }
    }

    fun playerTarget(e: EntityTargetEvent) {
        if (e.target == null) return
        if (Vanish.mobTarget.containsKey(e.target!!.uniqueId) && Vanish.mobTarget[e.target!!.uniqueId]!!) {
            e.isCancelled = true
        }
    }
}