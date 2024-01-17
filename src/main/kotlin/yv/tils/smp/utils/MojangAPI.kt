package yv.tils.smp.utils

class MojangAPI {
    fun name2uuid(playerName: String): UUID {
        try {
            val url = "https://api.mojang.com/users/profiles/minecraft/${playerName}"
            val connection = new URL(url).openConnection()
            connection.setRequestMethod("GET")

            val responseCode = connection.getResponseCode()
        }
        catch(Exception e) {
            
        }
        return null
    }
}