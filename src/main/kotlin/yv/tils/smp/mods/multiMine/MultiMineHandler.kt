package yv.tils.smp.mods.multiMine

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig
import java.util.UUID

// TODO: Add fast Leave decay
// TODO: Try making the break process better for the performance
// TODO: Test what happens with worldguard or other protection plugins

class MultiMineHandler {
    companion object {
        val active = Config.config["modules.multiMine"] as Boolean
        val animationTime = MultiMineConfig.config["animationTime"] as Int
        val cooldownTime = MultiMineConfig.config["cooldownTime"] as Int
        val breakLimit = MultiMineConfig.config["breakLimit"] as Int

        val cooldownMap: MutableMap<UUID, Int> = mutableMapOf()
        val brokenMap: MutableMap<UUID, Int> = mutableMapOf()

        val blocks = MultiMineConfig.blockList
    }

    fun trigger(e: BlockBreakEvent) {
        if (!active) return

        val loc = e.block.location
        val player = e.player
        val item = player.inventory.itemInMainHand
        val block = e.block

        if (!checkBlock(e.block.type, blocks)) return
        if (!checkTool(block, item)) return
        if (checkCooldown(e.player.uniqueId)) return
        if (e.player.isSneaking) return
        if (e.player.gameMode != GameMode.SURVIVAL) return

        brokenMap[player.uniqueId] = 0

        setCooldown(player.uniqueId)
        breakBlock(loc, player, item)
    }

    private var itemBroke = false

    private fun breakBlock(loc: Location, player: Player, item: ItemStack) {
        if (brokenMap[player.uniqueId]!! >= breakLimit) {
            return
        }

        if (brokenMap[player.uniqueId]!! != 0) {
            try {
                if (itemBroke) return

                if (damageItem(player, 1, item)) {
                    return
                }
            } catch (_: NullPointerException) {
                return
            }
        }

        brokenMap[player.uniqueId] = brokenMap[player.uniqueId]!! + 1

        for (i in -1..1) {
            for (j in -1..1) {
                for (k in -1..1) {
                    val newLoc = Location(loc.world, loc.x + i, loc.y + j, loc.z + k)
                    val newBlock = newLoc.block

                    if (checkBlock(newBlock.type, blocks) && checkTool(newBlock, item)) {
                        Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
                            newBlock.breakNaturally(item, true, true)
                            breakBlock(newLoc, player, item)
                        }, animationTime * 1L)
                    }
                }
            }
        }
    }

    private fun damageItem(player: Player, damage: Int, item: ItemStack): Boolean {
        val damageable: Damageable = item.itemMeta as Damageable

        if (damageable.damage + damage >= item.type.maxDurability) {
            itemBroke = true
            player.inventory.removeItem(item)
            player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1f, 1f)
            return true
        } else {
            item.damage(damage, player)
            return false
        }
    }

    private fun setCooldown(player: UUID) {
        cooldownMap[player] = cooldownTime
    }

    private fun checkCooldown(player: UUID): Boolean {
        return cooldownMap[player] != null && cooldownMap[player] != 0
    }

    private fun checkBlock(material: Material, blocks: List<Material>): Boolean {
        return blocks.contains(material)
    }

    private fun checkTool(block: Block, tool: ItemStack): Boolean {
        if (tool.type == Material.AIR) return false

        return block.getDrops(tool).isNotEmpty()
    }

    init {
        Bukkit.getScheduler().runTaskTimer(YVtils.instance, Runnable {
            for (entry in cooldownMap) {
                if (entry.value == 0) continue
                cooldownMap[entry.key] = entry.value - 1
            }
        }, 20, 20)
    }
}