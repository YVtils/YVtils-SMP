package yv.tils.smp.mods.admin.invSee

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.entitySelectorArgumentOnePlayer
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.entity.Player

class InvSee {
    val command = commandTree("invsee") {
        withPermission("yvtils.smp.command.invsee")
        withUsage("invsee <player>")
        withAliases("inv")

        entitySelectorArgumentOnePlayer("player") {
            playerExecutor { player, args ->
                val target = args[0] as Player
                val inv = InvSeeListener().getInv(target)

                InvSeeListener.invSee[player.uniqueId] = InvSeeListener.InvSeePlayer(target.uniqueId, inv)

                player.openInventory(inv)
            }
        }
    }
}