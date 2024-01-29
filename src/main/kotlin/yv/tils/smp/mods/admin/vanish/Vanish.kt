package yv.tils.smp.mods.admin.vanish

import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.mods.server.connect.PlayerJoin
import yv.tils.smp.mods.server.connect.PlayerQuit
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.MutableMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator
import kotlin.collections.listOf
import kotlin.collections.set

class Vanish {
    companion object {
        var exec_target: MutableMap<UUID, UUID> = HashMap()
        var vanish: MutableMap<UUID, Boolean> = HashMap() //Default: false
        var oldVanish: MutableMap<UUID, Boolean> = HashMap() //Default: false
        var layer: MutableMap<UUID, Int> = HashMap() //Default: 1
        var itemPickup: MutableMap<UUID, Boolean> = HashMap() //Default: false
        var invInteraction: MutableMap<UUID, Boolean> = HashMap() //Default: true
        var mobTarget: MutableMap<UUID, Boolean> = HashMap() //Default: true
    }

    val command = commandTree("vanish") {
        withPermission("yvtils.smp.command.vanish")
        withUsage("vanish [quick | player] [player]")
        withAliases("v")

        literalArgument("quick", true) {
            playerArgument("player", true) {
                anyExecutor { sender, args ->
                    try {
                        val target = args[0] as Player
                        quickVanish(target, sender)
                        println("|-/-/-/-/-|")
                        println("Vanish - Player & Quick")
                        println("Vanish Command - Player Executor - Player: ${sender.name}")
                        println("|-/-/-/-/-|")
                    }catch (_: Exception) {
                        quickVanish(sender as Player, sender)
                        println("|-/-/-/-/-|")
                        println("Vanish - Non Player & Quick")
                        println("Vanish Command - Player Executor - Player: ${sender.name}")
                        println("|-/-/-/-/-|")
                    }
                }
            }
        }

        playerArgument("player", true) {
            playerExecutor { player, args ->
                try {
                    val target = args[0] as Player
                    vanish(player, target)
                    println("|-/-/-|")
                    println("Vanish - Player & Non Quick")
                    println("Vanish Command - Player Executor - Player: ${player.name}")
                    println("|-/-/-|")
                }catch (_: Exception) {
                    vanish(player, player)
                    println("|-/-|")
                    println("Vanish - Non Player & Non Quick")
                    println("Vanish Command - Player Executor - Player: ${player.name}")
                    println("|-/-|")
                }
            }
        }
    }

