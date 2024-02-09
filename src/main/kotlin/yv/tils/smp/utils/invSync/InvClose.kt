package yv.tils.smp.utils.invSync

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryCloseEvent
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.vanish.Vanish

class InvClose {
    fun onInvClose(e: InventoryCloseEvent) {
        val player = e.player

        if (InvOpen.invOpen.containsKey(player.uniqueId) && InvOpen.invOpen[player.uniqueId]!!) {
            InvOpen.invOpen.remove(player.uniqueId)
            InvOpen.inventory.remove(player.uniqueId)
            InvOpen.containerPos.remove(player.uniqueId)
        }

        if (!(Vanish.vanish.containsKey(player.uniqueId) && Vanish.vanish[player.uniqueId]!!) || !player.hasPermission("yvtils.smp.silentContainerOpen")) return
        for (p in player.world.players) {
            val distance = p.location.distance(player.location)

            Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
                if (distance <= 16) {
                    p.stopAllSounds()
                }
            }, 1L)
        }
    }
}