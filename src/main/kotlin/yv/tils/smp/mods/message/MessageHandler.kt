package yv.tils.smp.mods.message

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.mods.message.MSGCommand.Companion.chatSession
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import kotlin.collections.set

class MessageHandler {
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

        if (!target.hasPermission("yvtils.smp.msg.receive")) {
            val noPermLayout = Language().getMessage(LangStrings.MSG_NO_RECEIVE_PERMISSION)
            sender.sendMessage(noPermLayout)
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