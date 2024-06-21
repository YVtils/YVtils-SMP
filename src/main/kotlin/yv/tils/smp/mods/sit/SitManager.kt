package yv.tils.smp.mods.sit

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.UUID

class SitManager {
    companion object {
        val sittingPlayers = mutableListOf<UUID>()
    }

    fun sit(player: Player) {
        val uuid = player.uniqueId

        if (isSitting(uuid)) {
            return
        }
        sittingPlayers.add(uuid)

        sitDown(0.0, 1.975, 0.0, player.location).addPassenger(player)
    }

    fun sitGetter(player: Player) {
        if (player.vehicle is ArmorStand) {
            val sit = player.vehicle as ArmorStand
            standUp(player, sit, 0.0, 1.975, 0.0)
        }
    }

    private fun sitDown(x: Double, y: Double, z: Double, location: Location): ArmorStand {
        val world = location.getWorld()
        val sit: ArmorStand = world.spawnEntity(location.subtract(x, y, z), EntityType.ARMOR_STAND) as ArmorStand

        sit.isInvulnerable = true
        sit.isInvisible = true
        sit.setGravity(false)

        return sit
    }

    fun standUp(player: Player, armorStand: ArmorStand, x: Double, y: Double, z: Double) {
        val uuid = player.uniqueId
        if (!isSitting(uuid)) {
            return
        }

        armorStand.remove()
        player.teleport(player.location.add(x, y, z))
        sittingPlayers.remove(uuid)
    }

    fun isSitting(uuid: UUID): Boolean {
        return sittingPlayers.contains(uuid)
    }
}