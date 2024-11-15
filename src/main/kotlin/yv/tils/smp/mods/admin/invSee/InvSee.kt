package yv.tils.smp.mods.admin.invSee

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.inventory.GUIFiller
import java.util.*

class InvSee {
    companion object {
        var invSee: MutableMap<UUID, UUID> = HashMap()
    }

    val command = commandTree("invsee") {
        withPermission("yvtils.smp.command.invsee")
        withUsage("invsee <player>")
        withAliases("inv")

        playerArgument("player") {
            playerExecutor { player, args ->
                val target = args[0] as Player
                invSee[player.uniqueId] = target.uniqueId
                player.openInventory(getInv(target))
            }
        }
    }

    /**
     * Get inventory of player
     * @param target Player to get inventory
     */
    private fun getInv(target: Player): Inventory {
        var inv = Bukkit.createInventory(
            null, 54,
            Placeholder().replacer(
                Language().getMessage(
                    LangStrings.MODULE_INVSEE_INVENTORY
                ),
                listOf("player"),
                listOf(target.name)
            )
        )

        val armour = target.inventory.armorContents
        val invContent = target.inventory.contents
        val offhand = target.inventory.itemInOffHand

        var j = 3
        for (i in 1..4) {
            inv.setItem(i, armour[j])
            j--
        }

        inv.setItem(7, offhand)

        j = 0
        for (i in 18..53) {
            if (i < 45) {
                inv.setItem(i, invContent[i - 9])
            } else {
                inv.setItem(i, invContent[j])
                j++
            }
        }

        // Filler
        val fillerSlots = mutableListOf<Int>()
        fillerSlots.addAll(listOf(0, 5, 6))
        fillerSlots.addAll(8..17)

        inv = GUIFiller().fillInventory(inv, onlySlots = fillerSlots)

        return inv
    }
}