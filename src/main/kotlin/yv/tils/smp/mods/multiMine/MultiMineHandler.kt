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
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig
import java.util.UUID

// TODO: Add fast Leave decay
// TODO: Try making the break process better for the performance
// TODO: Add command to toggle multiMine and save in save.yml

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
        val uuid = player.uniqueId
        val item = player.inventory.itemInMainHand
        val block = e.block

        if (!player.hasPermission("yvtils.smp.multiMine")) return
        if (!MultiMineConfig().getPlayerSetting(uuid.toString())) return
        if (!checkBlock(e.block.type, blocks)) return
        if (!checkTool(block, item)) return
        if (checkCooldown(e.player.uniqueId)) return
        if (player.isSneaking) return
        if (player.gameMode != GameMode.SURVIVAL) return

        brokenMap[player.uniqueId] = 0

        setCooldown(player.uniqueId)
        registerBlocks(loc, player, item)
    }

    private var itemBroke = false

    private fun registerBlocks(loc: Location, player: Player, item: ItemStack) {
        if (brokenMap[player.uniqueId]!! >= breakLimit) {
            return
        }

        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    if (x == 0 && y == 0 && z == 0) continue
                    val newLoc = Location(loc.world, loc.x + x, loc.y + y, loc.z + z)
                    val newBlock = newLoc.block

                    Bukkit.getScheduler().runTaskLater(YVtils.instance, Runnable {
                        if (!breakBlock(newBlock, player, item)) {
                            return@Runnable
                        } else {
                            registerBlocks(newLoc, player, item)
                        }
                    }, animationTime * 1L)
                }
            }
        }
    }

    /**
     * Breaks the blocks
     * @param block The block to break
     * @param player The player who is breaking the block
     * @param item The item the player is using to break the block
     * @return true if the block was broken
     */
    private fun breakBlock(block: Block, player: Player, item: ItemStack): Boolean {
        if (checkBlock(block.type, blocks) && checkTool(block, item)) {
            if (brokenMap[player.uniqueId]!! != 0) {
                try {
                    if (itemBroke) return false

                    if (damageItem(player, 1, item)) {
                        return false
                    }
                } catch (_: NullPointerException) {
                    return false
                }
            }

            brokenMap[player.uniqueId] = brokenMap[player.uniqueId]!! + 1

            block.breakNaturally(item, true, true)
            return true
        }
        return false
    }

    /**
     * Damages the item
     * @param player The player who is breaking the block
     * @param damage The amount of damage to deal to the item
     * @param item The item to damage
     * @return true if the item broke
     */
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
        if (tool.type.maxDurability.toInt() == 0) return false

        return block.getDrops(tool).isNotEmpty()
    }

    fun toggle(sender: Player) {
        val uuid = sender.uniqueId.toString()
        val value = MultiMineConfig().getPlayerSetting(uuid)

        MultiMineConfig().changePlayerSetting(uuid, !value)

        sender.sendMessage(
            if (!value) {
                Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_TOGGLE_ACTIVATE)
            } else {
                Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_TOGGLE_DEACTIVATE)
            }
        )
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