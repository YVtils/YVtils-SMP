package yv.tils.smp.mods.questSystem.quests.lightBlock

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import yv.tils.smp.utils.color.ColorUtils

class lightBlock {
    private fun outputItems(): MutableList<ItemStack> {
        val item = ItemStack(Material.LIGHT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf<Component>()
        itemMeta.displayName(ColorUtils().convert("<yellow>Light Block"))
        itemMeta.addEnchant(Enchantment.MENDING, 1, true)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        lore.add(ColorUtils().convert("Invisibility I"))

        itemMeta.lore(lore)
        item.setItemMeta(itemMeta)

        return mutableListOf(item)
    }

    private fun inputItems(): MutableList<ItemStack> {
        val itemFrame = ItemStack(Material.LANTERN, 1)
        val frameMeta = itemFrame.itemMeta
        frameMeta.displayName(ColorUtils().convert("<yellow>Lantern <gray>or <yellow>Soul Lantern"))
        itemFrame.setItemMeta(frameMeta)

        val glassPane = ItemStack(Material.GLASS_PANE, 4)
        val glassMeta = glassPane.itemMeta
        glassMeta.displayName(ColorUtils().convert("<yellow>Any Glass Pane"))
        glassPane.setItemMeta(glassMeta)

        return mutableListOf(itemFrame, glassPane)
    }

    fun checkInventory(player: Player): Boolean {
        val inventory = player.inventory.contents
        val items = inputItems()

        var foundCount = 0

        for (invContent in inventory) {
            if (invContent == null) continue
            if (invContent.type == Material.AIR) continue

            for (item in items) {
                if (invContent.type == item.type) {
                    if (item.amount > 64 && invContent.amount >= 64) {
                        item.amount -= 64
                    }else if (invContent.amount >= item.amount) {
                        foundCount++
                        items.remove(item)
                        break
                    }
                }
            }

            if (foundCount == items.size) {
                return true
            }
        }

        return false
    }

    fun configFile(ymlFile: YamlConfiguration): YamlConfiguration {
        ymlFile.addDefault("enabled", true)
        ymlFile.addDefault("name", "Light Block")
        ymlFile.addDefault("DisplayItem", "LIGHT")
        ymlFile.addDefault("description", "Craft an <gold>invisible <white>Light Source")
        ymlFile.options().copyDefaults(true)

        return ymlFile
    }
}