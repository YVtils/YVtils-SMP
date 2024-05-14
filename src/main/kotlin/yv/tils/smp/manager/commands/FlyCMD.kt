package yv.tils.smp.manager.commands

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerArgument
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

class FlyCMD {
    companion object {
        var fly: MutableMap<UUID, Boolean> = HashMap()
        var airAfter: MutableMap<UUID, Boolean> = HashMap()
    }

    val command = commandTree("fly") {
        withPermission("yvtils.smp.command.fly")
        withUsage("fly [player]")

        playerArgument("player", true) {
            anyExecutor { sender, args ->

                if (sender !is Player && args[0] == null) {
                    sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                    return@anyExecutor
                }

                if (args[0] is Player) {
                    val target = args[0] as Player
                    flySwitch(target, sender)
                } else {
                    flySwitch(sender as Player)
                }
            }
        }
    }

    fun flySwitch(player: Player, sender: CommandSender = player) {
        val uuid = player.uniqueId

        if (fly[uuid] == null || fly[uuid] == false) {
            fly[uuid] = true
            player.allowFlight = true
            player.isFlying = true
            player.sendMessage(Language().getMessage(uuid, LangStrings.FLY_COMMAND_ENABLE))

            if (player != sender) {
                if (sender is Player) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(sender.uniqueId, LangStrings.FLY_COMMAND_ENABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                } else {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(LangStrings.FLY_COMMAND_ENABLE_OTHER),
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

            player.sendMessage(Language().getMessage(uuid, LangStrings.FLY_COMMAND_DISABLE))

            if (player != sender) {
                if (sender is Player) {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(sender.uniqueId, LangStrings.FLY_COMMAND_DISABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                } else {
                    sender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(LangStrings.FLY_COMMAND_DISABLE_OTHER),
                            listOf("player"),
                            listOf(player.name)
                        )
                    )
                }
            }
        }
    }

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

    fun onWorldChange(e: PlayerChangedWorldEvent) {
        val player = e.player
        val uuid = player.uniqueId

        if (fly[uuid] == true) {
            player.allowFlight = true
            player.isFlying = true
        }
    }

    fun onRejoin(e: PlayerJoinEvent) {
        val player = e.player
        val uuid = player.uniqueId

        if (fly[uuid] == true) {
            player.allowFlight = true
            player.isFlying = true
        }
    }

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