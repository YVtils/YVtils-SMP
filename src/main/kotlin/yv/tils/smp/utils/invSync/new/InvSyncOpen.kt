package yv.tils.smp.utils.invSync.new

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryOpenEvent
import org.objectweb.asm.Handle
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.utils.invSync.new.handler.HandleSync

class InvSyncOpen {
    fun onInventoryOpen(e: InventoryOpenEvent) {
        val player = e.player

        val isVanished = Vanish.vanish.containsKey(player.uniqueId) && Vanish.vanish[player.uniqueId]?.vanish ?: false

        println("Location: ${e.inventory.location}")

        if (e.inventory.location == null) {
            return
        }

        println("Vanished: $isVanished")

        if (isVanished) {
            val isSilentInteraction = Vanish.vanish[player.uniqueId]?.invInteraction ?: false

            println("Silent Interaction: $isSilentInteraction")

            println("Sync Data: ${HandleSync.invSyncData}")
            println("Player: $player")
            println("Contains: ${HandleSync.invSyncData.containsKey(player)}")

            if (HandleSync.invSyncData.containsKey(player)) {
                return
            }

            if (isSilentInteraction) {


                HandleSync().createSync(
                    player = player as Player,
                    container = e.inventory,
                    type = InvSyncType.CONTAINER
                )

                HandleSync().handleSync(player, e.inventory, true)
            }
        }
    }
}