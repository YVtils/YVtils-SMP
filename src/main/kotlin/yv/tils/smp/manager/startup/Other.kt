package yv.tils.smp.manager.startup

import org.bukkit.permissions.Permission
import yv.tils.smp.YVtils
import yv.tils.smp.utils.updater.PluginVersion

class Other {
    fun register() {
        pluginVersion()
        permissions()
    }

    private fun pluginVersion() {
        PluginVersion().updateChecker(YVtils.instance.pluginVersion)
    }

    private fun permissions() {
        val pm = YVtils.instance.server.pluginManager
        pm.addPermission(Permission.loadPermission("yvtils.smp.multiMine", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.chatSync", mapOf()))
    }
}