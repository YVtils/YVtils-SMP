package yv.tils.smp.mods.server.maintenance

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerLoginEvent
import yv.tils.smp.utils.configs.global.Config
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import yv.tils.smp.utils.internalAPI.Vars

class PlayerLogin {

    private var state = 1

    fun eventReceiver(e: PlayerLoginEvent) {
        if (Config.config["modules.server"] as Boolean) {
            funcStarter(state, e)
        }
    }

    private fun funcStarter(state: Int, e: PlayerLoginEvent) {
        when (state) {
            1 -> checkPerm(e, e.player)
        }
    }

    private fun checkPerm(e: PlayerLoginEvent, player: Player) {
        if (MaintenanceHandler.maintenance && !player.hasPermission("yvtils.smp.bypass.maintenance")) {
            e.result = PlayerLoginEvent.Result.KICK_FULL
            player.kick(
                Placeholder().replacer(
                    Language().getMessage(
                        player.uniqueId,
                        LangStrings.MAINTENANCE_PLAYER_NOT_ALLOWED_TO_JOIN_KICK_MESSAGE
                    ),
                    listOf("prefix"),
                    listOf(Vars().prefix)
                ),
                PlayerKickEvent.Cause.PLUGIN
            )
        } else {
            funcStarter(state++, e)
        }
    }
}