package yv.tils.smp.manager.startup

import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.configs.language.de_yml
import yv.tils.smp.utils.configs.language.en_yml

class Configs {
    fun register() {
    }

    fun language() {
        de_yml().strings()
        en_yml().strings()
        Language().getLanguageFiles()
    }
}