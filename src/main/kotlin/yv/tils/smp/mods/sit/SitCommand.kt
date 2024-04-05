package yv.tils.smp.mods.sit

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor

class SitCommand {
    val command = commandTree("sit") {
        withPermission("yvtils.smp.command.sit")
        withUsage("sit")
        withAliases("chair")

        playerExecutor {
            player, _ ->
            if (SitManager().isSitting(player.uniqueId)) {
                SitManager().sitGetter(player)
            } else {
                SitManager().sit(player)
            }
        }
    }
}