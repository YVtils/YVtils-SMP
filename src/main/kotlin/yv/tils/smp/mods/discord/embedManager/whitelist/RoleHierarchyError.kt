package yv.tils.smp.mods.discord.embedManager.whitelist

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Role
import yv.tils.smp.mods.discord.embedManager.EmbedVars
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder

class RoleHierarchyError {
    val builder = EmbedBuilder()

    fun embed(role: String, guild: Guild?): EmbedBuilder {
        val role = role.replace(" ", "")
        val roles = role.split(",")
        var role1: Role

        val list = mutableListOf<String>()
        for (r in roles) {
            role1 = guild?.getRoleById(r)!!
            list.add(role1.asMention)
        }

        return builder
            .setTitle(ColorUtils().convert(Language().getMessage(LangStrings.EMBED_CMD_ROLE_ADD_ERROR_TITLE)))
            .setDescription(ColorUtils().convert(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.EMBED_CMD_ROLE_ADD_ERROR_DESC),
                    listOf("role"),
                    list
                )
            ))
            .setColor(EmbedVars.errorColor)
            .setFooter(EmbedVars.footerText, EmbedVars.footerIcon)
            .setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)
    }
}