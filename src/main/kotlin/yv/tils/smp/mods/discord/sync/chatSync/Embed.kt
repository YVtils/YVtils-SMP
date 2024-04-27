package yv.tils.smp.mods.discord.sync.chatSync

import net.dv8tion.jda.api.EmbedBuilder
import org.bukkit.entity.Player
import yv.tils.smp.mods.discord.whitelist.ImportWhitelist
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.status.StatusConfig
import java.awt.Color

class Embed {
    val builder = EmbedBuilder()

    private fun color(sender: Player): Color {
        val statusConfig = ImportWhitelist().reader(null, sender.name, sender.uniqueId.toString())

        if (statusConfig[2] == sender.uniqueId.toString()) {
            return Color(0xABFF99)
        }

        val status = StatusConfig.saves[statusConfig[0]]
        val statusString = status as String
        val statusComponent = ColorUtils().convert(statusString)


        return try {
            Color(statusComponent.color()?.asHexString()!!.toInt(16))
        } catch (_: Exception) {
            Color(0xABFF99)
        }
    }

    fun embed(sender: Player, message: String): EmbedBuilder {
        return builder
            .setAuthor(sender.getName(), null, "https://cravatar.eu/helmhead/" + sender.getName() + "/600")
            .setDescription(message)
            .setColor(color(sender));
    }
}