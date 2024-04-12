package yv.tils.smp.manager.startup

import yv.tils.smp.mods.discord.BotManager
import yv.tils.smp.mods.fusionCrafting.FusionLoader
import yv.tils.smp.utils.configs.status.StatusConfig

class Modules {
    fun registerModules() {
        registerQuests()
        registerStatus()
        registerDiscord()
    }

    private fun registerQuests() {
        FusionLoader().generateDefaultQuests()
        FusionLoader().loadQuestThumbnail()
    }

    private fun registerStatus() {
        StatusConfig().loadConfig()
    }

    private fun registerDiscord() {
        BotManager().startBot()
    }
}