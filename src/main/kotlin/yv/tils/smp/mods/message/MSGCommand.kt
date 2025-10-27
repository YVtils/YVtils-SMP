package yv.tils.smp.mods.message

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.entitySelectorArgumentOnePlayer
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
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

                    sendMessage(sender, target, message)
                }
            }
        }
    }

    fun sendMessage(sender: CommandSender, target: Player, message: String) {
        val senderName = if (sender is Player) {
            chatSession[sender.uniqueId] = target.uniqueId
            chatSession[target.uniqueId] = sender.uniqueId
            sender.name
        } else {
            "Console"
        }

        if (sender == target) {
            val noteLayout = Placeholder().replacer(
                Language().getMessage(LangStrings.MSG_NOTE),
                listOf("message"),
                listOf(message)
            )
            sender.sendMessage(noteLayout)
            return
        }

        val targetName = target.name

        val display = Config.config["msg.design"] as String

        val layout = Placeholder().replacer(
            ColorUtils().convert(display),
            listOf("sender", "receiver", "message"),
            listOf(senderName, targetName, message)
        )

        sender.sendMessage(layout)
        target.sendMessage(layout)
    }
}