package yv.tils.smp.mods.waypoints

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import yv.tils.smp.YVtils
import yv.tils.smp.utils.color.ColorUtils
import kotlin.math.atan2

class WaypointPath {
    companion object {
        val crystalList: MutableList<EnderCrystal> = mutableListOf()
    }

    fun generatePath(player: Player, x: Double, y: Double, z: Double, worldName: String) {
        // TODO: Check if player is already navigating, if so, cancel/switch the current navigation

        val world = Bukkit.getWorld(worldName)
        val location = Location(world, x, y, z)

        navigate(player, location)
    }

    private fun navigate(player: Player, location: Location) {
        val endCrystal = spawnEndCrystal(player, location)

        object : BukkitRunnable() {
            override fun run() {
                spawnParticle(player, player.location, location)
                generateActionbar(player, player.location, location)

                if (location.distance(player.location) < 10) {
                    removeCrystal(endCrystal)
                    cancel()
                    return
                }
            }
        }.runTaskTimer(YVtils.instance, 0, 20)
    }

    private fun generateActionbar(player: Player, currentLoc: Location, targetLoc: Location) {
        val distance = currentLoc.distance(targetLoc)

        val direction = direction(currentLoc, targetLoc)

        player.sendActionBar(ColorUtils().convert("<green>$direction <white>| <yellow>${distance.toInt()}<white>m <white>| <green>$direction<white>"))
    }

    private fun direction(currentLoc: Location, targetLoc: Location): String {
        val x = targetLoc.x - currentLoc.x
        val z = targetLoc.z - currentLoc.z

        val angle = Math.toDegrees(atan2(z, x)) + 90 - (currentLoc.yaw + 180)

        // ⇑⇒⇓⇖⇗⇘⇙⇐
        // ↑→↓↖↗↘↙←
        return when (angle) {
            in -75.0 .. -15.0 -> "↖"
            in -105.0 .. -75.0 -> "←"
            in -165.0 .. -105.0 -> "↙"
            in -185.0 .. -165.0 -> "↓"
            in -255.0 .. -185.0 -> "↘"
            in -285.0 .. -255.0 -> "→"
            in -345.0 .. -285.0 -> "↗"
            else -> "↑"
        }
    }

    private fun spawnEndCrystal(player: Player, location: Location): EnderCrystal {
        val world = location.world
        val spawnLocation = location.clone().set(location.x, 400.0, location.z)

        while (spawnLocation.block.isPassable) {
            spawnLocation.subtract(0.0, 1.0, 0.0)
        }

        spawnLocation.subtract(0.0, 5.0, 0.0)

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

    private fun spawnParticle(player: Player, currentLoc: Location, targetLoc: Location) {
        val start = currentLoc.clone()
        val end = targetLoc.clone()
        val distance = start.distance(end)
        val particles = 3 * distance.toInt()

        val d = distance / particles

        for (i in 0 until particles) {
            val particleLoc = start.clone()
            val direction = end.toVector().subtract(start.toVector()).normalize()
            val v = direction.multiply(i * d)

            particleLoc.add(v.x, v.y, v.z)

            player.spawnParticle(Particle.FALLING_SPORE_BLOSSOM, particleLoc, 1, 0.0, 0.0, 0.0, 0.0)
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