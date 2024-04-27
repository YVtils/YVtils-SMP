package yv.tils.smp.mods.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.commandManager.CMDHandler
import yv.tils.smp.mods.discord.commandManager.CMDRegister
import yv.tils.smp.mods.discord.sync.chatSync.SyncChats
import yv.tils.smp.mods.discord.whitelist.ForceRemove
import yv.tils.smp.mods.discord.whitelist.SelfAdd
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.discord.DiscordConfig
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.logger.Debugger

class BotManager {
    companion object {
        val active = Config.config["modules.discord"] as Boolean

        lateinit var instance: BotManager
        lateinit var jda: JDA
        lateinit var builder: JDABuilder
    }

    val token = DiscordConfig.config["botToken"] as String
    val mainGuild = DiscordConfig.config["mainGuild"] as Long
    val status = DiscordConfig.config["botSettings.onlineStatus"] as String
    val activity = DiscordConfig.config["botSettings.activity"] as String
    val activityMessage = DiscordConfig.config["botSettings.activityMessage"] as String
    val logChannel = DiscordConfig.config["logChannel"] as Long

    fun startBot() {
        if (active) {
            if (checkToken()) {
                instance = this
                appearance()
            }
        }
    }

    private fun checkToken(): Boolean {
        if (!(token.isEmpty() || token.isBlank() || token == ColorUtils().convert(Language().directFormat("YOUR TOKEN HERE", "DEINEN BOT TOKEN")))) {
            builder = JDABuilder.createDefault(token)
            return true
        } else {
            YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.MODULE_DISCORD_NO_BOT_TOKEN_GIVEN))
            YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.MODULE_DISCORD_STARTUP_FAILED))
            return false
        }
    }

    private fun appearance() {
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT)
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS)
        builder.setMemberCachePolicy(MemberCachePolicy.ALL)

        when (activity.lowercase()) {
            "playing" -> builder.setActivity(Activity.playing(activityMessage))
            "listening" -> builder.setActivity(Activity.listening(activityMessage))
            "watching" -> builder.setActivity(Activity.watching(activityMessage))
            "competing" -> builder.setActivity(Activity.competing(activityMessage))
            else -> builder.setActivity(null)
        }

        when (status.lowercase()) {
            "online" -> builder.setStatus(OnlineStatus.ONLINE)
            "idle" -> builder.setStatus(OnlineStatus.IDLE)
            "dnd" -> builder.setStatus(OnlineStatus.DO_NOT_DISTURB)
            "invisible" -> builder.setStatus(OnlineStatus.INVISIBLE)
            else -> builder.setStatus(OnlineStatus.ONLINE)
        }

        builder.addEventListeners(CMDRegister())
        builder.addEventListeners(CMDHandler())
        builder.addEventListeners(ForceRemove())
        builder.addEventListeners(SelfAdd())
        builder.addEventListeners(SyncChats())

        try {
            jda = builder.build()
        } catch (e: Exception) {
            YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.MODULE_DISCORD_STARTUP_FAILED))
            e.printStackTrace()
        }

        try {
            jda.awaitReady()
        } catch (e: Exception) {
            YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.MODULE_DISCORD_STARTUP_FAILED))
            e.printStackTrace()
        }

        YVtils.instance.server.consoleSender.sendMessage(Language().getMessage(LangStrings.MODULE_DISCORD_STARTUP_FINISHED))
    }

    fun stopBot() {
        if (active) {
            try {
                builder.setStatus(OnlineStatus.OFFLINE)
                jda.shutdown()
            } catch (e: Exception) {
                YVtils.instance.server.consoleSender.sendMessage(Language().directFormat(
                    "There was an error while shutting down the bot, for more details enable debug in the config.yml file!",
                    "Es gab einen Fehler beim Herunterfahren des Bots, um weitere Details zu erhalten, aktiviere das Debuggen in der config.yml-Datei"
                ))

                val message: String = e.message.toString()
                Debugger().log("Bot shutting down failed!", message, "yv.tils.smp.mods.discord.BotManager.stopBot()")
            }
        }
    }
}