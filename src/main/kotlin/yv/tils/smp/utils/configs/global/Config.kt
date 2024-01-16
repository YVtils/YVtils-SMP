package yv.tils.smp.utils.configs.global

class Config {
    var config: Map<String, Any> = HashMap()

    fun loadConfig() {
        config = config.plus("Language" to "en")
    }
}