package yv.tils.smp.mods.discord.commandManager

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CMDHandler : ListenerAdapter() {
    override fun onSlashCommandInteraction(e: SlashCommandInteractionEvent) {
        val command = e.name
        val args = e.subcommandName

        when (command) {
            "mcinfo" -> {
                e.reply("mcinfo").queue()
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