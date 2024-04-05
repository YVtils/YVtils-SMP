package yv.tils.smp.mods.server.maintenance

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerLoginEvent
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language

class PlayerLogin {

    private var state = 1

    fun eventReceiver(e: PlayerLoginEvent) {
        funcStarter(state, e)
    }

    private fun funcStarter(state: Int, e: PlayerLoginEvent) {
        when (state) {
            1 -> checkPerm(e, e.player)
        }
    }

    private fun checkPerm(e: PlayerLoginEvent, player: Player) {
        if (MaintenanceCMD.maintenance && !player.hasPermission("yvtils.smp.bypass.maintenance")) {
            e.result = PlayerLoginEvent.Result.KICK_FULL
            e.kickMessage(Language().getMessage(LangStrings.MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE))
        } else {
            funcStarter(state++, e)
        }
    }
}