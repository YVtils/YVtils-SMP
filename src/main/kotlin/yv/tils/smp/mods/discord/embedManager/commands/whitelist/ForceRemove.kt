package yv.tils.smp.mods.discord.embedManager.whitelist.discord

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import yv.tils.smp.mods.discord.embedManager.EmbedVars
import yv.tils.smp.mods.discord.whitelist.ImportWhitelist
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

        list.add(acc[1])

        if (acc[0].matches(Regex("\\d+"))) {
            list.add("<@${acc[0]}>")
        } else {
            list.add(acc[0])
        }

        return builder
            .setTitle(
                ColorUtils().convert(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_REMOVED_TITLE),
                        listOf("mcName", "dcName"),
                        list
                    )
                )
            )
            .setDescription(ColorUtils().convert(Language().getMessage(LangStrings.EMBED_CMD_WHITELIST_REMOVED_DESC)))
            .addField("Whitelisted Players:", playerCount.toString(), true)
            .addField("Whitelist Status:", status, true)
            .setColor(EmbedVars.infoColor)
            .setFooter("YVtils-SMP • Site $site / $maxSite", EmbedVars.footerIcon)
            .setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)
    }

    fun makeDropDown(site: Int): StringSelectMenu.Builder {
        val list: MutableList<String> = ImportWhitelist.whitelistManager

        if (list.isEmpty()) {
            return StringSelectMenu.create("players")
                .setPlaceholder(ColorUtils().convert(Language().getMessage(LangStrings.WHITELIST_EMPTY)))
                .setDisabled(true)
                .addOption("null", "null")
        }

        var options = mutableListOf<SelectOption>()
        val start = (site - 1) * 25

        for (i in start until list.size) {
            options.add(SelectOption.of(list[i], list[i]))

            if (i >= start + 24) {
                break
            }
        }

        return StringSelectMenu.create("players")
            .setPlaceholder("Discord Tag,Minecraft Name,UUID")
            .addOptions(options)
    }

    fun makeButtons(playerCount: Int, site: Int): MutableList<Button> {
        val maxSite = (playerCount - 1) / 25 + 1
        val buttons: MutableList<Button> = mutableListOf()

        if (maxSite == 1) {
            buttons.add(Button.danger("whitelist_remove_prev", "«").asDisabled())
            buttons.add(Button.success("whitelist_remove_next", "»").asDisabled())
        } else if (site == maxSite) {
            buttons.add(Button.danger("whitelist_remove_prev", "«"))
            buttons.add(Button.success("whitelist_remove_next", "»").asDisabled())
        } else if (site == 1) {
            buttons.add(Button.danger("whitelist_remove_prev", "«").asDisabled())
            buttons.add(Button.success("whitelist_remove_next", "»"))
        } else {
            buttons.add(Button.danger("whitelist_remove_prev", "«"))
            buttons.add(Button.success("whitelist_remove_next", "»"))
        }

        return buttons
    }
}