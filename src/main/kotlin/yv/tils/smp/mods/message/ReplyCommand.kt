package yv.tils.smp.mods.message

import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import yv.tils.smp.mods.message.MSGCommand.Companion.chatSession
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class ReplyCommand {
    val command = commandTree("reply") {
        withPermission("yvtils.smp.command.reply")
        withUsage("reply <message>")
        withAliases("r")

        greedyStringArgument("message") {
            playerExecutor { sender, args ->
                reply(sender, args)
            }
        }
    }

    private fun reply(sender: Player, args: CommandArguments) {
        val target = chatSession[sender.uniqueId] ?: run {
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.MSG_HAVENT_MESSAGED_A_PLAYER),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                )
            )
            return
        }

        val message = args[0] as String
        MSGCommand().sendMessage(sender, Bukkit.getPlayer(target)!!, message)
    }
}