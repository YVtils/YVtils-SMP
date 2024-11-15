package yv.tils.smp.mods.admin.vanish.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.utils.color.ColorUtils

class VHandler {
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked

        if (e.inventory.location != null) {
            return
        }


        if (player.openInventory.title() == ColorUtils().convert("<#6D8896>Vanish") && e.inventory.size == 36 && e.inventory.location == null) {
            return
        }

        e.isCancelled = true

        val vanishToggleSlot = 19
        val layerChangeSlot = 12
        val layerToggleSlot = 21
        val itemPickupToggleSlot = 23
        val invInteractionToggleSlot = 24
        val mobTargetToggleSlot = 25

        val target = Vanish.exec_target[player.uniqueId]!!
        val vData = Vanish.vanish[target]!!

        when (e.slot) {
            vanishToggleSlot -> {
                vData.oldVanish = vData.vanish
                vData.vanish = !vData.vanish

                val guiElement = VBuilder().toggleSetting(vData, player as Player)

                for ((slot, item) in guiElement) {
                    e.inventory.setItem(slot, item)
                }
            }
            layerChangeSlot -> {
                val click = e.click

                if (click.isLeftClick) {
                    vData.layer++

                    if (vData.layer > 3) {
                        vData.layer = 1
                    }
                } else if (click.isRightClick) {
                    vData.layer--

                    if (vData.layer < 1) {
                        vData.layer = 3
                    }
                }

                val guiElement = VBuilder().layerSetting(vData, player as Player)

                for ((slot, item) in guiElement) {
                    e.inventory.setItem(slot, item)
                }
            }
            layerToggleSlot -> {
                vData.layer = 4

                val guiElement = VBuilder().layerSetting(vData, player as Player)

                for ((slot, item) in guiElement) {
                    e.inventory.setItem(slot, item)
                }
            }
            itemPickupToggleSlot -> {
                vData.itemPickup = !vData.itemPickup

                val guiElement = VBuilder().itemPickupSetting(vData, player as Player)

                for ((slot, item) in guiElement) {
                    e.inventory.setItem(slot, item)
                }
            }
            invInteractionToggleSlot -> {
                vData.invInteraction = !vData.invInteraction

                val guiElement = VBuilder().invInteractionSetting(vData, player as Player)

                for ((slot, item) in guiElement) {
                    e.inventory.setItem(slot, item)
                }
            }
            mobTargetToggleSlot -> {
                vData.mobTarget = !vData.mobTarget

                val guiElement = VBuilder().mobTargetSetting(vData, player as Player)

                for ((slot, item) in guiElement) {
                    e.inventory.setItem(slot, item)
                }
            }
        }

        Vanish.vanish[player.uniqueId] = vData
    }

    fun onInventoryClose(e:InventoryCloseEvent) {
        val player = e.player as Player
        var target = player

        if (player.openInventory.title() == ColorUtils().convert("<#6D8896>Vanish") && e.inventory.size == 36 && e.inventory.location == null) {
            if (Vanish.exec_target.containsKey(player.uniqueId)) {
                target = Bukkit.getPlayer(Vanish.exec_target[player.uniqueId]!!)!!
            }

            val vData = Vanish.vanish[target.uniqueId]!!

            if (vData.oldVanish != vData.vanish) {
                if (vData.vanish) {
                    Vanish().enableVanish(target)
                } else {
                    Vanish().disableVanish(target)
                }
            }
        }
    }
}