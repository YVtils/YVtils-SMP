package yv.tils.smp.utils.invSync


import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.utils.color.ColorUtils
import java.util.*

class InvOpen {
    companion object {
        var containerPos: MutableMap<UUID, Location> = HashMap()
        var inventory: MutableMap<UUID, Array<ItemStack?>> = HashMap()
        var invOpen: MutableMap<UUID, Boolean> = HashMap()
    }

    fun onInvOpen(e: InventoryOpenEvent) {
        val player = e.player

        if (Vanish.vanish.containsKey(player.uniqueId) && Vanish.vanish[player.uniqueId]!!) {
            if (invOpen.containsKey(player.uniqueId) && invOpen[player.uniqueId]!!) return

            if (e.inventory.location != null) {
                inventory[player.uniqueId] = e.inventory.contents
                containerPos[player.uniqueId] = e.inventory.location!!

                for (p in player.world.players) {
                    val distance = p.location.distance(player.location)

                    if (distance <= 16) {
                        p.stopAllSounds()
                    }
                }

                e.isCancelled = true

                player.closeInventory()

                customInv(player)
            }
        }
    }

    private fun customInv(player: HumanEntity) {
        val inv = inventory[player.uniqueId]?.let {
            Bukkit.createInventory(
                player,
                it.size,
                ColorUtils().convert("Container Clone")
            )
        }

        if (inv == null) return

        inv.contents = inventory[player.uniqueId]!!
        player.openInventory(inv)
    }
}