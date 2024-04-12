package yv.tils.smp.manager.startup

import yv.tils.smp.mods.fusionCrafting.FusionLoader
import yv.tils.smp.utils.configs.status.StatusConfig

class Modules {
    fun registerModules() {
        registerQuests()
        registerStatus()
    }

    private fun registerQuests() {
        FusionLoader().generateDefaultQuests()
        FusionLoader().loadQuestThumbnail()
    }

    private fun registerStatus() {
        StatusConfig().loadConfig()
    }
}