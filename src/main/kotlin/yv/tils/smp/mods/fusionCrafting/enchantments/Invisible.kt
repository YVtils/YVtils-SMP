package yv.tils.smp.mods.fusionCrafting.enchantments

import org.bukkit.Bukkit
import org.bukkit.entity.ItemFrame
import org.bukkit.event.hanging.HangingBreakEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.inventory.ItemStack
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import java.util.UUID

class Invisible {
    companion object {
        val itemFrames = mutableMapOf<UUID, ItemStack>()
    }

    fun onHangingPlace(e: HangingPlaceEvent) {
        val item = e.itemStack
        val entity = e.entity

        if (item != null) {
            if (item.hasItemMeta() && item.itemMeta!!.persistentDataContainer.has(FusionKeys.FUSION_INVISIBLE.key)) {
                if (entity is ItemFrame) {
                    entity.isVisible = false

                    itemFrames[entity.uniqueId] = item

                    Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
                        if (entity.item.isEmpty && itemFrames.contains(entity.uniqueId)) {
                            val location = entity.location
                            val itemFrame = itemFrames[entity.uniqueId]!!.clone()

                            itemFrame.amount = 1

                            itemFrames.remove(entity.uniqueId)
                            entity.remove()

                            location.world!!.dropItem(location, itemFrame)
                        } else {
                            itemFrames.remove(entity.uniqueId)
                        }
                    }, 20 * 60 * 1)
                }
            }
        }
    }

    fun onHangingBreak(e: HangingBreakEvent) {
        val entity = e.entity

        if (entity is ItemFrame) {
            if (itemFrames.contains(entity.uniqueId)) {
                val item = itemFrames[entity.uniqueId]!!.clone()
                val location = entity.location

                item.amount = 1

                e.isCancelled = true

                itemFrames.remove(entity.uniqueId)
                entity.remove()
                location.world!!.dropItem(location, item)
            }
        }

    }
}