package yv.tils.smp.mods.discord.embedManager.commands

import net.dv8tion.jda.api.EmbedBuilder
import yv.tils.smp.mods.discord.embedManager.EmbedVars

class HelpEmbed {
    val builder = EmbedBuilder()

    fun embed(): EmbedBuilder {
        return builder
            .setTitle("Help")
            .setDescription("[On this website you can find help for Usage and setting up the plugin](https://yvtils.net/yvtils/smp)")
            .setColor(EmbedVars.infoColor)
            .setFooter(EmbedVars.footerText, EmbedVars.footerIcon)
            .setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)
    }
}