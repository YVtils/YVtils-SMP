package yv.tils.smp.manager.commands.handle

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.block.BlockFace
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.util.*

class FlyHandler {
    companion object {
        var fly: MutableMap<UUID, Boolean> = HashMap()
        var airAfter: MutableMap<UUID, Boolean> = HashMap()
    }

    /**
     * Toggle fly for player
     * @param player Player to toggle fly
     * @param sender CommandSender to send messages
     * @param silent Boolean to toggle silent mode
     */
    fun flySwitch(player: Player, sender: CommandSender = player, silent: Boolean = false) {
        val uuid = player.uniqueId

        if (fly[uuid] == null || fly[uuid] == false) {
            fly[uuid] = true
            player.allowFlight = true
            player.isFlying = true
            if (!silent) {
                player.sendMessage(Language().getMessage(uuid, LangStrings.FLY_COMMAND_ENABLE))

                if (player != sender) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(sender, LangStrings.FLY_COMMAND_ENABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                }
            }
        } else {
            if (player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) {
                fly[uuid] = false
            } else {
                airAfter[uuid] = player.location.block.getRelative(BlockFace.DOWN).type.isAir

                fly[uuid] = false
                player.allowFlight = false
                player.isFlying = false
            }

            if (!silent) {
                player.sendMessage(Language().getMessage(uuid, LangStrings.FLY_COMMAND_DISABLE))

                if (player != sender) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(sender, LangStrings.FLY_COMMAND_DISABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                }
            }
        }
    }

    /**
     * Cancel fall damage for flying players
     * @param e EntityDamageEvent
     */
    fun onLandingDamage(e: EntityDamageEvent) {
        if (e.entity is Player) {
            val player = e.entity as Player
            val uuid = player.uniqueId

            if (fly[uuid] == true) {
                if (e.cause == EntityDamageEvent.DamageCause.FALL) {
                    e.isCancelled = true
                }
            } else if (airAfter[uuid] == true) {
                if (e.cause == EntityDamageEvent.DamageCause.FALL) {
                    e.isCancelled = true
                    airAfter[uuid] = false
                }
            }
        }
    }

    /**
     * Reanable fly for player on world change
     * @param e PlayerChangedWorldEvent
     */
    fun onWorldChange(e: PlayerChangedWorldEvent) {
        val player = e.player
        val uuid = player.uniqueId

        if (fly[uuid] == true) {
            player.allowFlight = true
            player.isFlying = true
        }
    }

    /**
     * Reanable fly for player on rejoin
     * @param e PlayerJoinEvent
     */
    fun onRejoin(e: PlayerJoinEvent) {
        val player = e.player
        val uuid = player.uniqueId

        if (fly[uuid] == true) {
            player.allowFlight = true
            player.isFlying = true
        }
    }

    /**
     * Reanable fly for player on gamemode switch
     * @param e PlayerGameModeChangeEvent
     */
    fun onGamemodeSwitch(e: PlayerGameModeChangeEvent) {
        val player = e.player
        val uuid = player.uniqueId

        Bukkit.getScheduler().runTaskLater(
            YVtils.instance,
            Runnable {
                if (fly[uuid] == true) {
                    player.allowFlight = true
                    player.isFlying = true
                }
            },
            1
        )
    }
}