package yv.tils.smp.mods.server.connect

import com.destroystokyo.paper.profile.PlayerProfile
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.logger.Debugger
import yv.tils.smp.utils.updater.PluginVersion
import java.util.Locale

class PlayerJoin {

    private var state = 1

    fun eventReceiver(e: PlayerJoinEvent) {
        println("PlayerJoin - Event Receiver")

        funcStarter(state, e)
    }

    private fun funcStarter(state: Int, e: PlayerJoinEvent) {
        when (state) {
            1 -> vanishJoin(e, e.player)
            2 -> generateJoinMessage(e, e.player)
            3 -> setupPlayer(e, e.player)
            4 -> otherActions(e, e.player)
        }
    }


    private fun vanishJoin(e: PlayerJoinEvent, player: Player, ) {
        //TODO: Add vanish check
        if (false) {
            e.joinMessage(null)
            setupPlayer(e, player)
        } else {
            funcStarter(state++, e)
        }
    }

    private fun generateJoinMessage(e: PlayerJoinEvent, player: Player, ) {
        //TODO: Add random generator
        Debugger().log("PlayerJoin - Generate Join Message", "Player ${player.name} joined the server", "yv.tils.smp.mods.server.connect.PlayerJoin.generateJoinMessage()")
        e.joinMessage()
        funcStarter(state++, e)
    }

    private fun setupPlayer(e: PlayerJoinEvent, player: Player, ) {
        Debugger().log("PlayerJoin - Setup Player", "Player ${player.name} joined the server", "yv.tils.smp.mods.server.connect.PlayerJoin.setupPlayer()")

        println(player.locale())

        Language.playerLang.plus(player.uniqueId to player.locale())

        println(Language.playerLang)

        funcStarter(state++, e)
    }

    private fun otherActions(e: PlayerJoinEvent, player: Player, ) {
        PluginVersion().onPlayerJoin(player)

        funcStarter(state++, e)
    }
}
