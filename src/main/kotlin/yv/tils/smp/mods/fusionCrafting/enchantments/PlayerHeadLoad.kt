package yv.tils.smp.mods.fusionCrafting.enchantments

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.utils.color.ColorUtils

// TODO: Add support for players that never joined the server
class PlayerHeadLoad {
    fun loadPlayerHead(nameTag: ItemStack, outPut: ItemStack, meta: ItemMeta): SkullMeta {
        var playerName = ColorUtils().strip(nameTag.displayName())
        playerName = playerName.replace("[", "")
        playerName = playerName.replace("]", "")

        val playerHead = ItemStack(Material.PLAYER_HEAD)

        playerHead.itemMeta = outPut.itemMeta

        val skullMeta = playerHead.itemMeta as SkullMeta
        skullMeta.displayName(ColorUtils().convert("<gold>${playerName}'s Head"))
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName))
        skullMeta.persistentDataContainer.remove(FusionKeys.FUSION_PLAYER_HEAD.key)

        return skullMeta
    }
}