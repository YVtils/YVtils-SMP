package yv.tils.smp.mods.other

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.event.player.PlayerToggleFlightEvent
import yv.tils.smp.YVtils
import yv.tils.smp.manager.commands.FlyCMD
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

/**
 * Inspired by this Tutorial from Coole Pizza: https://www.youtube.com/watch?v=S9f_mFiYT50
 */
class SpawnElytra {
    companion object {
        var active = Config.config["spawnElytra.active"] as Boolean
        var radius = Config.config["spawnElytra.radius"] as Int
        var multiplyValue = Config.config["spawnElytra.multiplyValue"] as Int

        private lateinit var instance: SpawnElytra

        fun getInstance(): SpawnElytra {
            return instance
        }
    }

    private val main: YVtils = YVtils.instance
    private var flying: MutableList<Player> = ArrayList()
    private var boosted: MutableList<Player> = ArrayList()

    init {
        if (active) {
            instance = this

            Bukkit.getScheduler().runTaskTimer(main, Runnable {
                Bukkit.getWorld("world")?.players?.forEach { player ->
                    if (FlyCMD.fly.containsKey(player.uniqueId) && FlyCMD.fly[player.uniqueId] == true) return@forEach
                    if (player.gameMode != GameMode.SURVIVAL) return@forEach
                    player.allowFlight = isInSpawnRadius(player)
                    if (flying.contains(player) && !player.location.block.getRelative(BlockFace.DOWN).type.isAir) {
                        player.allowFlight = false
                        player.isFlying = false
                        player.isGliding = false
                        boosted.remove(player)
                        Bukkit.getScheduler().runTaskLater(main, Runnable { flying.remove(player) }, 5)
                    }
                }
            }, 0, 3)
        }
    }

    fun onWorldChange(e: PlayerChangedWorldEvent) {
        val player = e.player
        if (FlyCMD.fly.containsKey(player.uniqueId) && FlyCMD.fly[player.uniqueId] == true) return
        player.allowFlight = false
        player.isFlying = false
        player.isGliding = false
        boosted.remove(player)
        flying.remove(player)
    }

    fun onDoubleJump(e: PlayerToggleFlightEvent) {
        val player = e.player
        if (FlyCMD.fly.containsKey(player.uniqueId) && FlyCMD.fly[player.uniqueId] == true) return
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!isInSpawnRadius(player)) {
            if (player.world.name != "world") {
                player.allowFlight = false
            }
            return
        }
        e.isCancelled = true
        player.isGliding = true
        flying.add(player)

        player.sendActionBar(
            Placeholder().replacer(
                Language().getMessage(player.uniqueId, LangStrings.SPAWN_ELYTRA_BOOST),
                listOf(
                    "key"
                ),
                listOf(
                    "<key:key.swapOffhand>"
                )
            )
        )
    }

    fun onLandDamage(e: EntityDamageEvent) {
        if (e.entityType == EntityType.PLAYER
            && (e.cause == EntityDamageEvent.DamageCause.FALL || e.cause == EntityDamageEvent.DamageCause.FLY_INTO_WALL)
            && flying.contains(e.entity)
        ) e.isCancelled = true
    }

    fun onHandSwap(e: PlayerSwapHandItemsEvent) {
        if (flying.contains(e.player)) {
            if (boosted.contains(e.player)) return
            e.isCancelled = true
            boosted.add(e.player)
            e.player.velocity = e.player.location.direction.multiply(multiplyValue.toDouble())
        }
    }

    fun onToggleGlide(e: EntityToggleGlideEvent) {
        if (e.entityType != EntityType.PLAYER) return

        var player = e.entity as Player

        if (player.inventory.chestplate?.type == Material.ELYTRA) return

        if (e.entityType == EntityType.PLAYER && flying.contains(e.entity) || !e.isGliding) e.isCancelled = true
    }

    fun isInSpawnRadius(player: Player): Boolean {
        if (player.world.name != "world") return false
        return player.location.distance(player.world.spawnLocation) <= radius
    }
}