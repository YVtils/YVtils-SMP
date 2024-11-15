package yv.tils.smp.manager.commands.register

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Vars

class SeedCMD {
    val command = commandTree("seed") {
        withPermission("yvtils.smp.command.seed")
        withUsage("seed show")

        literalArgument("show", false) {
            anyExecutor { sender, _ ->
                val seedMap: MutableMap<Long, String> = mutableMapOf()

                for (world in Bukkit.getWorlds()) {
                    if (world.seed !in seedMap.keys) {
                        seedMap[world.seed] = world.name
                    } else {
                        seedMap[world.seed] = seedMap[world.seed] + ", " + world.name
                    }
                }

                if (seedMap.keys.size == 1) {
                    sender.sendMessage(
                        ColorUtils().convert(
                            Vars().prefix + " Seed: <gray>[<green><click:copy_to_clipboard:${seedMap.keys.first()}><hover:show_text:'${
                                Language().getRawMessage(
                                    sender,
                                    LangStrings.COMMAND_REPLACE_COPY_COMMAND_TO_CLIPBOARD
                                )
                            }'>${seedMap.keys.first()}</click><gray>]"
                        )
                    )
                } else {
                    val seedList: MutableList<String> = mutableListOf()

                    for (seed in seedMap.keys) {
                        seedList.add(
                            "<white>${seedMap[seed]}: <gray>[<green><click:copy_to_clipboard:$seed><hover:show_text:'${
                                Language().getRawMessage(
                                    sender,
                                    LangStrings.COMMAND_REPLACE_COPY_COMMAND_TO_CLIPBOARD
                                )
                            }'>$seed</click><gray>]"
                        )
                    }

                    sender.sendMessage(
                        ColorUtils().convert(
                            Vars().prefix + " Seeds:<newline>"
                        ).append(
                            ColorUtils().convert(
                                seedList.joinToString("<newline>")
                            )
                        )
                    )
                }
            }
        }
    }
}