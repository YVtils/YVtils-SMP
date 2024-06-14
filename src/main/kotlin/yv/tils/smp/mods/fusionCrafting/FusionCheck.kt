package yv.tils.smp.mods.fusionCrafting

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import yv.tils.smp.utils.color.ColorUtils

class FusionCheck {
    companion object {
        val fusionItems = mutableListOf<ItemStack>()
        var canAccept = false
        var correctItems = 0
    }

    fun buildItemList(fusion: MutableMap<String, Any>, inv: Inventory, player: Player) {
        val items = mutableListOf<ItemStack>()

        var mapKey = ""

        for (input in fusion) {
            if (input.key.startsWith("input.")) {
                if (mapKey == "") mapKey = input.key.split(".")[0] + "." + input.key.split(".")[1]
                mapKey = addItem(input, items, mapKey, inv, player)
            } else {
                playerCheck(player, inv, items, mapKey)
                mapKey = ""
                items.clear()
            }
        }

        if (correctItems == fusion.size) {
            canAccept = true
        }
    }

    private fun addItem(
        input: MutableMap.MutableEntry<String, Any>,
        items: MutableList<ItemStack>,
        mapKey: String,
        inv: Inventory,
        player: Player,
    ): String {
        var newMapKey = mapKey
        if (input.key.startsWith(mapKey)) {
            val dataList = input.value as MutableList<MutableMap<String, Any>>
            val item = ItemStack(Material.valueOf((dataList[0]["item"] as String).uppercase()))
            val data = dataList[2]["data"] as String

            item.amount = (input.value as MutableList<MutableMap<String, String>>)[1]["amount"]?.toInt() ?: 1
            // TODO: Implement data logic. Example: "Data1; Data2; Data3; ..." | ""

            items.add(item)
        } else {
            playerCheck(player, inv, items, mapKey)
            newMapKey = input.key.split(".")[0] + "." + input.key.split(".")[1]
            items.clear()

            addItem(input, items, newMapKey, inv, player)
        }

        return newMapKey
    }

    private fun playerCheck(player: Player, inv: Inventory, items: MutableList<ItemStack>, mapKey: String) {
        val inputSlots: List<Int> = listOf(10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31)

        if (compareInv(player.inventory, items)) {
            for (slot in inputSlots) {
                if (inv.getItem(slot)?.itemMeta?.displayName()
                        ?.let { ColorUtils().convert(it) } == "<red>✘<gray> | <aqua>${mapKey.split(".")[1]} <gray>(${items[0].amount}x)"
                ) {
                    val item = inv.getItem(slot) ?: return
                    val meta = item.itemMeta
                    meta.addEnchant(Enchantment.UNBREAKING, 1, true)
                    meta.displayName(ColorUtils().convert("<green>✔<gray> | <aqua>${mapKey.split(".")[1]} <gray>(${items[0].amount}x)"))
                    item.itemMeta = meta

                    inv.setItem(slot, item)
                }
            }
        }
    }

    private fun compareInv(inv: Inventory, items: MutableList<ItemStack>): Boolean {
        for (item in items) {
            if (inv.containsAtLeast(item, item.amount)) {
                fusionItems.add(item)
                correctItems++
                return true
            }
        }
        return false
    }
}