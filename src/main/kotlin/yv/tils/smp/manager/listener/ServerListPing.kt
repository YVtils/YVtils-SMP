package yv.tils.smp.manager.listener

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import yv.tils.smp.mods.server.motd.GenerateMOTD

class ServerListPing: Listener {
    @EventHandler
    fun onServerPing(e: PaperServerListPingEvent) {
        GenerateMOTD().onServerPing(e)
    }
}