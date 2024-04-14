package yv.tils.smp.mods.discord.commandManager

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.utils.FileUpload
import yv.tils.smp.mods.discord.embedManager.commands.ServerInfoEmbed
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
                        e.reply("forceremove").queue()
                    }
                    "list" -> {
                        e.reply("list").queue()
                    }
                }
            }
            "help" -> {
                e.reply("help").queue()
            }
        }
    }
}