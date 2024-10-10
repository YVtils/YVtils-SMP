package yv.tils.smp.mods.sit

import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDismountEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.player.PlayerQuitEvent

class DismountListener {
    fun onDismount(e: EntityDismountEvent) {
        val sitManager = SitManager()

        if (e.entity is Player) {
            val player = e.entity as Player
            if (e.dismounted is ArmorStand) {
                val sit = e.dismounted as ArmorStand
                sitManager.standUp(player, sit, 0.0, 1.975, 0.0)
            }
        }
    }

    fun onQuit(e: PlayerQuitEvent) {
        val sitManager = SitManager()
        if (sitManager.isSitting(e.player.uniqueId)) {
            sitManager.sitGetter(e.player)
        }
    }
}