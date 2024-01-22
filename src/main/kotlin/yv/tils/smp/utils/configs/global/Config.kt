package yv.tils.smp.utils.configs.global

import yv.tils.smp.YVtils

class Config {
    var config: MutableMap<String, Any> = HashMap()

    fun loadConfig() {
        val configFile = YVtils.instance.config

        for (key in configFile.getKeys(true)) {
            config[key] = configFile.get(key) as Any
        }
    }
}