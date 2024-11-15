package yv.tils.smp.mods.admin.invSee

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.util.*

class EcSee {
    companion object {
        var ecSee: MutableMap<UUID, UUID> = HashMap()
    }

    val command = commandTree("ecsee") {
        withPermission("yvtils.smp.command.ecsee")
        withUsage("ecsee <player>")
        withAliases("ec")

        playerArgument("player") {
            playerExecutor { player, args ->
                val target = args[0] as Player
                ecSee[player.uniqueId] = target.uniqueId
                player.openInventory(getInv(target))
            }
        }
    }

    /**
     * Get enderchest inventory of player
     * @param target Player to get enderchest
     */
    private fun getInv(target: Player): Inventory {
        val inv = Bukkit.createInventory(
            null, 27,
            Placeholder().replacer(
                Language().getMessage(
                    LangStrings.MODULE_INVSEE_ENDERCHEST
                ),
                listOf("player"),
                listOf(target.name)
            )
        )

        inv.contents = target.enderChest.contents
        return inv
    }
}