package yv.tils.smp.manager.commands

import dev.jorel.commandapi.kotlindsl.*
import net.kyori.adventure.text.Component
import org.bukkit.GameMode
import org.bukkit.entity.Player
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.StringReplacer

class GamemodeCMD {
    val command = commandTree("gm") {
        withPermission("yvtils.smp.command.gamemode")
        withUsage("gm <gamemode> [player]")
        withAliases("gamemode")

        integerArgument("", 0, 3, false) {
            playerArgument("player", true) {
                playerExecutor { player, args ->
                    if (args[1] is Player) {
                        val target = args[1] as Player
                        gamemodeSwitch(target, args[0] as Int)
                    } else {
                        gamemodeSwitch(player, args[0] as Int)
                    }
                }
            }
        }
    }

    private fun gamemodeSwitch(player: Player, gamemode: Int) {
        var list_en: List<String> = listOf()
        var list_de: List<String> = listOf()

        when (gamemode) {
            0 -> {
                list_en = listOf("Survival")
                list_de = listOf("Überleben")

                player.gameMode = GameMode.SURVIVAL
            }
            1 -> {
                list_en = listOf("Creative")
                list_de = listOf("Kreativ")

                player.gameMode = GameMode.CREATIVE
            }
            2 -> {
                list_en = listOf("Adventure")
                list_de = listOf("Abenteuer")

                player.gameMode = GameMode.ADVENTURE
            }
            3 -> {
                list_en = listOf("Spectator")
                list_de = listOf("Beobachter")

                player.gameMode = GameMode.SPECTATOR
            }
        }

        val list: List<String> = if (Language.playerLang[player.uniqueId]?.equals("en") == true) {
            list_en
        } else if (Language.playerLang[player.uniqueId]?.equals("de") == true) {
            list_de
        } else {
            list_en
        }

        player.sendMessage(StringReplacer().listReplacer(
            Language().getMessage(player.uniqueId, LangStrings.GAMEMODE_SWITCH),
            listOf("gamemode"),
            list
        ))
    }
}