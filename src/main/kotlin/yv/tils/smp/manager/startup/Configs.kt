package yv.tils.smp.manager.startup

import yv.tils.smp.utils.configs.admin.mutedPlayers_yml
import yv.tils.smp.utils.configs.discord.config_yml_discord
import yv.tils.smp.utils.configs.discord.save_yml_discord
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.global.config_yml
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.configs.language.de_yml
import yv.tils.smp.utils.configs.language.en_yml
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig
import yv.tils.smp.utils.configs.multiMine.config_yml_multiMine
import yv.tils.smp.utils.configs.status.config_yml_status
import yv.tils.smp.utils.configs.status.save_yml_status

class Configs {
    fun register() {
        config_yml().strings()
        config_yml_status().strings()
        config_yml_discord().strings()

        save_yml_status().strings()
        save_yml_discord().strings()

        mutedPlayers_yml().strings()

        config_yml_multiMine().strings()

        Config().loadConfig()
        mutedPlayers_yml().loadConfig()
        MultiMineConfig().loadConfig()
    }

    fun language() {
        de_yml().strings()
        en_yml().strings()
        Language().getLanguageFiles()
    }
}