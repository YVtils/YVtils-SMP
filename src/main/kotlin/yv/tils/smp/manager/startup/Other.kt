package yv.tils.smp.manager.startup

import yv.tils.smp.YVtils
import yv.tils.smp.utils.updater.PluginVersion

class Other {
    fun register() {
        pluginVersion()
    }

    private fun pluginVersion() {
        PluginVersion().updateChecker(YVtils.instance.pluginVersion)
    }
}