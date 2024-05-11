package yv.tils.smp.mods.discord.sync.chatSync

import net.dv8tion.jda.api.EmbedBuilder
import org.bukkit.entity.Player
import java.awt.Color

class Embed {
    val builder = EmbedBuilder()

    fun embed(sender: Player, message: String): EmbedBuilder {

        println("DEBUG | " + "https://cravatar.eu/helmhead/" + sender.uniqueId+ "/600")

        return builder
            .setAuthor(sender.getName(), null, "https://cravatar.eu/helmhead/" + sender.uniqueId+ "/600")
            .setDescription(message)
            .setColor(Color(0xABFF99))
    }
}