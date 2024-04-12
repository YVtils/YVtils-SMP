package yv.tils.smp.mods.status

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.entity.Player
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.configs.status.StatusConfig
import yv.tils.smp.utils.internalAPI.Placeholder

class StatusCommand {
    val command = commandTree("status") {
        withPermission("yvtils.smp.command.status")
        withUsage("status <set/default/clear> [status/player]")
        withAliases("prefix", "role")

        literalArgument("set", false) {
            greedyStringArgument("status", false) {
                playerExecutor { player, args ->
                    setStatus(player, args[0] as String)
                }
            }
        }

        literalArgument("default", false) {
            greedyStringArgument("status", false) {
                val suggestions = generateDefaultStatus()
                replaceSuggestions(ArgumentSuggestions.strings(suggestions))
                playerExecutor { player, args ->
                    setDefaultStatus(player, args[0] as String, suggestions)
                }
            }
        }

        literalArgument("clear", false) {
            playerArgument("player", true) {
                anyExecutor { sender, args ->
                    if (args[0] is Player) {
                        clearStatus(sender as Player, args[0] as Player)
                    } else {
                        clearStatus(sender as Player)
                    }
                }
            }
        }
    }

    private fun setStatus(player: Player, status: String) {
        val maxLength = StatusConfig.config["maxLength"] as Int

        if (ColorUtils().strip(status).length > maxLength) {
            player.sendMessage(Language().getMessage(LangStrings.MODULE_STATUS_CUSTOM_STATUS_TOO_LONG))
            return
        }

        if (setStatusDisplay(player, status)) {
            val display = StatusConfig.config["display"] as String

            val displayCompo = Placeholder().replacer(
                ColorUtils().convert(display),
                listOf("status", "playerName"),
                listOf(status, player.name)
            )

            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.MODULE_STATUS_SET),
                    listOf("status"),
                    listOf(ColorUtils().convert(displayCompo))
                )
            )
        }
    }

    private fun setDefaultStatus(player: Player, status: String, suggestions: Collection<String>) {
        if (!suggestions.contains(status)) {
            player.sendMessage(Language().getMessage(LangStrings.MODULE_STATUS_NO_DEFAULT_STATUS))
            return
        }

        if (setStatusDisplay(player, status)) {
            val display = StatusConfig.config["display"] as String

            val displayCompo = Placeholder().replacer(
                ColorUtils().convert(display),
                listOf("status", "playerName"),
                listOf(status, player.name)
            )

            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.MODULE_STATUS_SET),
                    listOf("status"),
                    listOf(ColorUtils().convert(displayCompo))
                )
            )
        }
    }

    private fun clearStatus(player: Player, target: Player? = null) {
        if (target == null) {
            setStatusDisplay(player, "")
            player.sendMessage(Language().getMessage(LangStrings.MODULE_STATUS_CLEAR_CLEARED))
        } else {
            if (!player.hasPermission("yvtils.smp.command.status.clear.others")) {
                player.sendMessage(Language().getMessage(LangStrings.MODULE_STATUS_CLEAR_OTHER_UNALLOWED))
                return
            }

            setStatusDisplay(target, "")

            player.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(LangStrings.MODULE_STATUS_CLEAR_OTHER_CLEARED),
                    listOf("player"),
                    listOf(target.name)
                )
            )
            target.sendMessage(Language().getMessage(LangStrings.MODULE_STATUS_CLEAR_CLEARED))
        }
    }

    fun setStatusDisplay(player: Player, status: String): Boolean {
        if (status == "") {
            player.displayName(ColorUtils().convert(player.name))
            player.playerListName(ColorUtils().convert(player.name))
            StatusTeamManager().removePlayer(player)
            StatusConfig().changeValue(player.uniqueId.toString(), "")
            return false
        }

        if (checkBlacklist(status)) {
            player.sendMessage(Language().getMessage(LangStrings.MODULE_STATUS_BLACKLISTED_STATUS))
            player.displayName(ColorUtils().convert(player.name))
            player.playerListName(ColorUtils().convert(player.name))
            StatusTeamManager().removePlayer(player)
            StatusConfig().changeValue(player.uniqueId.toString(), "")
            return false
        }

        val display = StatusConfig.config["display"] as String

        val displayCompo = Placeholder().replacer(
            ColorUtils().convert(display),
            listOf("status", "playerName"),
            listOf(status, player.name)
        )

        val displayCompoNameTag = Placeholder().replacer(
            ColorUtils().convert(display),
            listOf("status", "playerName"),
            listOf(status, "")
        )

        player.displayName(displayCompo)
        player.playerListName(displayCompo)
        StatusTeamManager().addPlayer(player, displayCompoNameTag)

        StatusConfig().changeValue(player.uniqueId.toString(), status)

        return true
    }

    private fun generateDefaultStatus(): Collection<String> {
        return StatusConfig.config["defaultStatus"] as List<String>
    }

    private fun checkBlacklist(status: String): Boolean {
        val blacklist = StatusConfig.config["blacklist"] as List<String>
        return blacklist.contains(status)
    }
}