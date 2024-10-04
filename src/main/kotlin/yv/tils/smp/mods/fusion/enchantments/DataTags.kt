package yv.tils.smp.mods.fusionCrafting.enchantments

import yv.tils.smp.mods.fusionCrafting.FusionKeys

enum class DataTags(val key: FusionKeys, val desc: String) {
    INVISIBLE(FusionKeys.FUSION_INVISIBLE, ""),
    PLAYER_HEAD(FusionKeys.FUSION_PLAYER_HEAD, ""),

    companion object {
        fun getNameByKey(key: FusionKeys): String? {
            return entries.find { it.key == key }?.name
        }
    }
}