package yv.tils.smp.mods.discord.embedManager.whitelist.discord

import net.dv8tion.jda.api.EmbedBuilder
import yv.tils.smp.mods.discord.embedManager.EmbedVars
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

class Check {
    val builder = EmbedBuilder()

    fun embed(mc: String, dc: String, b: Boolean): EmbedBuilder {
        val list = mutableListOf<String>()
        if (dc.matches(Regex("\\d+"))) {
            list.add("<@${dc}>")
        } else {
            list.add(dc)
        }

        builder
            .setTitle(ColorUtils().convert(Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_CHECK_TITLE)))
            .setDescription(ColorUtils().convert(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_CHECK_WHITELISTED_DESC),
                    listOf("mcName", "dcName"),
                    listOf(mc, list[0])
                )
            ))
            .setFooter(EmbedVars.footerText, EmbedVars.footerIcon)
            .setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)

        return if (b) {
            builder.setColor(EmbedVars.successColor)
        } else {
            builder.setColor(EmbedVars.errorColor)
        }
    }
}