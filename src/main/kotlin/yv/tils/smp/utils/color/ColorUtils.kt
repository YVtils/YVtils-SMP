package yv.tils.smp.utils.color

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

class ColorUtils {
    fun convert(text: String): Component {
        return MiniMessage.miniMessage().deserialize(text)
    }
}