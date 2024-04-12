package yv.tils.smp.mods.server.connect

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.logger.Debugger
import yv.tils.smp.utils.updater.PluginVersion

class PlayerJoin {

    private var state = 1

    fun eventReceiver(e: PlayerJoinEvent) {
        Debugger().log(
            "PlayerJoin - Event Receiver",
            "Player ${e.player.name} joined the server",
            "yv.tils.smp.mods.server.connect.PlayerJoin.eventReceiver()"
        )
        funcStarter(state, e)
    }

    private fun funcStarter(state: Int, e: PlayerJoinEvent) {
        when (state) {
            1 -> vanishJoin(e, e.player)
            2 -> sendJoinMessage(e, e.player)
            3 -> setupPlayer(e, e.player)
            4 -> otherActions(e, e.player)
        }
    }


    private fun vanishJoin(e: PlayerJoinEvent, player: Player) {
        if (Vanish.vanish.containsKey(player.uniqueId) && Vanish.vanish[player.uniqueId]!!) {
            e.joinMessage(null)
            setupPlayer(e, player)
            state = -1
        } else {
            funcStarter(state++, e)
        }
    }

    private fun sendJoinMessage(e: PlayerJoinEvent, player: Player) {
        e.joinMessage(generateJoinMessage(player))
        funcStarter(state++, e)
    }

    fun generateJoinMessage(player: Player): Component {
        val messages = Config.config["joinMessages"] as List<String>

        val random = messages.indices.random()

        Debugger().log(
            "PlayerQuit - Generate Join Message",
            "Generated Following Join Message: ${messages[random]}",
            "yv.tils.smp.mods.server.connect.PlayerJoin.generateJoinMessage()"
        )

        return Placeholder().replacer(
            Component.text(messages[random]),
            listOf("player"),
            listOf(player.name)
        )
    }

    private fun setupPlayer(e: PlayerJoinEvent, player: Player) {
        Debugger().log(
            "PlayerJoin - Setup Player",
            "Player ${player.name} joined the server",
            "yv.tils.smp.mods.server.connect.PlayerJoin.setupPlayer()"
        )

        Language.playerLang[player.uniqueId] = player.locale()

        funcStarter(state++, e)
    }

    private fun otherActions(e: PlayerJoinEvent, player: Player) {
        PluginVersion().onPlayerJoin(player)

        funcStarter(state++, e)
    }
}
