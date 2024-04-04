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
import yv.tils.smp.mods.admin.invSee.InvSee
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
        if (location != null) return

        val invsee_invRaw = Language().getRawMessage(LangStrings.MODULE_INVSEE_INVENTORY)
        val invsee_ecRaw = Language().getRawMessage(LangStrings.MODULE_INVSEE_ENDERCHEST)
        val invsee_inv = ColorUtils().convert(invsee_invRaw).toString()
        val invsee_ec = ColorUtils().convert(invsee_ecRaw).toString()

        if (player.openInventory.title().toString().startsWith(invsee_inv.split("<")[0])) {
            val target = Bukkit.getPlayer(InvSee.invSee[player.uniqueId] ?: return) ?: return

            playerInvEdit(inv, target)
        }

        if (player.openInventory.title().toString().startsWith(invsee_ec.split("<")[0])) {
            val target = Bukkit.getPlayer(EcSee.ecSee[player.uniqueId] ?: return) ?: return

            target.enderChest.contents = inv.contents
        }

        if (player.openInventory.title() != ColorUtils().convert("Container Clone")) return

        val containerLocation = InvOpen.containerPos[player.uniqueId] ?: return

        if (containerLocation.block.type == Material.CHEST) {
            val container = containerLocation.block.state as Chest
            container.inventory.contents = inv.contents
        } else if (containerLocation.block.type == Material.BARREL) {
            val container = containerLocation.block.state as Barrel
            container.inventory.contents = inv.contents
        } else if (containerLocation.block.type == Material.SHULKER_BOX) {
            val container = containerLocation.block.state as ShulkerBox
            container.inventory.contents = inv.contents
        }

    }

    private fun originalChange(player: HumanEntity, location: Location?, inv: Inventory) {
        if (location != null) return

        if (inv.type == InventoryType.CRAFTING) {
            for (entry in InvSee.invSee.entries) {
                if (entry.value == player.uniqueId) {
                    val containerSpy: Player = Bukkit.getPlayer(entry.key) ?: continue
                    containerSpy.openInventory.topInventory.contents = inv.contents
                }
            }
        }

        if (inv.type == InventoryType.ENDER_CHEST) {
            for (entry in EcSee.ecSee.entries) {
                if (entry.value == player.uniqueId) {
                    val containerSpy: Player = Bukkit.getPlayer(entry.key) ?: continue
                    containerSpy.openInventory.topInventory.contents = inv.contents
                }
            }
        }

        if (player.openInventory.title() == ColorUtils().convert("Container Clone")) return

        for (entry in InvOpen.containerPos.entries) {

            if (entry.value == location) {
                val containerSpy = Bukkit.getPlayer(entry.key) ?: continue
                containerSpy.openInventory.topInventory.contents = inv.contents
            }
        }
    }

    private fun playerInvEdit(inv: Inventory, player: HumanEntity) {
        val offHand = inv.getItem(7)
        val armour: Array<ItemStack?>
        armour = arrayOf(
            inv.getItem(4),
            inv.getItem(3),
            inv.getItem(2),
            inv.getItem(1)
        )
        val invContent = inv.contents.copyOfRange(18, 44 + 1)
        val invHotbar = inv.contents.copyOfRange(45, 53 + 1)

        inv.setItem(53, ItemStack(Material.GREEN_BED))
        inv.setItem(45, ItemStack(Material.RED_BED))

        inv.setItem(18, ItemStack(Material.GREEN_BED))
        inv.setItem(44, ItemStack(Material.RED_BED))


        val invMain = invHotbar + invContent

        player.inventory.contents = invMain
        player.inventory.armorContents = armour
        player.inventory.setItemInOffHand(offHand)
    }
}