package yv.tils.smp.mods.discord.whitelist

import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ActionRow
import org.bukkit.Bukkit
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.BotManager
import yv.tils.smp.mods.discord.embedManager.whitelist.RoleHierarchyError
import yv.tils.smp.mods.discord.embedManager.whitelist.discord.ForceRemove
import yv.tils.smp.utils.configs.discord.DiscordConfig
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.logger.Debugger

class ForceRemove : ListenerAdapter() {
    override fun onStringSelectInteraction(e: StringSelectInteractionEvent) {
        val list = ImportWhitelist.whitelistManager

        if (!e.interaction.values.isEmpty()) {
            val guild = e.guild
            var values = e.values.toString()

            values = values.replace("[", "")
            values = values.replace("]", "")

            val args = values.split(",")

            val player = Bukkit.getOfflinePlayer(args[1])

            list.remove(args[0] + "," + args[1] + "," + args[2])
            Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                player.isWhitelisted = false
            })
            DiscordConfig().changeValue(args[0])

            var user: User
            try {
                user = BotManager.jda.getUserById(args[0])!!
            } catch (_: NumberFormatException) {
                reply(e.member?.user?.globalName, args[1], args[0], list.size, args as MutableList<String>, e)
                return
            }

            Debugger().log("ForceRemove", "Removed ${args[1]} from the whitelist", "yv.tils.smp.mods.discord.whitelist.ForceRemove")

            runCatching {
                try {
                    var role = DiscordConfig.config["whitelistFeature.role"] as String
                    role = role.replace(" ", "")
                    val roles = role.split(",")
                    for (r in roles) {
                        guild?.getRoleById(r)?.let { guild.removeRoleFromMember(user, it) }?.queue()
                    }
                } catch (_: NumberFormatException) {
                    e.reply("").setEmbeds(RoleHierarchyError().embed(DiscordConfig.config["whitelistFeature.role"] as String, guild).build()).setEphemeral(true).queue()
                }
            }

            runCatching {
                reply(e.member?.user?.globalName, args[1], args[0], list.size, args as MutableList<String>, e)
            }
        }
    }

    private fun reply(exec: String?, mc: String, dc: String, size: Int, args: MutableList<String>, e: StringSelectInteractionEvent) {
        YVtils.instance.server.consoleSender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(LangStrings.MODULE_DISCORD_CMD_REGISTERED_REMOVE),
                listOf("discordUser", "mcName", "dcName"),
                listOf(exec!!, mc, dc)
            )
        )

        e.editMessageEmbeds(ForceRemove().embedRemove(size, YVtils.instance.server.hasWhitelist(), 1, args).build())
            .setComponents(
                ActionRow.of(ForceRemove().makeDropDown(1).build()),
                ActionRow.of(ForceRemove().makeButtons(ImportWhitelist.whitelistManager.size, 1))
            )
            .queue()
    }
}