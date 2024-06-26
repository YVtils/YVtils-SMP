package yv.tils.smp.mods.fusionCrafting

import org.bukkit.NamespacedKey
import yv.tils.smp.YVtils

enum class FusionKeys(val key: NamespacedKey) {
    FUSION_GUI(NamespacedKey(YVtils.instance, "fusion_gui")),
    FUSION_ITEMNAME(NamespacedKey(YVtils.instance, "fusion_itemname")),
    FUSION_INVISIBLE(NamespacedKey(YVtils.instance, "fusion_invisible")),
    FUSION_PLAYER_HEAD(NamespacedKey(YVtils.instance, "fusion_player_head"))
}