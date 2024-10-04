package yv.tils.smp.manager.commands.register

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.stringArgument
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import yv.tils.smp.YVtils
import yv.tils.smp.manager.commands.handle.GlobalMuteHandler
import yv.tils.smp.mods.discord.sync.chatSync.SyncChats
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class GlobalMuteCMD {
    val command = commandTree("globalmute") {
        withPermission("yvtils.smp.command.globalmute")
        withUsage("globalmute [state]")
        withAliases("gmute")

        val gmuteHandler = GlobalMuteHandler()

        stringArgument("state", true) {
            replaceSuggestions(ArgumentSuggestions.strings("true", "false", "toggle"))
            anyExecutor { sender, args ->
                gmuteHandler.globalMute(sender, args)
            }
        }

        anyExecutor { sender, _ ->
            gmuteHandler.globalMute(sender)
        }
    }
}