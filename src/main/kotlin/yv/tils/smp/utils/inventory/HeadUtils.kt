package yv.tils.smp.utils.inventory

import com.destroystokyo.paper.profile.ProfileProperty
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import yv.tils.smp.utils.color.ColorUtils
import java.util.*

class HeadUtils {
    fun createCustomHead(headTexture: CustomHeads, itemName: String): ItemStack {
        val item = ItemStack(Material.PLAYER_HEAD)
        val meta = item.itemMeta as SkullMeta
        val playerProfile = Bukkit.createProfile(UUID.randomUUID())


        playerProfile.setProperties(
            Collections.singletonList(
                ProfileProperty(
                    "textures",
                    headTexture.texture,
                    "")
            )
        )

        meta.playerProfile = playerProfile

        meta.displayName(ColorUtils().convert(itemName))
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        item.itemMeta = meta

        return item
    }
}