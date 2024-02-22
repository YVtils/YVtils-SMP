package yv.tils.smp.manager.startup

import yv.tils.smp.mods.fusionCrafting.FusionLoader

class Modules {
    fun registerModules() {
        registerQuests()
    }

    private fun registerQuests() {
        FusionLoader().generateDefaultQuests()
        FusionLoader().loadQuestThumbnail()
    }
}