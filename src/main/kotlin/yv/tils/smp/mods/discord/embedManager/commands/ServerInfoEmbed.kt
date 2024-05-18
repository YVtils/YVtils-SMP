package yv.tils.smp.mods.discord.embedManager.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.embedManager.EmbedVars
import yv.tils.smp.mods.discord.whitelist.ImportWhitelist
import yv.tils.smp.utils.configs.global.Config

class ServerInfoEmbed {
    val builder = EmbedBuilder()

    fun embed(user: User): EmbedBuilder {
        var version = YVtils.instance.server.minecraftVersion
        val viaVersion = YVtils.instance.server.pluginManager.getPlugin("ViaVersion") != null

        if (viaVersion) {
            version = "$version +"
        }

        var serverIP = Config.config["serverIP"]
        if (serverIP == null) {
            serverIP = ""
        }

        val userID = user.id
        var mcName = "-"

        if (ImportWhitelist().reader(dc = userID)[0] == userID) {
            mcName = ImportWhitelist().reader(dc = userID)[1]
        }

        builder
            .setTitle("Minecraft Server Info")
            .setThumbnail("attachment://server-icon.png")
            .addField("Version", version, true)
            .addField(
                "Players",
                "${YVtils.instance.server.onlinePlayers.size}/${YVtils.instance.server.maxPlayers}",
                true
            )
            .addField("Difficulty", YVtils.instance.server.worlds[0].difficulty.name, true)
            .addField("Linked Account", mcName, false)
            .setColor(EmbedVars.infoColor)
            .setFooter(EmbedVars.footerText, EmbedVars.footerIcon)

        return if (serverIP == "") {
            builder.setAuthor(EmbedVars.authorName, EmbedVars.authorLink, EmbedVars.authorIcon)
        } else {
            builder.setAuthor("${EmbedVars.authorName} • $serverIP", EmbedVars.authorLink, EmbedVars.authorIcon)
        }
    }
}