package yv.tils.smp.manager.startup

import org.bukkit.permissions.Permission
import yv.tils.smp.YVtils
import yv.tils.smp.mods.server.maintenance.MaintenanceHandler
import yv.tils.smp.utils.updater.PluginVersion

class Other {
    /**
     * Register all other startup tasks
     */
    fun register() {
        pluginVersion()
        permissions()
        MaintenanceHandler().loadState()
    }

    private fun pluginVersion() {
        PluginVersion().asyncUpdateChecker()
        PluginVersion().updateChecker(YVtils.instance.yvtilsVersion)
    }

    private fun permissions() {
        val pm = YVtils.instance.server.pluginManager
        pm.addPermission(Permission.loadPermission("yvtils.smp.multiMine", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.chatSync", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.command.moderation.announcement", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.command.multiMine.toggle", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.command.multiMine.manage", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.bypass.globalmute", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.bypass.maintenance", mapOf()))
        pm.addPermission(Permission.loadPermission("yvtils.smp.msg.receive", mapOf()))
    }
}