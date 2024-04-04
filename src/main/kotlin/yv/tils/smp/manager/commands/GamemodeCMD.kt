package yv.tils.smp.manager.commands

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer

class GamemodeCMD {
    val command = commandTree("gm") {
        withPermission("yvtils.smp.command.gamemode")
        withUsage("gm <gamemode> [player]")
        withAliases("gamemode")

        stringArgument("gamemode", false) {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "survival",
                    "creative",
                    "adventure",
                    "spectator",
                    "0",
                    "1",
                    "2",
                    "3"
                )
            )
            playerArgument("player", true) {
                anyExecutor { sender, args ->

                    if (sender !is Player && args[1] == null) {
                        sender.sendMessage(Language().getMessage(LangStrings.PLAYER_ARGUMENT_MISSING))
                        return@anyExecutor
                    }

                    if (args[1] is Player) {
                        val target = args[1] as Player
                        gamemodeSwitch(target, args[0].toString(), sender)
                    } else {
                        gamemodeSwitch(sender as Player, args[0].toString())
                    }
                }
            }
        }
    }

    private fun gamemodeSwitch(player: Player, gamemode: String, sender: CommandSender = player) {
        var list_en: List<String> = listOf()
        var list_de: List<String> = listOf()

        when (gamemode) {
            "survival", "0" -> {
                list_en = listOf("Survival")
                list_de = listOf("Überleben")

                player.gameMode = GameMode.SURVIVAL
            }

            "creative", "1" -> {
                list_en = listOf("Creative")
                list_de = listOf("Kreativ")

                player.gameMode = GameMode.CREATIVE
            }

            "adventure", "2" -> {
                list_en = listOf("Adventure")
                list_de = listOf("Abenteuer")

                player.gameMode = GameMode.ADVENTURE
            }

            "spectator", "3" -> {
                list_en = listOf("Spectator")
                list_de = listOf("Beobachter")

                player.gameMode = GameMode.SPECTATOR
            }

            else -> {
                player.sendMessage(
                    Language().getMessage(LangStrings.COMMAND_USAGE).append(
                        ColorUtils().convert("<blue>/gm <survival/creative/adventure/spectator> [player]")
                    )
                )

                return
            }
        }

        player.playSound(player.location, Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 15f, 15f)

        val list: List<String> = if (Language.playerLang[player.uniqueId]?.equals("en") == true) {
            list_en
        } else if (Language.playerLang[player.uniqueId]?.equals("de") == true) {
            list_de
        } else {
            list_en
        }

        player.sendMessage(
            StringReplacer().listReplacer(
                Language().getMessage(player.uniqueId, LangStrings.GAMEMODE_SWITCH),
                listOf("gamemode"),
                list
            )
        )


        val list2: List<String> = if (Language.playerLang[player.uniqueId]?.equals("en") == true) {
            list_en
        } else if (Language.playerLang[player.uniqueId]?.equals("de") == true) {
            list_de
        } else {
            list_en
        }

        if (player != sender) {
            if (sender is Player) {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(sender.uniqueId, LangStrings.GAMEMODE_SWITCH_OTHER),
                        listOf("gamemode", "player"),
                        listOf(list2[0], player.name)
                    )
                )
            } else {
                sender.sendMessage(
                    StringReplacer().listReplacer(
                        Language().getMessage(LangStrings.GAMEMODE_SWITCH_OTHER),
                        listOf("gamemode", "player"),
                        listOf(list2[0], player.name)
                    )
                )
            }
        }
    }
}