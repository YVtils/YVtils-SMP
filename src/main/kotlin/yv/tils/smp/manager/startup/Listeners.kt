package yv.tils.smp.manager.startup

import yv.tils.smp.YVtils
import yv.tils.smp.manager.listener.*

class Listeners {
    fun register() {
        val plugin = YVtils.instance

        plugin.server.pluginManager.registerEvents(PlayerLoginServer(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerJoinServer(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerQuitServer(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerWorldChange(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerGamemodeSwitch(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerChat(), plugin)
        plugin.server.pluginManager.registerEvents(EntityTarget(), plugin)
        plugin.server.pluginManager.registerEvents(EntityDismount(), plugin)
        plugin.server.pluginManager.registerEvents(EntityDamage(), plugin)
        plugin.server.pluginManager.registerEvents(InvOpen(), plugin)
        plugin.server.pluginManager.registerEvents(InvClose(), plugin)
        plugin.server.pluginManager.registerEvents(InvClick(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerSwapHandItems(), plugin)
        plugin.server.pluginManager.registerEvents(PlayerToggleFlight(), plugin)
        plugin.server.pluginManager.registerEvents(EntityToggleGlide(), plugin)
        plugin.server.pluginManager.registerEvents(PrepareAnvil(), plugin)
        plugin.server.pluginManager.registerEvents(BlockBreak(), plugin)
    }
}