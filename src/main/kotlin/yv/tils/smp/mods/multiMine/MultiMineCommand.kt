package yv.tils.smp.mods.multiMine

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*

class MultiMineCommand {
    val blockManage = BlockManage()

    val command = commandTree("multiMine") {
        withPermission("yvtils.smp.command.multiMine")
        withUsage("multiMine <add/remove/addMultiple/removeMultiple> [block]")
        withAliases("mm")

        stringArgument("action") {
            withPermission("yvtils.smp.command.multiMine.manage")
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "add",
                    "remove",
                    "addMultiple",
                    "removeMultiple"
                )
            )

            itemStackArgument("block", true) {
                anyExecutor { sender, args ->
                    when (args[0]) {
                        "add" -> {
                            blockManage.addBlock(sender, args[1])
                        }

                        "remove" -> {
                            blockManage.removeBlock(sender, args[1])
                        }

                        "addMultiple" -> {
                            blockManage.addMultiple(sender)
                        }

                        "removeMultiple" -> {
                            blockManage.removeMultiple(sender)
                        }
                    }
                }
            }
        }

        literalArgument("toggle", true) {
            withPermission("yvtils.smp.command.multiMine.toggle")
            playerExecutor { sender, _ ->
                MultiMineHandler().toggle(sender)
            }
        }
    }
}