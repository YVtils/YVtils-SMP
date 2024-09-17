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

    fun handleLore(text: String): List<Component> {
        val lore = mutableListOf<Component>()
        val mm = MiniMessage.miniMessage()

        var latestColorString = ""

        if (text.contains("<newline>")) {
            val lines = text.split("<newline>")
            for (line in lines) {
                lore.add(mm.deserialize(latestColorString + line))

                latestColorString = mm.serialize(mm.deserialize(line).children().last())
            }
        } else if (text.contains("<br>")) {
            val lines = text.split("<br>")
            for (line in lines) {
                lore.add(mm.deserialize(latestColorString + line))

                latestColorString = mm.serialize(mm.deserialize(line).children().last())
            }
        } else if (text.contains("\n")) {
            val lines = text.split("\n")
            for (line in lines) {
                println("line: $line")

                if (line.trim().isEmpty()) {
                    continue
                }

                lore.add(mm.deserialize(latestColorString + line))

                if (mm.deserialize(line).children().isEmpty()) {
                    continue
                }

                latestColorString = mm.serialize(mm.deserialize(line).children().last())
            }
        } else {
            lore.add(mm.deserialize(text))
        }

        println("lore: $lore")

        return lore
    }

    fun handleLore(text: Component): List<Component> {
        return handleLore(convert(text))
    }
}