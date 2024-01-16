package yv.tils.smp.manager.startup

import yv.tils.smp.YVtils
import yv.tils.smp.manager.listener.PlayerJoinServer
import yv.tils.smp.manager.listener.PlayerLoginServer
import yv.tils.smp.manager.listener.PlayerQuitServer

class Listeners {
    fun register() {
        val plugin = YVtils.instance

        plugin.server.pluginManager.registerEvents(PlayerLoginServer(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerJoinServer(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerQuitServer(), plugin)
    }
}