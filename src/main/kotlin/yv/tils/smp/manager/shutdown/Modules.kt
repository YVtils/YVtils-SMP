package yv.tils.smp.manager.shutdown

import yv.tils.smp.mods.discord.BotManager
import yv.tils.smp.mods.waypoints.WaypointPath

class Modules {
    fun shutdown() {
        WaypointPath().stopNavigations()
        BotManager().stopBot()
    }
}