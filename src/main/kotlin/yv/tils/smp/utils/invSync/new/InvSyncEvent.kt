package yv.tils.smp.utils.invSync.new

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import yv.tils.smp.utils.invSync.new.handler.HandleSync

class InvSyncEvent {
    fun onInvDrag(e: InventoryDragEvent) {
        HandleSync().handleSync(e.whoClicked as Player, e.inventory)
    }

    fun onInvClick(e: InventoryClickEvent) {
        HandleSync().handleSync(e.whoClicked as Player, e.inventory)
    }

    fun onItemDrop(e: PlayerDropItemEvent) {
        HandleSync().handleSync(e.player, e.player.inventory)
    }

    fun onItemPickup(e: PlayerAttemptPickupItemEvent) {
        HandleSync().handleSync(e.player, e.player.inventory)
    }
}