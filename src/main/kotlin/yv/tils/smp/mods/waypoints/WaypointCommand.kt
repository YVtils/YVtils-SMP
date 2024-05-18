package yv.tils.smp.mods.waypoints

import dev.jorel.commandapi.IStringTooltip
import dev.jorel.commandapi.StringTooltip
import dev.jorel.commandapi.SuggestionInfo
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.textArgument
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.waypoints.WaypointConfig
import java.util.function.Function

class WaypointCommand {
    val command = commandTree("waypoint") {
        withPermission("yvtils.smp.command.waypoint")
        withUsage("waypoint <create/delete/navigate> <name> [visibility]")
        withAliases("position", "pos", "wp")

        literalArgument("action", false) {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "create",
                    "delete",
                    "navigate"
                )
            )

            textArgument("name", false) {
                replaceSuggestions(
                    ArgumentSuggestions.stringsWithTooltipsCollection() { generateSuggestions(it) }
                )

                literalArgument("visibility", true) {
                    replaceSuggestions(
                        ArgumentSuggestions.strings("public", "private")
                    )
                }
            }
        }
    }

    private fun generateSuggestions(suggestionInfo: SuggestionInfo<CommandSender>): MutableList<IStringTooltip> {
        val player = suggestionInfo.sender
        val waypoints: MutableList<IStringTooltip> = mutableListOf()

        if (player !is Player) return waypoints

        val waypointMap = WaypointConfig.waypoints
        val playerWaypoints = waypointMap[player.uniqueId.toString()] ?: return waypoints

        for (waypoint in playerWaypoints) {
            val position = "${waypoint.x} ${waypoint.y} ${waypoint.z} | ${waypoint.world}"
            waypoints.add(StringTooltip.ofString(waypoint.name, position))
        }

        return waypoints
    }
}