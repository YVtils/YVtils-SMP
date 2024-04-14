package yv.tils.smp.mods.discord.embedManager

import net.dv8tion.jda.api.EmbedBuilder

class LogEmbed {
    val builder = EmbedBuilder()

    fun embed(desc: String): EmbedBuilder {
        return builder
            .setTitle("Log")
            .setDescription(desc)
            .setColor(EmbedVars.warningColor)
            .setFooter(EmbedVars.footerText, EmbedVars.footerIcon)
            .setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)
    }
}