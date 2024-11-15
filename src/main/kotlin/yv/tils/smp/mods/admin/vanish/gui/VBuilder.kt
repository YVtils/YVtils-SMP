package yv.tils.smp.mods.admin.vanish.gui

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import yv.tils.smp.mods.admin.vanish.Vanish
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.inventory.GUIFiller

class VBuilder {
    fun openGUI(sender: Player, target: Player) {
        val inv = buildGUI(Vanish.vanish[target.uniqueId]!!, sender)
        sender.openInventory(inv)
    }

    private fun buildGUI(vData: Vanish.VanishedPlayer, sender: Player): Inventory {
        val guiMap = mutableMapOf<Int, ItemStack>()

        guiMap.putAll(toggleSetting(vData, sender))
        guiMap.putAll(layerSetting(vData, sender))
        guiMap.putAll(itemPickupSetting(vData, sender))
        guiMap.putAll(invInteractionSetting(vData, sender))
        guiMap.putAll(mobTargetSetting(vData, sender))

        var inv = Bukkit.createInventory(sender, 36, ColorUtils().convert("<#6D8896>Vanish"))

        for ((slot, item) in guiMap) {
            inv.setItem(slot, item)
        }

        inv = GUIFiller().fillInventory(inv)

        return inv
    }

    /**
     * Item for toggling vanish
     * @return MutableMap of ItemStack {slot, item}
     */
    fun toggleSetting(vData: Vanish.VanishedPlayer, sender: Player): MutableMap<Int, ItemStack> {
        val items = mutableMapOf<Int, ItemStack>()

        val uuid = sender.uniqueId

        val vanish = ItemStack(Material.POTION)
        val vanishMeta = vanish.itemMeta as PotionMeta
        val vanishLore = mutableListOf<Component>()
        vanishMeta.displayName(ColorUtils().convert("<#96C8FF>Vanish"))
        vanishMeta.color = Color.fromRGB(246, 246, 246)
        vanishMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        vanishLore.addAll(ColorUtils().handleLore(Language().getMessage(uuid, LangStrings.MODULE_VANISH_VANISH_LORE)))
        vanishMeta.lore(vanishLore)
        vanish.itemMeta = vanishMeta

        items[10] = vanish
        items[19] = toggleItem(sender, vData.vanish)

        return items
    }

    /**
     * Item for setting layer
     * @return MutableMap of ItemStack {slot, item}
     */
    fun layerSetting(vData: Vanish.VanishedPlayer, sender: Player): MutableMap<Int, ItemStack> {
        val items = mutableMapOf<Int, ItemStack>()

        val uuid = sender.uniqueId
        val currentLayer = vData.layer

        val layer = ItemStack(Material.FILLED_MAP)
        val layerMeta = layer.itemMeta
        val layerLore = mutableListOf<Component>()
        layerMeta.displayName(ColorUtils().convert("<#96C8FF>Layer"))
        layerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        layerLore.addAll(ColorUtils().handleLore(Language().getMessage(uuid, LangStrings.MODULE_VANISH_LAYER_LORE)))
        layerMeta.lore(layerLore)
        layer.itemMeta = layerMeta
        layer.amount = currentLayer

        items[12] = layer
        items[21] = toggleItem(sender, vData.layer != 4)

        return items
    }

    /**
     * Item for setting item pickup
     * @return MutableMap of ItemStack {slot, item}
     */
    fun itemPickupSetting(vData: Vanish.VanishedPlayer, sender: Player): MutableMap<Int, ItemStack> {
        val items = mutableMapOf<Int, ItemStack>()

        val uuid = sender.uniqueId

        val itemPickup = ItemStack(Material.HOPPER)
        val itemPickupMeta = itemPickup.itemMeta
        val itemPickupLore = mutableListOf<Component>()
        itemPickupMeta.displayName(ColorUtils().convert("<#96C8FF>Item Pickup"))
        itemPickupMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        itemPickupLore.addAll(ColorUtils().handleLore(Language().getMessage(uuid, LangStrings.MODULE_VANISH_ITEM_PICKUP_LORE)))
        itemPickupMeta.lore(itemPickupLore)
        itemPickup.itemMeta = itemPickupMeta

        items[14] = itemPickup
        items[23] = toggleItem(sender, vData.itemPickup)

        return items
    }

    /**
     * Item for setting inventory interaction
     * @return MutableMap of ItemStack {slot, item}
     */
    fun invInteractionSetting(vData: Vanish.VanishedPlayer, sender: Player): MutableMap<Int, ItemStack> {
        val items = mutableMapOf<Int, ItemStack>()

        val uuid = sender.uniqueId

        val invInteraction = ItemStack(Material.LIGHT_GRAY_SHULKER_BOX)
        val invInteractionMeta = invInteraction.itemMeta
        val invInteractionLore = mutableListOf<Component>()
        invInteractionMeta.displayName(ColorUtils().convert("<#96C8FF>Silent Inventory Interaction"))
        invInteractionMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        invInteractionLore.addAll(ColorUtils().handleLore(Language().getMessage(uuid, LangStrings.MODULE_VANISH_INV_INTERACTION_LORE)))
        invInteractionMeta.lore(invInteractionLore)
        invInteraction.itemMeta = invInteractionMeta

        items[15] = invInteraction
        items[24] = toggleItem(sender, vData.invInteraction)


        return items
    }

    /**
     * Item for setting mob target
     * @return MutableMap of ItemStack {slot, item}
     */
    fun mobTargetSetting(vData: Vanish.VanishedPlayer, sender: Player): MutableMap<Int, ItemStack> {
        val items = mutableMapOf<Int, ItemStack>()

        val uuid = sender.uniqueId

        val mobTarget = ItemStack(Material.SPAWNER)
        val mobTargetMeta = mobTarget.itemMeta
        val mobTargetLore = mutableListOf<Component>()
        mobTargetMeta.displayName(ColorUtils().convert("<#96C8FF>Anti Mob Target"))
        mobTargetMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        mobTargetLore.addAll(ColorUtils().handleLore(Language().getMessage(uuid, LangStrings.MODULE_VANISH_MOB_TARGET_LORE)))
        mobTargetMeta.lore(mobTargetLore)
        mobTarget.itemMeta = mobTargetMeta

        items[16] = mobTarget
        items[25] = toggleItem(sender, vData.mobTarget)

        return items
    }

    private fun toggleItem(sender: Player, state: Boolean): ItemStack {
        val toggle = ItemStack(if (state) Material.LIME_DYE else Material.RED_DYE)
        val toggleMeta = toggle.itemMeta
        toggleMeta.displayName(ColorUtils().convert(if (state) "<#96C8FF>[${Language().getRawMessage(sender.uniqueId, LangStrings.ENABLED)}]" else "<#96C8FF>[${Language().getRawMessage(sender.uniqueId, LangStrings.DISABLED)}"))
        toggleMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        toggle.itemMeta = toggleMeta

        return toggle
    }
}