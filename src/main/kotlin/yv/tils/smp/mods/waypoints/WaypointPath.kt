package yv.tils.smp.mods.waypoints

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import kotlin.math.atan2

class WaypointPath {
    companion object {
        val crystalList: MutableList<EnderCrystal> = mutableListOf()
        val navigatingPlayers: MutableMap<Player, NaviData> = mutableMapOf()
    }

    data class NaviData(
        val player: Player,
        val location: Location,
        val endCrystal: EnderCrystal,
        val task: BukkitTask
    )

    fun generatePath(player: Player, x: Double, y: Double, z: Double, worldName: String, waypointName: String) {
        val world = Bukkit.getWorld(worldName)
        val location = Location(world, x, y, z)

        if (navigatingPlayers.containsKey(player)) {
            if (navigatingPlayers[player]?.location == location) {
                navigatingPlayers[player]?.task?.cancel()
                navigatingPlayers[player]?.endCrystal?.remove()
                navigatingPlayers.remove(player)

                player.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(player.uniqueId, LangStrings.MODULE_WAYPOINT_NAVIGATE_STOPPED),
                        mapOf(
                            "waypoint" to waypointName
                        )
                    )
                )

                return
            } else {
                navigatingPlayers[player]?.task?.cancel()
                navigatingPlayers[player]?.endCrystal?.remove()
                navigatingPlayers.remove(player)
            }
        }

        navigate(player, location, waypointName)

        player.sendMessage(
            Placeholder().replacer(
                Language().getMessage(player.uniqueId, LangStrings.MODULE_WAYPOINT_NAVIGATE_STARTED),
                mapOf(
                    "waypoint" to waypointName
                )
            )
        )
    }

    private fun navigate(player: Player, location: Location, waypointName: String) {
        val endCrystal = spawnEndCrystal(player, location)

        val task = object : BukkitRunnable() {
            override fun run() {
                spawnParticle(player, player.location, location)
                generateActionbar(player, player.location, location)

                if ((location.world == player.location.world) && location.distance(player.location) < 10) {
                    removeCrystal(endCrystal)

                    player.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(player.uniqueId, LangStrings.MODULE_WAYPOINT_NAVIGATE_FINISHED),
                            mapOf(
                                "waypoint" to waypointName
                            )
                        )
                    )

                    navigatingPlayers.remove(player)

                    cancel()
                    return
                }
            }
        }.runTaskTimer(YVtils.instance, 0, 20)

        navigatingPlayers[player] = NaviData(player, location, endCrystal, task)
    }

    private fun generateActionbar(player: Player, currentLoc: Location, targetLoc: Location) {
        if (currentLoc.world != targetLoc.world) {
            val dimensionLoc: Location?

            if (currentLoc.world == Bukkit.getWorld("world")) {
                if (targetLoc.world == Bukkit.getWorld("world_nether")) {
                    dimensionLoc = targetLoc.clone().set(targetLoc.x * 8, targetLoc.y, targetLoc.z * 8)
                    dimensionLoc.world = Bukkit.getWorld("world")
                } else {
                    player.sendActionBar(ColorUtils().convert(""))
                    return
                }
            } else if (currentLoc.world == Bukkit.getWorld("world_nether")) {
                if (targetLoc.world == Bukkit.getWorld("world")) {
                    dimensionLoc = targetLoc.clone().set(targetLoc.x / 8, targetLoc.y, targetLoc.z / 8)
                    dimensionLoc.world = Bukkit.getWorld("world_nether")
                } else {
                    player.sendActionBar(ColorUtils().convert(""))
                    return
                }
            } else if (currentLoc.world == Bukkit.getWorld("world_the_end")) {
                dimensionLoc = targetLoc.clone().set(0.0, 0.0, 0.0)
                dimensionLoc.world = Bukkit.getWorld("world_the_end")
            } else {
                player.sendActionBar(ColorUtils().convert(""))
                return
            }

            val distance = currentLoc.distance(dimensionLoc)
            val direction = direction(currentLoc, dimensionLoc)

            player.sendActionBar(ColorUtils().convert("<green>$direction <white>| <yellow>${distance.toInt()}<white>m <white>| <green>$direction<white>"))
            return
        }

        val distance = currentLoc.distance(targetLoc)
        val direction = direction(currentLoc, targetLoc)

        player.sendActionBar(ColorUtils().convert("<green>$direction <white>| <yellow>${distance.toInt()}<white>m <white>| <green>$direction<white>"))
    }

    private fun direction(currentLoc: Location, targetLoc: Location): String {
        val x = targetLoc.x - currentLoc.x
        val z = targetLoc.z - currentLoc.z

        var angle = Math.toDegrees(atan2(z, x)) - currentLoc.yaw

        angle = ((angle + 180) % 360 + 360) % 360 - 180

        return when (angle) {
            in -22.5 .. 22.5 -> "←"
            in 22.5 .. 67.5 -> "↖"
            in 67.5 .. 112.5 -> "↑"
            in 112.5 .. 157.5 -> "↗"
            in 157.5 .. 180.0, in -180.0 .. -157.5 -> "→"
            in -157.5 .. -112.5 -> "↘"
            in -112.5 .. -67.5 -> "↓"
            in -67.5 .. -22.5 -> "↙"
            else -> "↑"
        }
    }


    private fun spawnEndCrystal(player: Player, location: Location): EnderCrystal {
        val world = location.world
        val spawnLocation = location.clone().set(location.x, 400.0, location.z)

        while (spawnLocation.block.isPassable) {
            while (spawnLocation.block.isPassable) {
                spawnLocation.subtract(0.0, 1.0, 0.0)
            }

            spawnLocation.subtract(0.0, 5.0, 0.0)
        }

        val beamTarget = location.clone().add(0.0, 500.0, 0.0)

        val endCrystal = world.spawnEntity(spawnLocation, EntityType.END_CRYSTAL) as EnderCrystal
        endCrystal.isShowingBottom = false
        endCrystal.isInvulnerable = true
        endCrystal.beamTarget = beamTarget
        endCrystal.isInvisible = true
        endCrystal.isGlowing = true
        endCrystal.isSilent = true

        for (playerLoop in Bukkit.getOnlinePlayers()) {
            playerLoop.hideEntity(YVtils.instance, endCrystal)
        }

        player.showEntity(YVtils.instance, endCrystal)


        var team = player.scoreboard.getTeam("waypoint")

        if (team == null) {
            team = player.scoreboard.registerNewTeam("waypoint")
            team.color(NamedTextColor.GREEN)
        }

        team.addEntry(endCrystal.uniqueId.toString())

        crystalList.add(endCrystal)

        return endCrystal
    }

    // TODO: Fix error appearing when player is too far away and chunks are not loaded
    private fun spawnParticle(player: Player, currentLoc: Location, targetLoc: Location) {
        if (currentLoc.world != targetLoc.world) return

        val start = currentLoc.clone()
        val end = targetLoc.clone()
        val distance = start.distance(end)
        val particles = 3 * distance.toInt()

        val direction = end.toVector().subtract(start.toVector()).normalize()
        val step = direction.multiply(distance / particles)

        val currentParticleLoc = start.clone()

        for (i in 0 until particles) {
            currentParticleLoc.add(step)

            if (!currentParticleLoc.block.isPassable) {
                val initialY = currentParticleLoc.y
                while (!currentParticleLoc.block.isPassable) {
                    currentParticleLoc.y += 0.1
                    spawnSideParticles(player, currentParticleLoc, initialY, true)
                }
            } else {
                val initialY = currentParticleLoc.y
                while (currentParticleLoc.block.isPassable) {
                    currentParticleLoc.y -= 0.1
                }
                currentParticleLoc.y += 0.3
                spawnSideParticles(player, currentParticleLoc, initialY, false)
            }

            val blockBelow = currentParticleLoc.clone().add(0.0, -0.1, 0.0).block
            if (!blockBelow.isPassable) {
                currentParticleLoc.y = blockBelow.y + 1.0
            }

            player.spawnParticle(Particle.END_ROD, currentParticleLoc, 1, 0.0, 0.0, 0.0, 0.0)
        }
    }


    private fun spawnSideParticles(player: Player, currentParticleLoc: Location, initialY: Double, movingUp: Boolean) {
        val stepY = 0.1
        var y = initialY
        if (movingUp) {
            while (y < currentParticleLoc.y) {
                y += stepY
                spawnParticlesAtY(player, currentParticleLoc, y)
            }
        } else {
            while (y > currentParticleLoc.y) {
                y -= stepY
                spawnParticlesAtY(player, currentParticleLoc, y)
            }
        }
    }

    private fun spawnParticlesAtY(player: Player, currentParticleLoc: Location, y: Double) {
        val particleLoc = currentParticleLoc.clone()
        particleLoc.y = y

        val offset = 0.1
        val directions = arrayOf(
            Vector(offset, 0.0, 0.0),
            Vector(-offset, 0.0, 0.0),
            Vector(0.0, 0.0, offset),
            Vector(0.0, 0.0, -offset)
        )

        for (sideDirection in directions) {
            val sideParticleLoc = particleLoc.clone().add(sideDirection)
            if (sideParticleLoc.block.isPassable) {
                player.spawnParticle(Particle.END_ROD, sideParticleLoc, 1, 0.0, 0.0, 0.0, 0.0)
            }
        }
    }

    private fun removeCrystal(endCrystal: EnderCrystal) {
        endCrystal.remove()
        crystalList.remove(endCrystal)
    }

    fun stopNavigations() {
        for (crystal in crystalList) {
            crystal.remove()
            crystalList.remove(crystal)
        }
    }
}