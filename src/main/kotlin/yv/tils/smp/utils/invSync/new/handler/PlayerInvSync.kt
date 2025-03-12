package yv.tils.smp.utils.invSync.new.handler

import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yv.tils.smp.mods.admin.invSee.InvSeeListener

class PlayerInvSync {
    fun syncInventory(inv: Inventory, target: HumanEntity, player: HumanEntity, cause: String) {
        playerInvEdit(inv, target, player, cause)
    }

    data class PlayerInventory(
        val armorSlots: MutableMap<Int, ItemStack?>,
        val invSlots: MutableMap<Int, ItemStack?>,
        val hotbarSlots: MutableMap<Int, ItemStack?>,
        var offhandSlot: MutableMap<Int, ItemStack?>,
        var inventoryType: InventoryType
    )

    private fun playerInvEdit(inv: Inventory, target: HumanEntity, player: HumanEntity, cause: String) {
        val playerInv = PlayerInventory(
            mutableMapOf(),
            mutableMapOf(),
            mutableMapOf(),
            mutableMapOf(),
            inv.type
        )

        when (cause) {
            "invsee" -> {
                playerInv.armorSlots[1] = inv.getItem(1)
                playerInv.armorSlots[2] = inv.getItem(2)
                playerInv.armorSlots[3] = inv.getItem(3)
                playerInv.armorSlots[4] = inv.getItem(4)
                playerInv.offhandSlot[7] = inv.getItem(7)

                for (i in 18..44) {
                    playerInv.invSlots[i] = inv.getItem(i)
                }

                for (i in 45..53) {
                    playerInv.hotbarSlots[i] = inv.getItem(i)
                }

                playerInv.inventoryType = inv.type
            }
            "player" -> {
                playerInv.armorSlots[39] = inv.getItem(39)
                playerInv.armorSlots[38] = inv.getItem(38)
                playerInv.armorSlots[37] = inv.getItem(37)
                playerInv.armorSlots[36] = inv.getItem(36)
                playerInv.offhandSlot[40] = inv.getItem(40)

                for (i in 9..35) {
                    playerInv.invSlots[i] = inv.getItem(i)
                }

                for (i in 0..8) {
                    playerInv.hotbarSlots[i] = inv.getItem(i)
                }

                playerInv.inventoryType = inv.type
            }
            else -> {
                return
            }
        }

        val invMap = combineMaps(translatePlayerInv(playerInv))

        for ((invType, invSlots) in invMap) {
            if (invType == InventoryType.PLAYER) {
                for ((slot, item) in invSlots) {
                    target.inventory.setItem(slot, item)
                }
            } else if (invType == InventoryType.CHEST) {
                for ((slot, item) in invSlots) {
                    InvSeeListener.invSee[player.uniqueId]?.inv?.setItem(slot, item)
                }
            }
        }
    }

    private fun translatePlayerInv(playerInv: PlayerInventory): MutableMap<InventoryType, PlayerInventory> {
        val invMap = mutableMapOf<InventoryType, PlayerInventory>()

        invMap[playerInv.inventoryType] = playerInv

        if (playerInv.inventoryType == InventoryType.PLAYER) {
            val craftingInv = PlayerInventory(
                mutableMapOf(),
                mutableMapOf(),
                mutableMapOf(),
                mutableMapOf(),
                InventoryType.CHEST
            )

            craftingInv.armorSlots[1] = playerInv.armorSlots[39]
            craftingInv.armorSlots[2] = playerInv.armorSlots[38]
            craftingInv.armorSlots[3] = playerInv.armorSlots[37]
            craftingInv.armorSlots[4] = playerInv.armorSlots[36]

            craftingInv.offhandSlot[7] = playerInv.offhandSlot[40]

            for (i in 9..35) {
                craftingInv.invSlots[i+9] = playerInv.invSlots[i]
            }

            for (i in 0..8) {
                craftingInv.hotbarSlots[i+45] = playerInv.hotbarSlots[i]
            }

            invMap[InventoryType.CHEST] = craftingInv
        } else if (playerInv.inventoryType == InventoryType.CHEST) {
            val chestInv = PlayerInventory(
                mutableMapOf(),
                mutableMapOf(),
                mutableMapOf(),
                mutableMapOf(),
                InventoryType.PLAYER
            )
            chestInv.armorSlots[39] = playerInv.armorSlots[1]
            chestInv.armorSlots[38] = playerInv.armorSlots[2]
            chestInv.armorSlots[37] = playerInv.armorSlots[3]
            chestInv.armorSlots[36] = playerInv.armorSlots[4]

            chestInv.offhandSlot[40] = playerInv.offhandSlot[7]

            for (i in 18..44) {
                chestInv.invSlots[i-9] = playerInv.invSlots[i]
            }

            for (i in 45..53) {
                chestInv.hotbarSlots[i-45] = playerInv.hotbarSlots[i]
            }

            invMap[InventoryType.PLAYER] = chestInv
        }

        return invMap
    }

    private fun combineMaps(playerInv: MutableMap<InventoryType, PlayerInventory>): MutableMap<InventoryType, MutableMap<Int, ItemStack?>> {
        val invMap = mutableMapOf<InventoryType, MutableMap<Int, ItemStack?>>()

        for ((invType, inv) in playerInv) {
            val invSlots = mutableMapOf<Int, ItemStack?>()

            invSlots.putAll(inv.armorSlots)
            invSlots.putAll(inv.invSlots)
            invSlots.putAll(inv.hotbarSlots)
            invSlots.putAll(inv.offhandSlot)

            invMap[invType] = invSlots
        }

        return invMap
    }
}