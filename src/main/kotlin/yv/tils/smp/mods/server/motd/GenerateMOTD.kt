package yv.tils.smp.mods.server.motd

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import yv.tils.smp.YVtils
import yv.tils.smp.mods.server.maintenance.MaintenanceHandler
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.server.ServerConfig
import yv.tils.smp.utils.internalAPI.Placeholder
import java.text.SimpleDateFormat
import java.util.*

class GenerateMOTD {
    fun onServerPing(e: PaperServerListPingEvent) {
        if (Config.config["modules.server"] as Boolean) {
            if (MaintenanceHandler.maintenance) {
                e.version = "Server under maintenance"
                e.protocolVersion = 0
                e.maxPlayers = 0
                e.setHidePlayers(true)
                e.motd(handlePlaceholders(ServerConfig.config["motd.maintenance"] as String))
                return
            }

            e.maxPlayers = ServerConfig.config["maxPlayers"] as Int
            e.numPlayers = Placeholder().calcOnlinePlayers()

            e.setHidePlayers(false)
            e.listedPlayers.clear()
            e.listedPlayers.addAll(getHoverText())

            e.motd(getMOTD())
        }
    }

    private fun getHoverText(): Collection<PaperServerListPingEvent.ListedPlayerInfo> {
        val legacy: LegacyComponentSerializer = LegacyComponentSerializer.legacySection()
        val profiles = mutableListOf<PaperServerListPingEvent.ListedPlayerInfo>()

        for (line in ServerConfig.config["playerCountHover"] as List<String>) {
            val profile: PaperServerListPingEvent.ListedPlayerInfo = PaperServerListPingEvent.ListedPlayerInfo(
                legacy.serialize(handlePlaceholders(line)),
                UUID.randomUUID()
            )

            profiles.add(profile)
        }

        return profiles
    }

    private fun getMOTD(): Component {
        val top = ServerConfig.config["motd.top"] as List<String>
        val bottom = ServerConfig.config["motd.bottom"] as List<String>

        val motd = Component.text()
        motd.append(handlePlaceholders(selectRandomLine(top)))
        motd.append(Component.newline())
        motd.append(handlePlaceholders(selectRandomLine(bottom)))

        return motd.asComponent()
    }

    private fun selectRandomLine(lines: List<String>): String {
        return lines.random()
    }

    private fun handlePlaceholders(line: String): Component {
        var serverVersion = YVtils.instance.server.minecraftVersion
        val viaVersion = YVtils.instance.server.pluginManager.getPlugin("ViaVersion") != null
        if (viaVersion) {
            serverVersion = "$serverVersion +"
        }

        val date = SimpleDateFormat("dd/MM/yyyy").format(Date())

        var onlinePlayers = ""
        for (player in Bukkit.getOnlinePlayers()) {
            onlinePlayers += player.name + ", "
        }
        if (onlinePlayers.isNotEmpty()) {
            onlinePlayers = onlinePlayers.substring(0, onlinePlayers.length - 2)
        }

        val varMap = mapOf(
            "serverName" to Config.config["serverName"].toString(),
            "version" to serverVersion,
            "players" to Placeholder().calcOnlinePlayers().toString(),
            "maxPlayers" to Bukkit.getMaxPlayers().toString(),
            "date" to date,
            "onlinePlayers" to onlinePlayers
        )

        return Placeholder().replacer(
            line,
            varMap
        )
    }
}