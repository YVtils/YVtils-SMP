package yv.tils.smp.manager.commands.register

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.entitySelectorArgumentOnePlayer
import org.bukkit.entity.Player
import yv.tils.smp.manager.commands.handle.FlyHandler
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class FlyCMD {
    val command = commandTree("fly") {
        withPermission("yvtils.smp.command.fly")
        withUsage("fly [player]")

        entitySelectorArgumentOnePlayer("player", true) {
            anyExecutor { sender, args ->

                if (sender !is Player && args[0] == null) {
                    sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                    return@anyExecutor
                }

                val flyHandler = FlyHandler()

                if (args[0] is Player) {
                    val target = args[0] as Player
                    flyHandler.flySwitch(target, sender)
                } else {
                    flyHandler.flySwitch(sender as Player)
                }
            }
        }
    }
}