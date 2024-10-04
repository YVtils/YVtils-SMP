package yv.tils.smp.mods.admin.moderation.cmd

import dev.jorel.commandapi.kotlindsl.*
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import yv.tils.smp.YVtils
import yv.tils.smp.mods.admin.moderation.handler.MuteHandler
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.admin.mutedPlayers_yml
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars
import java.io.File
import java.util.*

class Mute {
    val command = commandTree("mute") {
        withPermission("yvtils.smp.command.moderation.mute")
        withUsage("mute <player> [reason]")

        offlinePlayerArgument("player") {
            greedyStringArgument("reason", true) {
                anyExecutor { sender, args ->
                    val targetArg = args[0] as OfflinePlayer
                    val target = Bukkit.getOfflinePlayer(MojangAPI().uuid2name(targetArg.uniqueId)!!)
                    val reason = args[1] ?: Language().getRawMessage(LangStrings.MOD_NO_REASON)

                    val muteHandler = MuteHandler()

                    muteHandler.mutePlayer(target, sender, reason as String)
                }
            }
        }
    }
}