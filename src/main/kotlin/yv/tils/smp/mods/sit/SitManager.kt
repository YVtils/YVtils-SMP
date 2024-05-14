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
        val uuid = player.getUniqueId()

        if (isSitting(uuid)) {
            return
        }
        sittingPlayers.add(uuid)

        sitDown(0.0, 1.975, 0.0, player.getLocation()).addPassenger(player)
    }

    fun sitGetter(player: Player) {
        if (player.getVehicle() is ArmorStand) {
            val sit = player.getVehicle() as ArmorStand
            standUp(player, sit, 0.0, 1.975, 0.0)
        }
    }

    fun sitDown(x: Double, y: Double, z: Double, location: Location): ArmorStand {
        val world = location.getWorld()
        val sit: ArmorStand = world.spawnEntity(location.subtract(x, y, z), EntityType.ARMOR_STAND) as ArmorStand

        sit.setInvulnerable(true);
        sit.setInvisible(true);
        sit.setGravity(false);

        return sit;
    }

    fun standUp(player: Player, armorStand: ArmorStand, x: Double, y: Double, z: Double) {
        val uuid = player.getUniqueId()
        if (!isSitting(uuid)) {
            return
        }

        armorStand.remove()
        player.teleport(player.getLocation().add(x, y, z))
        sittingPlayers.remove(uuid)
    }

    fun isSitting(uuid: UUID): Boolean {
        return sittingPlayers.contains(uuid)
    }
}