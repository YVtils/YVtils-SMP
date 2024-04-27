package yv.tils.smp.mods.multiMine

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
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
// TODO: Fix XP drop (Only first block drops XP)

class MultiMineHandler {
    companion object {
        val active = Config.config["modules.multiMine"] as Boolean
        val animationTime = MultiMineConfig.config["animationTime"] as Int
        val cooldownTime = MultiMineConfig.config["cooldownTime"] as Int
        val breakLimit = MultiMineConfig.config["breakLimit"] as Int

        val cooldownMap: MutableMap<UUID, Int> = mutableMapOf()
        val brokenMap: MutableMap<UUID, Int> = mutableMapOf()

        val blocks = listOf(
            Material.OAK_LOG,
            Material.BIRCH_LOG,
            Material.SPRUCE_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.CRIMSON_STEM,
            Material.WARPED_STEM,
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_CRIMSON_STEM,
            Material.STRIPPED_WARPED_STEM,

            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.LAPIS_ORE,
            Material.REDSTONE_ORE,
            Material.COPPER_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE,
            Material.ANCIENT_DEBRIS,
            Material.GLOWSTONE,
            )
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

        brokenMap[player.uniqueId] = 0

        setCooldown(player.uniqueId)
        breakBlock(loc, player, item)
    }

    fun breakBlock(loc: Location, player: Player, item: ItemStack) {
        if (brokenMap[player.uniqueId]!! >= breakLimit) {
            return
        }

        if (brokenMap[player.uniqueId]!! != 0) {
            if (damageItem(player, 1, item)) {
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
                            newBlock.breakNaturally(item)
                            breakBlock(newLoc, player, item)
                        }, animationTime * 1L)
                    }
                }
            }
        }
    }

    fun damageItem(player: Player, damage: Int, item: ItemStack): Boolean {
        val damageable: Damageable = item.itemMeta as Damageable

        if (damageable.damage + damage >= item.type.maxDurability) {
            player.inventory.removeItem(item)
            return true
        } else {
            item.damage(damage, player)
            return false
        }
    }

    fun setCooldown(player: UUID) {
        cooldownMap[player] = cooldownTime
    }

    fun checkCooldown(player: UUID): Boolean {
        return cooldownMap[player] != null && cooldownMap[player] != 0
    }

    fun checkBlock(material: Material, blocks: List<Material>): Boolean {
        return blocks.contains(material)
    }

    fun checkTool(block: Block, tool: ItemStack): Boolean {
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