    private fun quickVanish(player: Player, sender: CommandSender) {
        if (!player.isOnline) {
            if (sender is Player) {
                sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.PLAYER_NOT_ONLINE))
            } else {
                sender.sendMessage(Language().getMessage(LangStrings.PLAYER_NOT_ONLINE))
            }
            return
        }

        if (vanish.containsKey(player.uniqueId)) vanish[player.uniqueId] = false
        if (layer.containsKey(player.uniqueId)) layer[player.uniqueId] = 1
        if (itemPickup.containsKey(player.uniqueId)) itemPickup[player.uniqueId] = false
        if (invInteraction.containsKey(player.uniqueId)) invInteraction[player.uniqueId] = true
        if (mobTarget.containsKey(player.uniqueId)) mobTarget[player.uniqueId] = true

        if (vanish[player.uniqueId]!!) {
            VanishGUI().vanishRegister(player, false)
            disableVanish(player)
        } else {
          VanishGUI().vanishRegister(player, true)
          enableVanish(player)
        }

        return
    }

    private fun vanish(player: Player, sender: Player) {
        if (!player.isOnline) {
            sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.PLAYER_NOT_ONLINE))
            return
        }

        if (vanish.containsKey(player.uniqueId)) vanish[player.uniqueId] = false
        if (layer.containsKey(player.uniqueId)) layer[player.uniqueId] = 1
        if (itemPickup.containsKey(player.uniqueId)) itemPickup[player.uniqueId] = false
        if (invInteraction.containsKey(player.uniqueId)) invInteraction[player.uniqueId] = true
        if (mobTarget.containsKey(player.uniqueId)) mobTarget[player.uniqueId] = true

        exec_target[sender.uniqueId] = player.uniqueId

        VanishGUI().gui(sender)

        return
    }

    fun enableVanish(player: Player) {
        val quitMessage = PlayerQuit().generateQuitMessage(player)

        playerHide(player)

        player.canPickupItems = itemPickup[player.uniqueId]!!

        player.isSleepingIgnored = true
        player.isSilent = true

        if ((oldVanish.containsKey(player.uniqueId) && vanish.containsKey(player.uniqueId)) && oldVanish[player.uniqueId] == vanish[player.uniqueId]) {
            player.sendMessage(
                StringReplacer().listReplacer(
                    Language().getMessage(
                        player.uniqueId,
                        LangStrings.VANISH_REFRESH
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )

            for ((key, value) in exec_target) {
                if (value === key) return
                if (value == player.uniqueId) {
                    val target = Bukkit.getPlayer(key)
                    target?.sendMessage(
                        StringReplacer().listReplacer(
                            Language().getMessage(
                                target.uniqueId,
                                LangStrings.VANISH_REFRESH_OTHER
                            ),
                            listOf("PREFIX", "PLAYER"),
                            listOf(Vars().prefix, player.name)
                        )
                    )
                }
            }

            return
        }

        Bukkit.broadcast(quitMessage)

        player.sendMessage(
            StringReplacer().listReplacer(
                Language().getMessage(
                    player.uniqueId,
                    LangStrings.VANISH_ACTIVATE
                ),
                listOf("prefix"),
                listOf(Vars().prefix)
            )
        )
    }

    fun disableVanish(player: Player) {
        val joinMessage = PlayerJoin().generateJoinMessage(player)

        for (p in player.server.onlinePlayers) {
            p.showPlayer(YVtils.instance, player)
            player.showPlayer(YVtils.instance, p)
        }

        player.isSleepingIgnored = false
        player.canPickupItems = true
        player.isSilent = false

        if ((oldVanish.containsKey(player.uniqueId) && vanish.containsKey(player.uniqueId)) && oldVanish[player.uniqueId] == vanish[player.uniqueId]) {
            player.sendMessage(
                StringReplacer().listReplacer(
                    Language().getMessage(
                        player.uniqueId,
                        LangStrings.VANISH_REFRESH
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )

            for ((key, value) in exec_target) {
                if (value === key) return
                if (value == player.uniqueId) {
                    val target = Bukkit.getPlayer(key)
                    target?.sendMessage(
                        StringReplacer().listReplacer(
                            Language().getMessage(
                                target.uniqueId,
                                LangStrings.VANISH_REFRESH_OTHER
                            ),
                            listOf("PREFIX", "PLAYER"),
                            listOf(Vars().prefix, player.name)
                        )
                    )
                }
            }

            return
        }

        Bukkit.broadcast(joinMessage)
        player.sendMessage(
            StringReplacer().listReplacer(
                Language().getMessage(
                    player.uniqueId,
                    LangStrings.VANISH_DEACTIVATE
                ),
                listOf("prefix"),
                listOf(Vars().prefix)
            )
        )
    }

    fun playerHide(player: Player) {
        for (p in player.server.onlinePlayers) {
            p.hidePlayer(YVtils.instance, player)
            player.hidePlayer(YVtils.instance, p)

            if (!vanish.containsKey(p.uniqueId)) player.showPlayer(YVtils.instance, p)
        }

        if (layer[player.uniqueId] != 4) {
            for (entry in layer.entries) {
                if (entry.value <= layer[player.uniqueId]!!) {
                    for (p in player.server.onlinePlayers) {
                        if ((p.uniqueId == entry.key && entry.value != 4) && (vanish.containsKey(p.uniqueId) && vanish[p.uniqueId]!!)) {
                            p.showPlayer(YVtils.instance, player)
                            player.showPlayer(YVtils.instance, p)
                        }
                    }
                }
            }
        }
    }
}