package yv.tils.smp.manager.startup

import yv.tils.smp.mods.questSystem.QuestLoader

class Modules {
    fun registerModules() {
        registerQuests()
    }

    private fun registerQuests() {
        QuestLoader().generateDefaultQuests()
        QuestLoader().loadQuestThumbnail()
    }
}