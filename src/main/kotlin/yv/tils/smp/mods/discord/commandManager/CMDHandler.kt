package yv.tils.smp.mods.discord.commandManager

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.utils.FileUpload
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.embedManager.commands.HelpEmbed
import yv.tils.smp.mods.discord.embedManager.commands.ServerInfoEmbed
import yv.tils.smp.mods.discord.embedManager.whitelist.discord.ForceRemove
import yv.tils.smp.mods.discord.whitelist.ImportWhitelist
import java.io.File

class CMDHandler : ListenerAdapter() {
    override fun onSlashCommandInteraction(e: SlashCommandInteractionEvent) {
        val command = e.name
        val args = e.subcommandName

        when (command) {
            "mcinfo" -> {
                val serverIcon = File("./server-icon.png")

                if (serverIcon.exists()) {
                    e.reply("").setEmbeds(ServerInfoEmbed().embed(e.user).build()).setEphemeral(true).addFiles(FileUpload.fromData(serverIcon, "server-icon.png")).queue()
                } else {
                    e.reply("").setEmbeds(ServerInfoEmbed().embed(e.user).build()).setEphemeral(true).queue()
                }
            }
            "whitelist" -> {
                when (args) {
                    "forceadd" -> {
                        e.reply("forceadd").queue()
                    }
                    "forceremove" -> {
                        var site: Int
                        var maxSite: Int

                        try {
                            site = e.getOption("site")!!.asString.toInt()
                            maxSite = e.getOption("maxsite")!!.asString.toInt()

                            if (site > maxSite) {
                                site = 1
                            }
                        } catch (_: NullPointerException) {
                            site = 1
                            maxSite = 1
                        }

                        e.reply("").setEmbeds(ForceRemove().embed((ImportWhitelist.whitelistManager.size), YVtils.instance.server.hasWhitelist(), site).build())
                            .setComponents(
                                ActionRow.of(ForceRemove().makeButtons(ImportWhitelist.whitelistManager.size, 1)),
                                ActionRow.of(ForceRemove().makeDropDown(1).build())
                            )
                            .setEphemeral(true).queue()
                    }
                    "check" -> {
                        e.reply("check").queue()
                    }
                }
            }
            "help" -> {
                e.reply("").setEmbeds(HelpEmbed().embed().build()).setEphemeral(true).queue()
            }
        }
    }
}