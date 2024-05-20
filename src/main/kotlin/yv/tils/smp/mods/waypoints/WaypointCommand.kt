package yv.tils.smp.mods.waypoints

import dev.jorel.commandapi.IStringTooltip
import dev.jorel.commandapi.StringTooltip
import dev.jorel.commandapi.SuggestionInfo
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.executors.CommandArguments
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.waypoints.WaypointConfig
import yv.tils.smp.utils.configs.waypoints.WaypointConfig.Companion.waypoints
import java.util.concurrent.CompletableFuture
import kotlin.math.roundToInt

class WaypointCommand {
    val command = commandTree("waypoint") {
        withPermission("yvtils.smp.command.waypoint")
        withUsage("waypoint <create/delete/navigate> <name> [visibility]")
        withAliases("position", "pos", "wp")

        stringArgument("action", false) {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "create",
                    "delete",
                    "navigate"
                )
            )

            stringArgument("name", false) {
                replaceSuggestions(
                    ArgumentSuggestions.stringsWithTooltipsCollectionAsync { generateSuggestions(it) }
                )

                stringArgument("visibility", true) {
                    replaceSuggestions(
                        // TODO: Add unlisted as option
                        ArgumentSuggestions.strings("public", "private")
                    )

                    playerExecutor { sender, args ->
                        handleCommand(sender, args)
                    }
                }
            }
        }
    }

    private fun handleCommand(player: Player, args: CommandArguments) {
        val action = args[0] as String
        val name = args[1] as String
        val visibility = args[2] as String? ?: "private"

        when (action) {
            "create" -> {
                if (!checkWaypoint(player, name)) {
                    createWaypoint(player, name, visibility)
                } else {
                    println("TEMP MESSAGE: Waypoint already exists!")
                }
            }
            "delete" -> {
                if (checkWaypoint(player, name) && WaypointConfig().requestCreator(player.uniqueId.toString(), name)) {
                    deleteWaypoint(player, name)
                } else {
                    println("TEMP MESSAGE: Waypoint not found!")
                }
            }
            "navigate" -> {
                if (checkWaypoint(player, name)) {
                    navigateWaypoints(player, name)
                } else {
                    println("TEMP MESSAGE: Waypoint not found!")
                }
            }
            else -> {

            }
        }
    }

    private fun createWaypoint(player: Player, name: String, visibility: String, location: Location = player.location) {
        WaypointConfig().addWaypoint(player.uniqueId.toString(), name, visibility, location.x, location.y, location.z, location.world.name)

        // TODO: Message
        println("TEMP MESSAGE: Waypoint created!")
    }

    private fun deleteWaypoint(player: Player, name: String) {
        println("TEMP MESSAGE: Waypoint deleted!")


        WaypointConfig().removeWaypoint(player.uniqueId.toString(), name)

        // TODO: Message
    }

    private fun navigateWaypoints(player: Player, name: String) {
        println("TEMP MESSAGE: Navigating to waypoint!")

        val visibility = WaypointConfig().requestVisibility(player.uniqueId.toString(), name)

        if (visibility.lowercase() == "public") {
            val waypoint = waypoints["PUBLIC"]?.find { it.name == name } ?: return
            WaypointPath().generatePath(player, waypoint.x, waypoint.y, waypoint.z, waypoint.world)
        } else if (visibility.lowercase() == "private") {
            val waypoint = waypoints[player.uniqueId.toString()]?.find { it.name == name } ?: return
            WaypointPath().generatePath(player, waypoint.x, waypoint.y, waypoint.z, waypoint.world)
        }

        // TODO: Message
    }

    private fun checkWaypoint(player: Player, name: String): Boolean {
        println("TEMP MESSAGE: Checking waypoint")

        val playerWaypoints = waypoints[player.uniqueId.toString()]

        if (playerWaypoints != null) {
            for (waypoint in playerWaypoints) {
                if (waypoint.name == name) {
                    return true
                }
            }
        }

        val publicWaypoints = waypoints["PUBLIC"]

        if (publicWaypoints != null) {
            for (waypoint in publicWaypoints) {
                if (waypoint.name == name) {
                    return true
                }
            }
        }

        return false
    }

    private fun generateSuggestions(suggestionInfo: SuggestionInfo<CommandSender>): CompletableFuture<Collection<IStringTooltip>> {
        return CompletableFuture.supplyAsync {
            val player = suggestionInfo.sender
            val waypointSuggestions: MutableList<IStringTooltip> = mutableListOf()

            if (player !is Player) return@supplyAsync waypointSuggestions

            val waypointMap = waypoints
            val playerWaypoints = waypointMap[player.uniqueId.toString()]
            playerWaypoints?.let {
                for (waypoint in it) {
                    val position = "${waypoint.x.roundToInt()} ${waypoint.y.roundToInt()} ${waypoint.z.roundToInt()} | ${waypoint.world}"
                    waypointSuggestions.add(StringTooltip.ofString(waypoint.name, position))
                }
            }

            val publicWaypoints = waypointMap["PUBLIC"]
            publicWaypoints?.let {
                for (waypoint in it) {
                    val position = "${waypoint.x.roundToInt()} ${waypoint.y.roundToInt()} ${waypoint.z.roundToInt()} | ${waypoint.world}"
                    waypointSuggestions.add(StringTooltip.ofString(waypoint.name, position))
                }
            }

            waypointSuggestions
        }
    }
}