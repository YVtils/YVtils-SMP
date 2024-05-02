package yv.tils.smp.mods.discord.sync.consoleSync

import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.bukkit.scheduler.BukkitRunnable
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.BotManager.Companion.jda
import yv.tils.smp.utils.configs.discord.DiscordConfig
import yv.tils.smp.utils.configs.global.Config
import java.text.SimpleDateFormat

class GetConsole : AbstractAppender("YVtilsSMPLogger", null, null, true, null) {
    companion object {
        var active = Config.config["modules.discord"] as Boolean
        val channelID = DiscordConfig().readChannelID("consoleSync.channel")
    }

    init {
        start()

        object : BukkitRunnable() {
            override fun run() {
                println("Unnecessary console message spam")
                sendMessage()
            }
        }.runTaskTimerAsynchronously(YVtils.instance, 10, 10)
    }

    private var message = StringBuilder()

    private var newContentLength = 0

    private fun sendMessage() {
        val messageContent = message.toString()
        if (messageContent.isNotEmpty()) {
            val chunks = messageContent.chunked(2000)
            chunks.forEachIndexed { index, chunk ->
                var remainingChunk = chunk
                while (remainingChunk.isNotEmpty()) {
                    val currentChunk = if (remainingChunk.length > 2000) {
                        remainingChunk.substring(0, 2000)
                    } else {
                        remainingChunk
                    }
                    val formattedChunk = "```$currentChunk```"
                    if (messageID == "") {
                        jda.getTextChannelById(channelID)?.sendMessage(formattedChunk)?.queue { messageID = it.id }
                    } else {
                        if ((messageLength(messageID) + currentChunk.length) <= 1999) {
                            jda.getTextChannelById(channelID)?.editMessageById(messageID, formattedChunk)?.queue()
                        } else {
                            jda.getTextChannelById(channelID)?.sendMessage(formattedChunk)?.queue { messageID = it.id }
                        }
                    }
                    remainingChunk = if (remainingChunk.length > 2000) {
                        remainingChunk.substring(2000)
                    } else {
                        ""
                    }
                }
            }
        }
    }


    private fun messageLength(messageID: String): Int {
        return jda.getTextChannelById(channelID)?.retrieveMessageById(messageID)?.complete()?.contentRaw?.length ?: 0
    }

    override fun append(event: LogEvent) {
        if (!active) return

        val newContent = "[${SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(event.timeMillis)} ${event.level}] ${event.message.formattedMessage}\n"
        newContentLength += newContent.length
        message.append(newContent)
    }

    private var messageID = ""

    fun syncTask() {
        if (!active) return
        object : BukkitRunnable() {
            override fun run() {
                sendMessage()
            }
        }.runTaskTimerAsynchronously(YVtils.instance, 20, 20 * 3)
    }
}