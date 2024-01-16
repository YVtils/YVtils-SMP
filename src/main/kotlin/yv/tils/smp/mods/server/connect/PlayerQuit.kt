package yv.tils.smp.mods.server.connect

import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit {

    private var state = 1
    private var e: PlayerQuitEvent? = null
    private var player = e?.player

    fun eventReceiver(event: PlayerQuitEvent) {
        e = event
        player = event.player

        while (state != 0) {
            state = when (state) {
                1 -> vanishQuit()
                2 -> generateQuitMessage()
                else -> 0
            }
        }
    }

    private fun vanishQuit(): Int {
        return if (true) {
            e?.quitMessage(null)
            0
        } else {
            state++
        }
    }

    private fun generateQuitMessage(): Int {
        e?.quitMessage()
        return state++
    }
}