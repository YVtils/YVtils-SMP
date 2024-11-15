package yv.tils.smp.mods.admin.vanish

import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.entity.Player

class VanishCMD {
    val vanish = Vanish()

    val command = commandTree("vanish") {
        withPermission("yvtils.smp.command.vanish")
        withUsage("vanish [quick | player] [player]")
        withAliases("v")

        literalArgument("quick", true) {
            withAliases("q")
            playerArgument("player", true) {
                anyExecutor { sender, args ->
                    try {
                        val target = args[0] as Player
                        vanish.quickVanish(target, sender)
                    } catch (_: Exception) {
                        vanish.quickVanish(sender as Player, sender)
                    }
                }
            }
        }

        playerArgument("player", true) {
            playerExecutor { player, args ->
                try {
                    val target = args[0] as Player
                    vanish.vanish(target, player)
                } catch (_: Exception) {
                    vanish.vanish(player, player)
                }
            }
        }
    }
}