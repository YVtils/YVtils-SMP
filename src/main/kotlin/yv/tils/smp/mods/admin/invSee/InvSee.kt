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
    val command = commandTree("invsee") {
        withPermission("yvtils.smp.command.invsee")
        withUsage("invsee <player>")
        withAliases("inv")

        playerArgument("player") {
            playerExecutor { player, args ->
                val target = args[0] as Player
                val inv = InvSeeListener().getInv(target)

                InvSeeListener.invSee[player.uniqueId] = InvSeeListener.InvSeePlayer(target.uniqueId, inv)

                player.openInventory(inv)
            }
        }
    }
}