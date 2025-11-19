package yv.tils.smp.mods.fusion

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.stringArgument
import yv.tils.smp.mods.fusionCrafting.FusionOverview

class FusionCommand {
    val command = commandTree("fusion") {
        withPermission("yvtils.smp.command.fusion")
        withUsage("/fusion")
        withAliases("ccr", "fc")

        stringArgument("manage", true) {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "manage"
                )
            )
            withPermission("yvtils.smp.fusion.manage")

            playerExecutor { sender, args ->
                if (args[0] != "manage") {
                    FusionOverview().openOverview(sender, "<gold>Fusion Crafting")
                } else {
                    FusionOverview().openOverview(sender, "<red>Fusion Management")
                }
            }
        }
    }
}