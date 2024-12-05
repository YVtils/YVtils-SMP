package yv.tils.smp.mods.discord.whitelist

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.exceptions.HierarchyException
import org.bukkit.Bukkit
import yv.tils.smp.YVtils
import yv.tils.smp.mods.discord.embedManager.whitelist.*
import yv.tils.smp.mods.discord.embedManager.whitelist.discord.ForceAdd
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.configs.discord.DiscordConfig
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.net.HttpURLConnection
import java.net.URI

class ForceAdd {
    fun onMessageReceived(mc: String, dc: Member?, exec: Member, guild: Guild): EmbedBuilder {
        val playerUUID = MojangAPI().name2uuid(mc) ?: return AccountCheckError().embed(mc)
        val player = Bukkit.getOfflinePlayer(playerUUID)
        val userID = dc?.user?.id ?: "~$mc"
        val userName = dc?.user?.name ?: "~$mc"

        if (!mc.matches(Regex("[a-zA-Z0-9_]+"))) {
            return AccountCanNotExist().embed(mc)
        }

        if (player.isWhitelisted) {
            return AccountAlreadyListed().embed(mc)
        }

        runCatching {
            val url =
                URI("https://api.mojang.com/users/profiles/minecraft/$mc").toURL().openConnection() as HttpURLConnection
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

                    if (dc == null) {
                        Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                            player.isWhitelisted = true
                        })

                        ImportWhitelist.whitelistManager.add("$userID,$mc,${player.uniqueId}")
                        DiscordConfig().changeValue(userID, "$mc ${player.uniqueId}")
                    } else {
                        try {
                            var role = DiscordConfig.config["whitelistFeature.role"] as String
                            role = role.replace(" ", "")
                            val roles = role.split(",")

                            for (r in roles) {
                                try {
                                    val role = guild.getRoleById(r)
                                    dc.let { role?.let { it1 -> guild.addRoleToMember(it, it1) } }?.queue()
                                } catch (_: NumberFormatException) {
                                }
                            }

                            Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                                player.isWhitelisted = true
                            })

                            ImportWhitelist.whitelistManager.add("$userID,$mc,${player.uniqueId}")
                            DiscordConfig().changeValue(userID, "$mc ${player.uniqueId}")
                        } catch (_: HierarchyException) {
                            return RoleHierarchyError().embed(
                                DiscordConfig.config["whitelistFeature.role"].toString(),
                                guild
                            )
                        }
                    }
                    YVtils.instance.server.consoleSender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(LangStrings.MODULE_DISCORD_CMD_REGISTERED_CHANGE),
                            listOf("discordUser", "dcName", "oldName", "newName"),
                            listOf(exec.user.globalName, userName, whitelist[1], mc) as List<String>
                        )
                    )
                    return ForceAdd().embedReplace(userName, whitelist[1], mc)
                } else {
                    if (dc == null) {
                        Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                            player.isWhitelisted = true
                        })

                        ImportWhitelist.whitelistManager.add("$userID,$mc,${player.uniqueId}")
                        DiscordConfig().changeValue(userID, "$mc ${player.uniqueId}")
                    } else {
                        try {
                            var role = DiscordConfig.config["whitelistFeature.role"] as String
                            role = role.replace(" ", "")
                            val roles = role.split(",")

                            for (r in roles) {
                                try {
                                    val role = guild.getRoleById(r)
                                    dc.let { role?.let { it1 -> guild.addRoleToMember(it, it1) } }?.queue()
                                } catch (_: NumberFormatException) {
                                }
                            }

                            Bukkit.getScheduler().runTask(YVtils.instance, Runnable {
                                player.isWhitelisted = true
                            })

                            ImportWhitelist.whitelistManager.add("$userID,$mc,${player.uniqueId}")
                            DiscordConfig().changeValue(userID, "$mc ${player.uniqueId}")
                        } catch (_: HierarchyException) {
                            return RoleHierarchyError().embed(
                                DiscordConfig.config["whitelistFeature.role"].toString(),
                                guild
                            )
                        }
                    }
                    YVtils.instance.server.consoleSender.sendMessage(
                        Placeholder().replacer(
                            Language().getMessage(LangStrings.MODULE_DISCORD_CMD_REGISTERED_ADD),
                            listOf("discordUser", "dcName", "mcName"),
                            listOf(exec.user.globalName, userName, mc) as List<String>
                        )
                    )
                    return ForceAdd().embed(mc, userName)
                }
            } else if (statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                return AccountNotFound().embed(mc)
            } else {
                return AccountCheckError().embed(mc)
            }
        }
        return null!!
    }

    private fun whitelistRemove(dc: String, mc: String, uuid: String) {
        ImportWhitelist.whitelistManager.remove("$dc,$mc,$uuid")
    }
}