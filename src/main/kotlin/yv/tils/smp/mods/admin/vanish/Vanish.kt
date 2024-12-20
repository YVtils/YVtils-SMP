package yv.tils.smp.mods.admin.vanish

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.vanish.gui.VBuilder
import yv.tils.smp.mods.server.connect.PlayerJoin
import yv.tils.smp.mods.server.connect.PlayerQuit
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.util.*

class Vanish {
    companion object {
        var exec_target: MutableMap<UUID, UUID> = HashMap()
        var vanish: MutableMap<UUID, VanishedPlayer> = HashMap()
    }

    /**
     * Data class for vanished player
     * @param player Player to vanish
     * @param vanish Boolean of vanish state
     * @param layer Int of vanish layer
     * @param itemPickup Boolean of item pickup state | true = pickup enabled
     * @param invInteraction Boolean of inventory interaction state | true = silent inventory interaction
     * @param mobTarget Boolean of mob target state | true = mobs can not target player
     */
    data class VanishedPlayer(
        var player: Player,
        var oldVanish: Boolean,
        var vanish: Boolean,
        var layer: Int,
        var itemPickup: Boolean,
        var invInteraction: Boolean,
        var mobTarget: Boolean
    )

    /**
     * Toggle vanish for player
     * @param player Player to toggle vanish
     * @param sender CommandSender to send messages
     */
    fun vanish(player: Player, sender: CommandSender) {
        if (sender !is Player) {
            quickVanish(player, sender)
            return
        }

        exec_target[sender.uniqueId] = player.uniqueId

        val currentState = currentState(player)

        vanish[player.uniqueId]!!.oldVanish = currentState

        VBuilder().openGUI(sender, player)
    }

    /**
     * Quick toggle vanish for player
     * @param player Player to toggle vanish
     * @param sender CommandSender to send messages
     */
    fun quickVanish(player: Player, sender: CommandSender) {
        val state = currentState(player)

        vanish[player.uniqueId]!!.oldVanish = state

        if (state) {
            vanish[player.uniqueId]!!.vanish = false
            disableVanish(player)
        } else {
            vanish[player.uniqueId]!!.vanish = true
            enableVanish(player)
        }
    }

    /**
     * Refresh vanish for player
     * @param player Player to refresh vanish
     * @return Boolean of vanish state
     */
    fun refreshVanish(player: Player): Boolean {
        val state = currentState(player)
        if (state) {
            loopHidePlayers(player)
        }

        return state
    }

    /**
     * Enable vanish for player
     * @param player Player to enable vanish
     * @param silent Boolean if fake leave message should not be sent
     * @return Boolean if vanish was enabled
     */
    fun enableVanish(player: Player, silent: Boolean = false): Boolean {
        loopHidePlayers(player)

        val vData = vanish[player.uniqueId]!!

        player.canPickupItems = vData.itemPickup

        player.isSleepingIgnored = true
        player.isSilent = true

        if (vData.vanish == vData.oldVanish) {
            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        player.uniqueId,
                        LangStrings.VANISH_REFRESH
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )

            for ((key, value) in exec_target) {
                if (value === key) return true
                if (value == player.uniqueId) {
                    val target = Bukkit.getPlayer(key)
                    target?.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(
                                target.uniqueId,
                                LangStrings.VANISH_REFRESH_OTHER
                            ),
                            listOf("prefix", "player"),
                            listOf(Vars().prefix, player.name)
                        )
                    )
                }
            }
            return true
        }

        if (!silent) {
            val quitMessage = PlayerQuit().generateQuitMessage(player)
            Bukkit.broadcast(quitMessage)
        }

        player.sendMessage(
            Placeholder().replacer(
                Language().getMessage(
                    player.uniqueId,
                    LangStrings.VANISH_ACTIVATE
                ),
                listOf("prefix"),
                listOf(Vars().prefix)
            )
        )

        return false
    }

    /**
     * Disable vanish for player
     * @param player Player to disable vanish
     * @param silent Boolean if fake join message should not be sent
     * @return Boolean if vanish was disabled
     */
    fun disableVanish(player: Player, silent: Boolean = false): Boolean {
        showPlayer(player)

        val vData = vanish[player.uniqueId]!!

        player.canPickupItems = true

        player.isSleepingIgnored = false
        player.isSilent = false

        if (vData.vanish == vData.oldVanish) {
            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(
                        player.uniqueId,
                        LangStrings.VANISH_REFRESH
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )

            for ((key, value) in exec_target) {
                if (value === key) return true
                if (value == player.uniqueId) {
                    val target = Bukkit.getPlayer(key)
                    target?.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(
                                target.uniqueId,
                                LangStrings.VANISH_REFRESH_OTHER
                            ),
                            listOf("prefix", "player"),
                            listOf(Vars().prefix, player.name)
                        )
                    )
                }
            }
            return true
        }

        if (!silent) {
            val joinMessage = PlayerJoin().generateJoinMessage(player)
            Bukkit.broadcast(joinMessage)
        }

        player.sendMessage(
            Placeholder().replacer(
                Language().getMessage(
                    player.uniqueId,
                    LangStrings.VANISH_DEACTIVATE
                ),
                listOf("prefix"),
                listOf(Vars().prefix)
            )
        )

        return false
    }

    /**
     * Loop through all online players and hide vanished player
     * @param player Player to hide
     */
    private fun loopHidePlayers(player: Player) {
        for (p in player.server.onlinePlayers) {
            hidePlayer(player, p)
        }
    }

    /**
     * Hide player for target player
     * @param exec Player to hide
     * @param target Player to hide from
     */
    fun hidePlayer(exec: Player, target: Player) {
        target.hidePlayer(YVtils.instance, exec)
        exec.hidePlayer(YVtils.instance, target)

        if (!vanish.containsKey(target.uniqueId) || !vanish[target.uniqueId]!!.vanish) {
            exec.showPlayer(YVtils.instance, target)
            return
        }

        val layer = vanish[exec.uniqueId]!!.layer
        val targetLayer = vanish[target.uniqueId]!!.layer
        if (layer == 4 || targetLayer == 4) return

        if (targetLayer > layer) {
            exec.hidePlayer(YVtils.instance, target)
            target.showPlayer(YVtils.instance, exec)
        } else if (targetLayer < layer) {
            exec.showPlayer(YVtils.instance, target)
            target.hidePlayer(YVtils.instance, exec)
        } else {
            exec.showPlayer(YVtils.instance, target)
            target.showPlayer(YVtils.instance, exec)
        }
    }

    /**
     * Show player for target player
     * @param player Player to show
     */
    private fun showPlayer(player: Player) {
        for (p in player.server.onlinePlayers) {
            p.showPlayer(YVtils.instance, player)
        }
    }

    /**
     * Get current vanish state for player
     * @param player Player to get vanish state
     * @return Boolean of vanish state
     */
    private fun currentState(player: Player): Boolean {
        if (!vanish.containsKey(player.uniqueId)) {
            generateDummyData(player)
        }

        return vanish[player.uniqueId]!!.vanish
    }

    /**
     * Generate dummy data for player
     * @param player Player to generate dummy data
     */
    private fun generateDummyData(player: Player) {
        vanish[player.uniqueId] = VanishedPlayer(player,
            oldVanish = false,
            vanish = false,
            layer = 1,
            itemPickup = false,
            invInteraction = true,
            mobTarget = true
        )
    }
}