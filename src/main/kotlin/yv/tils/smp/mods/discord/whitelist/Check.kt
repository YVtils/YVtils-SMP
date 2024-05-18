package yv.tils.smp.mods.discord.whitelist

import net.dv8tion.jda.api.EmbedBuilder
import org.bukkit.Bukkit
import yv.tils.smp.mods.discord.embedManager.whitelist.AccountCanNotExist
import yv.tils.smp.mods.discord.embedManager.whitelist.discord.Check

class Check {
    fun  whitelistCheck(mc: String): EmbedBuilder {
        val player = Bukkit.getOfflinePlayer(mc)

        if (!mc.matches(Regex("[a-zA-Z0-9_]+"))) {
            return AccountCanNotExist().embed(mc)
        }

        val dc = ImportWhitelist().reader(mc = mc, uuid = player.uniqueId.toString())[0]

        val isWhitelisted = player.isWhitelisted
        return Check().embed(mc, dc, isWhitelisted)
    }
}