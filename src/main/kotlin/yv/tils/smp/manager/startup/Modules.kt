package yv.tils.smp.manager.startup

import dev.jorel.commandapi.CommandAPI
import yv.tils.smp.mods.discord.BotManager
import yv.tils.smp.mods.discord.whitelist.ImportWhitelist
import yv.tils.smp.mods.fusionCrafting.FusionLoader
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.status.StatusConfig

class Modules {
    fun registerModules() {
        registerFusion()
        registerStatus()
        registerDiscord()
    }

    private fun registerFusion() {
        FusionLoader().generateDefaultFusions()
        FusionLoader().loadFusionThumbnail()
    }

    private fun registerStatus() {
        StatusConfig().loadConfig()
    }

    private fun registerDiscord() {
        BotManager().startBot()
        ImportWhitelist().importer()
    }
}