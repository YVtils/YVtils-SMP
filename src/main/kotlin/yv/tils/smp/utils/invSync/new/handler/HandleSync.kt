package yv.tils.smp.utils.invSync.new.handler

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.invSync.new.InvSyncType
import yv.tils.smp.utils.invSync.old.InvOpen.Companion.inventory

class HandleSync {
    companion object {
        var invSyncData: MutableMap<Player, InvSyncData> = HashMap()
    }

    data class InvSyncData(
        var inv: InvSyncContainer? = null,
        var target: Player? = null,
        val type: InvSyncType
    )

    data class InvSyncContainer(
        val location: Location,
        val inv: Inventory,
        val blockType: Material
    )

    /**
     * Create a new sync
     * @param player The player to sync
     * @param target The target player to sync
     * @param container The container to sync
     * @param type The type of sync
     */
    fun createSync(player: Player, target: Player? = null, container: Inventory? = null, type: InvSyncType) {
        if (container == null && target == null) {
            return
        }

        if (container != null && target != null) {
            return
        }

        if (container != null) {
            setInvSyncData(player, container, type)
        }

        if (target != null) {
            setInvSyncData(player, target.inventory, type)
        }
    }

    /**
     * Remove a sync
     * @param player The player to remove the sync
     */
    fun removeSync(player: HumanEntity) {
        removeInvSyncData(player as Player)
    }

    fun handleSync(player: Player, newInv: Inventory, openInv: Boolean = false) {
        val data = getInvSyncData(player) ?: return

        when (data.type) {
            InvSyncType.CONTAINER -> {
                println("Container")

                if (openInv) {
                    customInv(player)
                }
            }
            InvSyncType.ECSEE -> {

            }
            InvSyncType.INVSEE -> {
                val invType = newInv.type

                println("InvType: $invType")

                val target = data.target ?: return

                if (invType == InventoryType.PLAYER) {
                    PlayerInvSync().syncInventory(newInv, target, player, "player")
                } else if (invType == InventoryType.CHEST) {
                    PlayerInvSync().syncInventory(newInv, target, player, "invsee")
                }
            }
        }
    }

    private fun setInvSyncData(player: Player, inv: Inventory, type: InvSyncType) {
        invSyncData[player] = InvSyncData(
            inv = InvSyncContainer(
                location = player.location,
                inv = inv,
                blockType = player.location.block.type
            ),
            type = type
        )
    }

    private fun removeInvSyncData(player: Player) {
        invSyncData.remove(player)
    }

    private fun getInvSyncData(player: Player): InvSyncData? {
        return invSyncData[player]
    }

    private fun customInv(player: HumanEntity) {
        println("Custom Inv")

        val inv = invSyncData[player as Player]?.inv?.let {
            Bukkit.createInventory(
                player,
                it.inv.size,
                ColorUtils().convert("Container Clone")
            )
        } ?: return

        println("Inv: $inv")

        inv.contents = invSyncData[player]?.inv?.inv?.contents ?: return

        println("Contents: ${inv.contents}")

        player.openInventory(inv)
    }
}