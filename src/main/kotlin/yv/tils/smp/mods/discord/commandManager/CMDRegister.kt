package yv.tils.smp.mods.discord.commandManager

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.discord.DiscordConfig
import yv.tils.smp.utils.configs.language.Language

class CMDRegister : ListenerAdapter() {
    override fun onReady(e: ReadyEvent) {
        val commandData: MutableList<CommandData> = mutableListOf()
        serverInfoCMD(commandData)
        whitelistCMD(commandData)
        helpCMD(commandData)
        e.jda.updateCommands().addCommands(commandData).queue()
    }

    private fun serverInfoCMD(commandData: MutableList<CommandData>) {
        try {
            commandData.add(Commands.slash("mcinfo", ColorUtils().convert(Language().directFormat("Sends a helpful overview about the Minecraft Server",
                "Sendet Hilfreiche Information über den Minecraft Server")))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.valueOf(DiscordConfig.config["serverInfoCommand.permission"] as String))))
        } catch (_: Exception) {
            commandData.add(Commands.slash("mcinfo", ColorUtils().convert(Language().directFormat("Sends a helpful overview about the Minecraft Server",
                "Sendet Hilfreiche Information über den Minecraft Server")))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND)))
        }
    }

    private fun whitelistCMD(commandData: MutableList<CommandData>) {
        val data1 = SubcommandData("forceadd", ColorUtils().convert(Language().directFormat("Let you add Minecraft Accounts to the Whitelist. You can link an Discord Account too, if you want to",
            "Lässt dich einen Minecraft Account zur whitelist hinzufügen. Du kannst freiwillig einen Discord Account verlinken")))

        val data2 = SubcommandData("forceremove", ColorUtils().convert(Language().directFormat("Gives you a menu, where you can select one account, which will be removed from the whitelist",
            "Gibt ein Menu zurück, in dem du einen Account auswählen kannst, welcher von der whitelist entfernt wird")))

        val data3 = SubcommandData("check", ColorUtils().convert(Language().directFormat("Let you check if a Minecraft Account is whitelisted, when yes with wich discord account it is linked",
            "Lässt dich überprüfen ob ein Minecraft Account auf der whitelist ist, wenn ja siehst du auch mit welchem Discord Account dieser gelinkt ist")))

        val options1: MutableList<OptionData> = mutableListOf()
        val options2: MutableList<OptionData> = mutableListOf()
        val options3: MutableList<OptionData> = mutableListOf()

        options1.add(OptionData(OptionType.STRING, "mc_name", ColorUtils().convert(Language().directFormat("Enter a Minecraft InGame Name to whitelist this Name.",
            "Schreibe hier, welcher Minecraft Account zur whitelist hinzugefügt werden soll")), true))

        options1.add(OptionData(OptionType.USER, "dc_name", ColorUtils().convert(Language().directFormat("When you want to link an Discord Account to an MC Account, select here the Account of the User",
            "Wenn du einen Discord Account linken willst, setze ihn hiermit fest")), false))

        data1.addOptions(options1)

        options2.add(OptionData(OptionType.INTEGER, "site", ColorUtils().convert(Language().directFormat("Enter the site you want to see from the whitelist (25 players per site), leave blank for site one",
            "Bitte wähle die gewünschte Seite hier aus (Pro Seite 25 Einträge). Leer lassen für Seite Eins")), false))

        data2.addOptions(options2)

        options3.add(OptionData(OptionType.STRING, "name", ColorUtils().convert(Language().directFormat("Input a Name from Minecraft to check it",
            "Setzte hier einen Minecraft Namen fest, welchen du überprüfen willst")), true))

        data3.addOptions(options3)

        try {
            commandData.add(Commands.slash("whitelist", ColorUtils().convert(Language().directFormat("Sends a Whitelist Request to the Minecraft Server",
                "Sendet eine whitelist Anfrage an den Minecraft Server")))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.valueOf(DiscordConfig.config["whitelistCommand.permission"] as String)))
                .addSubcommands(data1, data2, data3))
        } catch (_: Exception) {
            commandData.add(Commands.slash("whitelist", ColorUtils().convert(Language().directFormat("Sends a Whitelist Request to the Minecraft Server",
                "Sendet eine whitelist Anfrage an den Minecraft Server")))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
                .addSubcommands(data1, data2, data3))
        }
    }

    private fun helpCMD(commandData: MutableList<CommandData>) {
        try {
            commandData.add(Commands.slash("help", ColorUtils().convert(Language().directFormat("Sends a helpful overview about the Minecraft Server",
                "Sendet Hilfreiche Information über den Minecraft Server")))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.valueOf(DiscordConfig.config["helpCommand.permission"] as String))))
        } catch (_: Exception) {
            commandData.add(Commands.slash("help", ColorUtils().convert(Language().directFormat("Sends a helpful overview about the YVtils SMP Plugin Features and Guides",
                "Sendet eine Hilfreiche Übersicht über das YVtils SMP Plugin und dessen Funktionen und Anleitungen")))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND)))
        }
    }
}