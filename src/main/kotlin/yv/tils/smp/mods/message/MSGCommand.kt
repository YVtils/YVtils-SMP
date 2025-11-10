package yv.tils.smp.mods.message

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.entitySelectorArgumentOnePlayer
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import org.bukkit.entity.Player
import java.util.*

class MSGCommand {
    companion object {
        val chatSession = mutableMapOf<UUID, UUID>()
    }

    val command = commandTree("msg") {
        withPermission("yvtils.smp.command.msg")
        withUsage("msg <player> <message>")
        withAliases("message", "tell", "whisper", "dm", "w")

        entitySelectorArgumentOnePlayer("player") {
            greedyStringArgument("message") {
                anyExecutor { sender, args ->
                    val target = args["player"] as Player
                    val message = args["message"] as String

                    MessageHandler().sendMessage(sender, target, message)
                }
            }
        }
    }
}