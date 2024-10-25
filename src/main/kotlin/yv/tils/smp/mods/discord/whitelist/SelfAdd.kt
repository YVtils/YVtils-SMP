package yv.tils.smp.mods.discord.whitelist

import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.exceptions.HierarchyException
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bukkit.Bukkit
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.embedManager.whitelist.AccountAdded
import yv.tils.smp.mods.discord.embedManager.whitelist.AccountAlreadyListed
import yv.tils.smp.mods.discord.embedManager.whitelist.AccountCanNotExist
import yv.tils.smp.mods.discord.embedManager.whitelist.AccountChange
import yv.tils.smp.mods.discord.embedManager.whitelist.AccountCheckError
import yv.tils.smp.mods.discord.embedManager.whitelist.AccountNotFound
import yv.tils.smp.mods.discord.embedManager.whitelist.RoleHierarchyError
import yv.tils.smp.utils.configs.discord.DiscordConfig
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.net.HttpURLConnection
import java.net.URI
import java.util.concurrent.TimeUnit

class SelfAdd : ListenerAdapter() {
    override fun onMessageReceived(e: MessageReceivedEvent) {

        if (!e.channel.type.isMessage) return
        if (e.author.isBot) return
        if (e.channelType.compareTo(ChannelType.TEXT) != 0) return
        if (e.channel.id != DiscordConfig.config["whitelistFeature.channel"].toString()) return

        val channel = e.channel.asTextChannel()
        val userID = e.author.id

        val name = e.message.contentRaw
        val messageID = e.messageId

        channel.deleteMessageById(messageID).queue()

        if (!name.matches(Regex("[a-zA-Z0-9_]+"))) {
            channel.sendMessageEmbeds(AccountCanNotExist().embed(e.message.contentRaw).build()).complete().delete()
                .queueAfter(5, TimeUnit.SECONDS)
            return
        }

        val player = Bukkit.getOfflinePlayer(name)

        if (player.isWhitelisted) {
            channel.sendMessageEmbeds(AccountAlreadyListed().embed(e.message.contentRaw).build()).complete().delete()
                .queueAfter(5, TimeUnit.SECONDS)
            return
        }

        runCatching {
            val url = URI("https://api.mojang.com/users/profiles/minecraft/$name").toURL()
                .openConnection() as HttpURLConnection
            url.requestMethod = "GET"

            val statusCode = url.responseCode
            if (statusCode == HttpURLConnection.HTTP_OK) {
                if (ImportWhitelist().reader(dc = userID).contains(userID)) {
                    val whitelist = ImportWhitelist().reader(dc = userID)
                    val oldPlayer = Bukkit.getOfflinePlayer(whitelist[1])

                    Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                        oldPlayer.isWhitelisted = false
                    })

                    whitelistRemove(userID, oldPlayer.name.toString(), oldPlayer.uniqueId.toString())

                    try {
                        var role = DiscordConfig.config["whitelistFeature.role"] as String
                        role = role.replace(" ", "")
                        val roles = role.split(",")

                        for (r in roles) {
                            try {
                                val guildRole = e.guild.getRoleById(r)
                                e.member?.let { guildRole?.let { it1 -> e.guild.addRoleToMember(it, it1) } }?.queue()
                            } catch (_: NumberFormatException) {
                            }
                        }

                        YVtils.instance.server.consoleSender.sendMessage(
                            Placeholder().replacer(
                                Language().getMessage(LangStrings.MODULE_DISCORD_REGISTERED_NAME_CHANGE),
                                listOf("discordUser", "oldName", "newName"),
                                listOf(e.member?.user?.globalName, whitelist[1], name) as List<String>
                            )
                        )

                        channel.sendMessageEmbeds(AccountChange().embed(whitelist[1], e.message.contentRaw).build())
                            .complete().delete().queueAfter(5, TimeUnit.SECONDS)

                        Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                            player.isWhitelisted = true
                        })

                        ImportWhitelist.whitelistManager.add("$userID,$name,${player.uniqueId}")
                        DiscordConfig().changeValue(userID, "$name ${player.uniqueId}")
                    } catch (_: HierarchyException) {
                        channel.sendMessageEmbeds(
                            RoleHierarchyError().embed(
                                DiscordConfig.config["whitelistFeature.role"].toString(),
                                e.guild
                            ).build()
                        ).complete().delete().queueAfter(15, TimeUnit.SECONDS)
                    }
                } else {
                    try {
                        var role = DiscordConfig.config["whitelistFeature.role"] as String
                        role = role.replace(" ", "")
                        val roles = role.split(",")

                        for (r in roles) {
                            try {
                                val guildRole = e.guild.getRoleById(r)
                                e.member?.let { guildRole?.let { it1 -> e.guild.addRoleToMember(it, it1) } }?.queue()
                            } catch (_: NumberFormatException) {
                            }
                        }

                        YVtils.instance.server.consoleSender.sendMessage(
                            Placeholder().replacer(
                                Language().getMessage(LangStrings.MODULE_DISCORD_REGISTERED_NAME_ADD),
                                listOf("discordUser", "name"),
                                listOf(e.member?.user?.globalName, name) as List<String>
                            )
                        )
                        channel.sendMessageEmbeds(AccountAdded().embed(e.message.contentRaw).build()).complete()
                            .delete().queueAfter(5, TimeUnit.SECONDS)
                        Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                            player.isWhitelisted = true
                        })
                        ImportWhitelist.whitelistManager.add("$userID,$name,${player.uniqueId}")
                        DiscordConfig().changeValue(userID, "$name ${player.uniqueId}")
                    } catch (_: HierarchyException) {
                        channel.sendMessageEmbeds(
                            RoleHierarchyError().embed(
                                DiscordConfig.config["whitelistFeature.role"].toString(),
                                e.guild
                            ).build()
                        ).complete().delete().queueAfter(15, TimeUnit.SECONDS)
                    }
                }
            } else if (statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                YVtils.instance.server.consoleSender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.MODULE_DISCORD_REGISTERED_NAME_WRONG),
                        listOf("discordUser", "name"),
                        listOf(e.member?.user?.globalName, name) as List<String>
                    )
                )
                channel.sendMessageEmbeds(AccountNotFound().embed(e.message.contentRaw).build()).complete().delete()
                    .queueAfter(15, TimeUnit.SECONDS)
            } else {
                YVtils.instance.server.consoleSender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(LangStrings.MODULE_DISCORD_REGISTERED_NAME_SERVERERROR_CHECK_INPUT),
                        listOf("discordUser", "name"),
                        listOf(e.member?.user?.globalName, name) as List<String>
                    )
                )
                channel.sendMessageEmbeds(AccountCheckError().embed(e.message.contentRaw).build()).complete().delete()
                    .queueAfter(15, TimeUnit.SECONDS)
            }
        }
    }

    private fun whitelistRemove(dc: String, mc: String, uuid: String) {
        ImportWhitelist.whitelistManager.remove("$dc,$mc,$uuid")
    }
}