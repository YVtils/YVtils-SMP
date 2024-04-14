package yv.tils.smp.mods.discord.embedManager.whitelist.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import yv.tils.smp.mods.discord.embedManager.EmbedVars
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

class ForceRemove {
    val builder = EmbedBuilder()

    fun embed(playerCount: Int, whitelist: Boolean, site: Int): EmbedBuilder {
        var status = if (whitelist) {
            "on"
        } else {
            "off"
        }

        val maxSite = (playerCount - 1) / 25 + 1

        return builder
            .setTitle(ColorUtils().convert(Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_REMOVE_TITLE)))
            .setDescription(ColorUtils().convert(Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_REMOVE_DESC)))
            .addField("Whitelisted Players:", playerCount.toString(), true)
            .addField("Whitelist Status:", status, true)
            .setColor(EmbedVars.infoColor)
            .setFooter("YVtils-SMP • Site $site / $maxSite", EmbedVars.footerIcon)
            .setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)
    }

    fun embedRemove(playerCount: Int, whitelist: Boolean, site: Int, acc: MutableList<String>): EmbedBuilder {
        var status = if (whitelist) {
            "on"
        } else {
            "off"
        }

        val maxSite = (playerCount - 1) / 25 + 1

        val list: MutableList<String> = mutableListOf()

        if (acc[0].matches(Regex("\\d+"))) {
            list.add("<@${acc[0]}>")
        } else {
            list.add(acc[0])
        }

        return builder
            .setTitle(ColorUtils().convert(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_REMOVED_TITLE),
                    listOf("accountName"),
                    list
                )
            ))
            .setDescription(ColorUtils().convert(Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_REMOVED_DESC)))
            .addField("Whitelisted Players:", playerCount.toString(), true)
            .addField("Whitelist Status:", status, true)
            .setColor(EmbedVars.infoColor)
            .setFooter("YVtils-SMP • Site $site / $maxSite", EmbedVars.footerIcon)
            .setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)
    }

    fun makeButtons(playerCount: Int, site: Int): MutableList<Button> {
        val maxSite = (playerCount - 1) / 25 + 1
        val buttons: MutableList<Button> = mutableListOf()

        if (site == maxSite) {
            buttons.add(Button.danger("whitelist_remove_prev", "«").asDisabled())
        } else if (site == 1) {
            buttons.add(Button.success("whitelist_remove_next", "»").asDisabled())
        } else {
            buttons.add(Button.danger("whitelist_remove_prev", "«"))
            buttons.add(Button.success("whitelist_remove_next", "»"))
        }

        return buttons
    }
}