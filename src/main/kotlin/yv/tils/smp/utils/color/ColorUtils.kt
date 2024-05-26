package yv.tils.smp.utils.color

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

class ColorUtils {
    fun convert(text: String): Component {
        return MiniMessage.miniMessage().deserialize(text)
    }

    fun convert(text: Component): String {
        return MiniMessage.miniMessage().serialize(text)
    }

    fun convertChatMessage(text: Component): Component {
        return convert(strip(text))
    }

    fun strip(text: String): String {
        return PlainTextComponentSerializer.plainText().serialize(convert(text))
    }

    fun strip(text: Component): String {
        return PlainTextComponentSerializer.plainText().serialize(text)
    }

    fun stripChatMessage(text: Component): String {
        return strip(strip(text))
    }
}