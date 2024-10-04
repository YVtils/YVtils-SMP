package yv.tils.smp.manager.commands.register

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.stringArgument
import yv.tils.smp.manager.commands.handle.GlobalMuteHandler

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