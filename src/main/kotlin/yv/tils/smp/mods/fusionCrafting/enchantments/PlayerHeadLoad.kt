package yv.tils.smp.mods.fusionCrafting.enchantments

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import yv.tils.smp.YVtils
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.utils.MojangAPI
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.internalAPI.Placeholder
import java.net.URL

class PlayerHeadLoad {
    fun loadPlayerHead(nameTag: ItemStack, outPut: ItemStack): SkullMeta {
        var playerName = ColorUtils().strip(nameTag.displayName())
        playerName = playerName.replace("[", "").replace("]", "")

        val playerHead = getHead(playerName)

        if (playerHead != null) {
            val meta = playerHead.itemMeta as SkullMeta
            meta.displayName(Placeholder().replacer(Language().getMessage(LangStrings.MODULE_FUSION_PLAYER_HEAD_DISPLAY_NAME), mapOf("player" to playerName)))
            meta.lore(outPut.itemMeta.lore())
            meta.persistentDataContainer.remove(FusionKeys.FUSION_PLAYER_HEAD.key)
            outPut.itemMeta = meta
            return meta
        } else {
            YVtils.instance.logger.warning("Failed to load player head for $playerName")
            val meta = outPut.itemMeta as SkullMeta
            meta.displayName(Language().getMessage(LangStrings.MODULE_FUSION_LOAD_PLAYER_HEAD_FAILED))
            meta.lore(outPut.itemMeta.lore())
            meta.persistentDataContainer.remove(FusionKeys.FUSION_PLAYER_HEAD.key)
            outPut.itemMeta = meta
            return meta
        }
    }

    private fun getHead(name: String): ItemStack? {
        val playerHead = ItemStack(Material.PLAYER_HEAD)
        val skullMeta = playerHead.itemMeta as SkullMeta

        val onlinePlayer = Bukkit.getPlayerExact(name)
        if (onlinePlayer != null) {
            skullMeta.owningPlayer = onlinePlayer
            playerHead.itemMeta = skullMeta
            return playerHead
        }

        val playerUUID = MojangAPI().name2uuid(name)
        if (playerUUID != null) {
            val skinURL = MojangAPI().getSkinTextures(uuid = playerUUID)
            if (skinURL != null) {
                val profile = Bukkit.createProfile(playerUUID, name)
                val textures = profile.textures
                textures.skin = URL(skinURL)
                profile.setTextures(textures)
                skullMeta.playerProfile = profile
                playerHead.itemMeta = skullMeta
                return playerHead
            }
        }

        val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(name)
        if (offlinePlayer.hasPlayedBefore()) {
            skullMeta.owningPlayer = offlinePlayer
            playerHead.itemMeta = skullMeta
            return playerHead
        }

        return null
    }
}
