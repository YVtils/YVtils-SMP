package yv.tils.smp.utils.invSync

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Barrel
import org.bukkit.block.Chest
import org.bukkit.block.ShulkerBox
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.invSee.EcSee
import yv.tils.smp.mods.admin.invSee.InvSeeListener
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class InvSync {
    fun onInvChange(e: InventoryClickEvent) {
        val player = e.whoClicked

        Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
            copyChange(player, e.inventory.location, e.inventory)
            originalChange(player, e.inventory.location, e.inventory)
        }, 1L)
    }

    private fun copyChange(player: HumanEntity, location: Location?, inv: Inventory) {
        if (location != null) {
            return
        }

        val invseeInvRaw = Language().getRawMessage(LangStrings.MODULE_INVSEE_INVENTORY)
        val invseeEcRaw = Language().getRawMessage(LangStrings.MODULE_INVSEE_ENDERCHEST)
        val invseeInv = ColorUtils().convert(invseeInvRaw).toString()
        val invseeEc = ColorUtils().convert(invseeEcRaw).toString()

        if (player.openInventory.title().toString().startsWith(invseeInv.split("<")[0])) {
            val target = Bukkit.getPlayer(InvSeeListener.invSee[player.uniqueId]?.player ?: return) ?: return
            playerInvEdit(inv, target, player, "invsee")
            return
        }

        if (player.openInventory.title().toString().startsWith(invseeEc.split("<")[0])) {
            val target = Bukkit.getPlayer(EcSee.ecSee[player.uniqueId] ?: return) ?: return
            target.enderChest.contents = inv.contents.map { it?.clone() }.toTypedArray()
            return
        }

        if (player.openInventory.title() != ColorUtils().convert("Container Clone")) {
            return
        }

        val containerLocation = InvOpen.containerPos[player.uniqueId] ?: return

        when (containerLocation.block.type) {
            Material.CHEST -> {
                val container = containerLocation.block.state as Chest
                container.inventory.contents = inv.contents.map { it?.clone() }.toTypedArray()
            }
            Material.BARREL -> {
                val container = containerLocation.block.state as Barrel
                container.inventory.contents = inv.contents.map { it?.clone() }.toTypedArray()
            }
            Material.SHULKER_BOX -> {
                val container = containerLocation.block.state as ShulkerBox
                container.inventory.contents = inv.contents.map { it?.clone() }.toTypedArray()
            }
            else -> {}
        }
    }

    private fun originalChange(player: HumanEntity, location: Location?, inv: Inventory) {
        if (location == null) {
            return
        }

        when (inv.type) {
            InventoryType.CRAFTING -> {
                for (entry in InvSeeListener.invSee.entries) {
                    val target = entry.value.player
                    if (target == player.uniqueId) {
                        playerInvEdit(inv, player, Bukkit.getPlayer(entry.key) ?: return, "player")
                    }
                }
                return
            }
            InventoryType.ENDER_CHEST -> {
                for (entry in EcSee.ecSee.entries) {
                    if (entry.value == player.uniqueId) {
                        val containerSpy: Player = Bukkit.getPlayer(entry.key) ?: continue
                        containerSpy.openInventory.topInventory.contents = inv.contents.map { it?.clone() }.toTypedArray()
                    }
                }
                return
            }
            else -> {}
        }

        if (player.openInventory.title() == ColorUtils().convert("Container Clone")) {
            return
        }

        for (entry in InvOpen.containerPos.entries) {
            if (entry.value == location) {
                val containerSpy = Bukkit.getPlayer(entry.key) ?: continue
                containerSpy.openInventory.topInventory.contents = inv.contents.map { it?.clone() }.toTypedArray()
            }
        }
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
                val inv2 = target.inventory

                playerInv.armorSlots[39] = inv2.getItem(39)
                playerInv.armorSlots[38] = inv2.getItem(38)
                playerInv.armorSlots[37] = inv2.getItem(37)
                playerInv.armorSlots[36] = inv2.getItem(36)
                playerInv.offhandSlot[40] = inv2.getItem(40)

                for (i in 9..35) {
                    playerInv.invSlots[i] = inv2.getItem(i)
                }

                for (i in 0..8) {
                    playerInv.hotbarSlots[i] = inv2.getItem(i)
                }

                playerInv.inventoryType = inv.type
            }
            else -> {
                return
            }
        }

        println("playerInv: $playerInv")

        val invMap = combineMaps(translatePlayerInv(playerInv))

        println("invMap: $invMap")

        for ((invType, invSlots) in invMap) {
            println("SyncLoop: $invType, $invSlots")

            if (invType == InventoryType.CRAFTING) {
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

        if (playerInv.inventoryType == InventoryType.CRAFTING) {
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

            println("craftingInv: $craftingInv")

            invMap[InventoryType.CHEST] = craftingInv
        } else if (playerInv.inventoryType == InventoryType.CHEST) {
            val chestInv = PlayerInventory(
                mutableMapOf(),
                mutableMapOf(),
                mutableMapOf(),
                mutableMapOf(),
                InventoryType.CRAFTING
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

            println("chestInv: $chestInv")

            invMap[InventoryType.CRAFTING] = chestInv
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
