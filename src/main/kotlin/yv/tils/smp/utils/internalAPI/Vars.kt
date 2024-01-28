package yv.tils.smp.utils.internalAPI

import yv.tils.smp.utils.configs.global.Config

class Vars {
    var prefix = " <dark_gray>[<blue>YVtils-SMP<dark_gray>]<white>"
    var prefixCustom = " ${Config.config["prefix"]}"
}