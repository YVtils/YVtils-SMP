package yv.tils.smp.mods.admin.vanish

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.color.ColorUtils

class VanishGUI {
    fun gui(player: Player) {
        val inv = Bukkit.createInventory(player,36, ColorUtils().convert("<#6D8896>Vanish"))

        var target = player

        if (Vanish.exec_target.containsKey(player.uniqueId)) {
            target = Bukkit.getPlayer(MojangAPI().UUID2Name(Vanish.exec_target[player.uniqueId]!!)!!)!!
        }

        // Vanish
        val vanish = ItemStack(Material.POTION)
        val vanishMeta = vanish.itemMeta as PotionMeta
        vanishMeta.displayName(ColorUtils().convert("<#96C8FF>Vanish"))
        vanishMeta.color = Color.fromRGB(246, 246, 246)
        vanishMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        vanish.itemMeta = vanishMeta

        inv.setItem(10, vanish)

        // Layer
        val layer = ItemStack(Material.FILLED_MAP)
        val layerMeta = layer.itemMeta
        layerMeta.displayName(ColorUtils().convert("<#96C8FF>Layer"))
        layerMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        layer.itemMeta = layerMeta

        inv.setItem(12, layer)

        // Item Pickup
        val itemPickup = ItemStack(Material.HOPPER)
        val itemPickupMeta = itemPickup.itemMeta
        itemPickupMeta.displayName(ColorUtils().convert("<#96C8FF>Item Pickup"))
        itemPickupMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        itemPickup.itemMeta = itemPickupMeta

        inv.setItem(14, itemPickup)

        // Inventory Interaction
        val invInteraction = ItemStack(Material.LIGHT_GRAY_SHULKER_BOX)
        val invInteractionMeta = invInteraction.itemMeta
        invInteractionMeta.displayName(ColorUtils().convert("<#96C8FF>Inventory Interaction"))
        invInteractionMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        invInteraction.itemMeta = invInteractionMeta

        inv.setItem(15, invInteraction)

        // Mob Target
        val mobTarget = ItemStack(Material.SPAWNER)
        val mobTargetMeta = mobTarget.itemMeta
        mobTargetMeta.displayName(ColorUtils().convert("<#96C8FF>Mob Target"))
        mobTargetMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        mobTarget.itemMeta = mobTargetMeta

        inv.setItem(16, mobTarget)

        guiToggles(inv, target)

        // Filler
        val filler = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = filler.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
        filler.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, filler)
            }
        }

        if (Vanish.vanish.containsKey(target.uniqueId)) {
            Vanish.oldVanish[target.uniqueId] = Vanish.vanish[target.uniqueId]!!
        }

        player.openInventory(inv)
    }

    private fun guiToggles(inv: Inventory, player: Player) {
        // Vanish Toggle
        if (!Vanish.vanish.containsKey(player.uniqueId)) {
            val vanishToggle = ItemStack(Material.RED_DYE)
            val vanishToggleMeta = vanishToggle.itemMeta
            vanishToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            vanishToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            vanishToggle.itemMeta = vanishToggleMeta

            inv.setItem(19, vanishToggle)
        }else if (Vanish.vanish[player.uniqueId] == true) {
            val vanishToggle = ItemStack(Material.LIME_DYE)
            val vanishToggleMeta = vanishToggle.itemMeta
            vanishToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[ENABLED]"))
            vanishToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            vanishToggle.itemMeta = vanishToggleMeta

            inv.setItem(19, vanishToggle)
        } else {
            val vanishToggle = ItemStack(Material.RED_DYE)
            val vanishToggleMeta = vanishToggle.itemMeta
            vanishToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            vanishToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            vanishToggle.itemMeta = vanishToggleMeta

            inv.setItem(19, vanishToggle)
        }

        // Layer Toggle
        if (!Vanish.layer.containsKey(player.uniqueId)) {
            val layerToggle = ItemStack(Material.RED_DYE)
            val layerToggleMeta = layerToggle.itemMeta
            layerToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            layerToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            layerToggle.itemMeta = layerToggleMeta

            inv.setItem(21, layerToggle)
        } else if (Vanish.layer[player.uniqueId] == 1) {
            val layerToggle = ItemStack(Material.LIME_DYE)
            val layerToggleMeta = layerToggle.itemMeta
            layerToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[ENABLED]"))
            layerToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            layerToggle.itemMeta = layerToggleMeta

            inv.setItem(21, layerToggle)
            inv.getItem(12)?.amount = 1
        } else if (Vanish.layer[player.uniqueId] == 2) {
            val layerToggle = ItemStack(Material.LIME_DYE)
            val layerToggleMeta = layerToggle.itemMeta
            layerToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[ENABLED]"))
            layerToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            layerToggle.itemMeta = layerToggleMeta

            inv.setItem(21, layerToggle)
            inv.getItem(12)?.amount = 2
        } else if (Vanish.layer[player.uniqueId] == 3) {
            val layerToggle = ItemStack(Material.LIME_DYE)
            val layerToggleMeta = layerToggle.itemMeta
            layerToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[ENABLED]"))
            layerToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            layerToggle.itemMeta = layerToggleMeta

            inv.setItem(21, layerToggle)
            inv.getItem(12)?.amount = 3
        } else {
            val layerToggle = ItemStack(Material.RED_DYE)
            val layerToggleMeta = layerToggle.itemMeta
            layerToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            layerToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            layerToggle.itemMeta = layerToggleMeta

            inv.setItem(21, layerToggle)
        }

        // Item Pickup Toggle
        if (!Vanish.itemPickup.containsKey(player.uniqueId)) {
            val itemPickupToggle = ItemStack(Material.RED_DYE)
            val itemPickupToggleMeta = itemPickupToggle.itemMeta
            itemPickupToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            itemPickupToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            itemPickupToggle.itemMeta = itemPickupToggleMeta

            inv.setItem(23, itemPickupToggle)
        } else if (Vanish.itemPickup[player.uniqueId] == true) {
            val itemPickupToggle = ItemStack(Material.LIME_DYE)
            val itemPickupToggleMeta = itemPickupToggle.itemMeta
            itemPickupToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[ENABLED]"))
            itemPickupToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            itemPickupToggle.itemMeta = itemPickupToggleMeta

            inv.setItem(23, itemPickupToggle)
        } else {
            val itemPickupToggle = ItemStack(Material.RED_DYE)
            val itemPickupToggleMeta = itemPickupToggle.itemMeta
            itemPickupToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            itemPickupToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            itemPickupToggle.itemMeta = itemPickupToggleMeta

            inv.setItem(23, itemPickupToggle)
        }

        // Inventory Interaction Toggle
        if (!Vanish.invInteraction.containsKey(player.uniqueId)) {
            val invInteractionToggle = ItemStack(Material.RED_DYE)
            val invInteractionToggleMeta = invInteractionToggle.itemMeta
            invInteractionToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            invInteractionToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            invInteractionToggle.itemMeta = invInteractionToggleMeta

            inv.setItem(24, invInteractionToggle)
        } else if (Vanish.invInteraction[player.uniqueId] == true) {
            val invInteractionToggle = ItemStack(Material.LIME_DYE)
            val invInteractionToggleMeta = invInteractionToggle.itemMeta
            invInteractionToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[ENABLED]"))
            invInteractionToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            invInteractionToggle.itemMeta = invInteractionToggleMeta

            inv.setItem(24, invInteractionToggle)
        } else {
            val invInteractionToggle = ItemStack(Material.RED_DYE)
            val invInteractionToggleMeta = invInteractionToggle.itemMeta
            invInteractionToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            invInteractionToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            invInteractionToggle.itemMeta = invInteractionToggleMeta

            inv.setItem(24, invInteractionToggle)
        }

        // Mob Target Toggle
        if (!Vanish.mobTarget.containsKey(player.uniqueId)) {
            val mobTargetToggle = ItemStack(Material.RED_DYE)
            val mobTargetToggleMeta = mobTargetToggle.itemMeta
            mobTargetToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            mobTargetToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            mobTargetToggle.itemMeta = mobTargetToggleMeta

            inv.setItem(25, mobTargetToggle)
        } else if (Vanish.mobTarget[player.uniqueId] == true) {
            val mobTargetToggle = ItemStack(Material.LIME_DYE)
            val mobTargetToggleMeta = mobTargetToggle.itemMeta
            mobTargetToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[ENABLED]"))
            mobTargetToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            mobTargetToggle.itemMeta = mobTargetToggleMeta

            inv.setItem(25, mobTargetToggle)
        } else {
            val mobTargetToggle = ItemStack(Material.RED_DYE)
            val mobTargetToggleMeta = mobTargetToggle.itemMeta
            mobTargetToggleMeta.displayName(ColorUtils().convert("<#96C8FF>[DISABLED]"))
            mobTargetToggleMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
            mobTargetToggle.itemMeta = mobTargetToggleMeta

            inv.setItem(25, mobTargetToggle)
        }
    }

    fun invInteraction(e: InventoryClickEvent) {
        val player = e.whoClicked as Player

        var target = player

        if (Vanish.exec_target.containsKey(player.uniqueId)) {
            target = Bukkit.getPlayer(MojangAPI().UUID2Name(Vanish.exec_target[player.uniqueId]!!)!!)!!
        }

        println(player.openInventory.title() == ColorUtils().convert("<#6D8896>Vanish"))

        if (player.openInventory.title() == ColorUtils().convert("<#6D8896>Vanish") && e.inventory.size == 36 && e.inventory.location == null) {
            e.isCancelled = true

            when (e.slot) {
                19 -> {
                    if (Vanish.vanish.containsKey(target.uniqueId)) {
                        vanishRegister(target, !Vanish.vanish[target.uniqueId]!!)
                    } else {
                        vanishRegister(target, true)
                    }
                }
                12 -> {
                    if (Vanish.layer.containsKey(target.uniqueId)) {
                        if (e.click.isLeftClick) {
                            if (Vanish.layer[target.uniqueId] == 1) {
                                layerRegister(target, 2)
                            } else if (Vanish.layer[target.uniqueId] == 2) {
                                layerRegister(target, 3)
                            } else if (Vanish.layer[target.uniqueId] == 3) {
                                layerRegister(target, 1)
                            }
                        } else if (e.click.isRightClick) {
                            if (Vanish.layer[target.uniqueId] == 1) {
                                layerRegister(target, 3)
                            } else if (Vanish.layer[target.uniqueId] == 2) {
                                layerRegister(target, 1)
                            } else if (Vanish.layer[target.uniqueId] == 3) {
                                layerRegister(target, 2)
                            }
                        }
                    } else {
                        layerRegister(target, 1)
                    }
                }
                21 -> {
                    if (Vanish.layer.containsKey(target.uniqueId)) {
                        if (Vanish.layer[target.uniqueId] == 1) {
                            layerRegister(target, 4)
                        } else if (Vanish.layer[target.uniqueId] == 2) {
                            layerRegister(target, 4)
                        } else if (Vanish.layer[target.uniqueId] == 3) {
                            layerRegister(target, 4)
                        } else if (Vanish.layer[target.uniqueId] == 4) {
                            layerRegister(target, 1)
                        }
                    } else {
                        layerRegister(target, 1)
                    }
                }
                23 -> {
                    if (Vanish.itemPickup.containsKey(target.uniqueId)) {
                        itemPickupRegister(target, !Vanish.itemPickup[target.uniqueId]!!)
                    } else {
                        itemPickupRegister(target, true)
                    }
                }
                24 -> {
                    if (Vanish.invInteraction.containsKey(target.uniqueId)) {
                        invInteractionRegister(target, !Vanish.invInteraction[target.uniqueId]!!)
                    } else {
                        invInteractionRegister(target, true)
                    }
                }
                25 -> {
                    if (Vanish.mobTarget.containsKey(target.uniqueId)) {
                        mobTargetRegister(target, !Vanish.mobTarget[target.uniqueId]!!)
                    } else {
                        mobTargetRegister(target, true)
                    }
                }
            }
            guiToggles(e.inventory, target)
        }
    }

    fun vanishRegister(player: Player, vanish_bool: Boolean) {
        val uuid = player.uniqueId

        if (Vanish.vanish.containsKey(uuid)) {
            Vanish.oldVanish[uuid] = Vanish.vanish[uuid]!!
        }

        Vanish.vanish[uuid] = vanish_bool
    }

    fun layerRegister(player: Player, layer_int: Int) {
        val uuid = player.uniqueId
        Vanish.layer[uuid] = layer_int
    }

    fun itemPickupRegister(player: Player, itemPickup_bool: Boolean) {
        val uuid = player.uniqueId
        Vanish.itemPickup[uuid] = itemPickup_bool
    }

    fun invInteractionRegister(player: Player, invInteraction_bool: Boolean) {
        val uuid = player.uniqueId
        Vanish.invInteraction[uuid] = invInteraction_bool
    }

    fun mobTargetRegister(player: Player, mobTarget_bool: Boolean) {
        val uuid = player.uniqueId
        Vanish.mobTarget[uuid] = mobTarget_bool
    }

    fun guiClose(e: InventoryCloseEvent) {
        val player = e.player as Player
        var target = player

        if (Vanish.exec_target.containsKey(player.uniqueId)) {
            target = Bukkit.getPlayer(MojangAPI().UUID2Name(Vanish.exec_target[player.uniqueId]!!)!!)!!
        }

        if (player.openInventory.title() == ColorUtils().convert("<#6D8896>Vanish") && e.inventory.size == 36 && e.inventory.location == null) {
            if (Vanish.vanish[target.uniqueId]!!) {
                Vanish().enableVanish(target)
            }else{
                Vanish().disableVanish(target)
            }
        }
    }
}