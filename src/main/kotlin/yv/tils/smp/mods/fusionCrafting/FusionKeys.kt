package yv.tils.smp.mods.fusionCrafting

import org.bukkit.NamespacedKey
import yv.tils.smp.YVtils

enum class FusionKeys(val key: NamespacedKey) {
    FUSION_GUI(NamespacedKey(YVtils.instance, "fusion_gui")),
    FUSION_INVISIBLE(NamespacedKey(YVtils.instance, "fusion_invisible"))
}