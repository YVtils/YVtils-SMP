package yv.tils.smp.mods.discord.sync.consoleSync

import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.bukkit.scheduler.BukkitRunnable
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.BotManager.Companion.jda
import yv.tils.smp.utils.configs.discord.DiscordConfig
import java.text.SimpleDateFormat

class GetConsole : AbstractAppender("YVtilsSMPLogger", null, null, true, null) {
    companion object {
        var active = DiscordConfig.config["consoleSync.enabled"] as Boolean
        val channelID = DiscordConfig().readChannelID("consoleSync.channel")

        private var message = StringBuilder()
        private var newContentLength = 0
        private var messageID = ""
    }

    init {
        start()
    }

    private fun sendMessage() {
        var messageContent = message.toString()

        if (messageContent.isNotEmpty()) {
            if (messageID.isNotEmpty()) {
                val oldMessageContent = jda.getTextChannelById(channelID)?.retrieveMessageById(messageID)?.complete()?.contentRaw ?: ""
                messageContent = oldMessageContent + "\n" + messageContent
            }

            val chunks = splitMessage(messageContent)
            message = StringBuilder()

            chunks.forEachIndexed { _, chunk ->
                val formattedChunk = "```$chunk```"

                var currentMessage = if (messageID.isEmpty()) {
                    jda.getTextChannelById(channelID)?.sendMessage(formattedChunk)?.complete()
                } else {
                    jda.getTextChannelById(channelID)?.editMessageById(messageID, formattedChunk)?.complete()
                }

                messageID = currentMessage?.id ?: ""
                var messageLength = currentMessage?.contentRaw?.length ?: 0

                if (messageLength >= 1900) {
                    messageID = ""
                }

                if (chunks.size > 1) {
                    messageID = ""
                }
            }
        }
    }

    private fun splitMessage(message: String): List<String> {
        val chunks = mutableListOf<String>()
        val split = message.split("\n")
        var currentChunk = ""

        for (i in 0 until split.size) {
            var splitPart = split[i].replace("```", "")

            if (currentChunk.length + splitPart.length < 1990) {
                currentChunk += "\n" + splitPart
            } else {
                currentChunk = currentChunk.trim()
                chunks.add(currentChunk)
                currentChunk = "\n" + splitPart
            }
        }

        currentChunk = currentChunk.trim()
        chunks.add(currentChunk)
        return chunks
    }

    override fun append(event: LogEvent) {
        if (!active) return

        val newContent = "[${SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(event.timeMillis)} ${event.level}] ${event.message.formattedMessage}\n"
        newContentLength += newContent.length
        message.append(newContent)
    }

    fun syncTask() {
        if (!active) return
        object : BukkitRunnable() {
            override fun run() {
                sendMessage()
            }
        }.runTaskTimerAsynchronously(YVtils.instance, 20, 20 * 5)
    }
}
