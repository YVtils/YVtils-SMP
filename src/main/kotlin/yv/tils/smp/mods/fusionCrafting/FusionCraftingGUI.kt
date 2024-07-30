package yv.tils.smp.mods.fusionCrafting

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.utils.color.ColorUtils

class FusionCraftingGUI {
    fun fusionGUI(player: HumanEntity, fusion: MutableMap<String, Any>) {
        var inv = Bukkit.createInventory(null, 54, ColorUtils().convert("<gold>Fusion Crafting - ${fusion["name"]}"))

        inv = generateFusionGUI(inv, fusion)

        player.openInventory(inv)

        FusionCheck().buildItemList(fusion, inv, player as Player)
    }

    fun generateFusionGUI(inv: Inventory, fusion: MutableMap<String, Any>): Inventory {
        val inputSlots: List<Int> = listOf(10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31)
        val outputSlots: List<Int> = listOf(15, 16, 24, 25, 33, 34)
        val acceptSlots = listOf(47, 48, 49, 50, 51, 52)
        val backSlot = 45

        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        back.itemMeta = backMeta
        inv.setItem(backSlot, back)

        val accept = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val acceptMeta = accept.itemMeta
        acceptMeta.displayName(ColorUtils().convert("<green>Accept Recipe"))
        acceptMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        accept.itemMeta = acceptMeta

        for (slot in acceptSlots) {
            inv.setItem(slot, accept)
        }

        generateInput(inputSlots, fusion, inv)
        generateOutput(fusion, inv)

        val filler = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = filler.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        filler.itemMeta = fillerMeta

        val innerFiller = ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
        innerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inputSlots.contains(i) || outputSlots.contains(i) || acceptSlots.contains(i) || i == backSlot) {
                if (inv.getItem(i) == null) {
                    inv.setItem(i, innerFiller)
                    continue
                }
                continue
            }

            inv.setItem(i, filler)
        }

        for (i in 0 until inv.size) {
            val item = inv.getItem(i) ?: continue
            item.itemMeta.persistentDataContainer.set(FusionKeys.FUSION_GUI.key, PersistentDataType.STRING, "fusion")
            inv.setItem(i, item)
        }

        return inv
    }

    private fun generateInput(slots: List<Int>, fusion: MutableMap<String, Any>, inv: Inventory) {
        val items = mutableListOf<ItemStack>()

        for (input in fusion) {
            if (input.key.startsWith("input.")) {
                val mapKey = input.key.split(".")[0] + "." + input.key.split(".")[1] + ".0"
                if (mapKey == input.key) {
                    for (i in 0 until (input.value as MutableList<*>).size) {
                        when (i) {
                            0 -> {
                                val item = ItemStack(
                                    Material.valueOf(
                                        (input.value as MutableList<MutableMap<String, String>>)[i]["item"]?.uppercase()
                                            ?: "BARRIER"
                                    )
                                )
                                items.add(item)
                            }

                            1 -> {
                                val item = items[items.size - 1]
                                item.amount =
                                    (input.value as MutableList<MutableMap<String, String>>)[i]["amount"]?.toInt() ?: 1
                            }

                            2 -> {
                                val item = items[items.size - 1]
                                val meta = item.itemMeta

                                if ((input.value as MutableList<MutableMap<String, *>>)[i]["data"] != "") {
                                    meta.lore(listOf(ColorUtils().convert("<gray>Item Data: " + (input.value as MutableList<MutableMap<String, *>>)[i]["data"])))
                                }

                                item.itemMeta = meta
                            }
                        }

                        val item = items[items.size - 1]
                        val meta = item.itemMeta
                        meta.displayName(ColorUtils().convert("<red>✘<gray> | <aqua>" + mapKey.split(".")[1] + " <gray>(" + (input.value as MutableList<MutableMap<String, String>>)[1]["amount"] + "x)"))
                        meta.persistentDataContainer.set(FusionKeys.FUSION_ITEMNAME.key, PersistentDataType.STRING, "<aqua>" + mapKey.split(".")[1])
                        item.itemMeta = meta
                    }
                }
            }
        }

        for (slot in slots) {
            if (items.isEmpty()) {
                break
            }

            val item = items[0]
            items.removeAt(0)

            inv.setItem(slot, item)
        }
    }

    private fun generateOutput(fusion: MutableMap<String, Any>, inv: Inventory) {
        val items = mutableListOf<ItemStack>()

        for (output in fusion) {
            if (output.key.startsWith("output.")) {
                val mapKey = output.key.split(".")[0] + "." + output.key.split(".")[1]
                if (mapKey == output.key) {
                    val item = ItemStack(
                        Material.valueOf(
                            (output.value as MutableList<MutableMap<String, String>>)[0]["item"]?.uppercase() ?: "DIRT"
                        )
                    )
                    item.amount = (output.value as MutableList<MutableMap<String, String>>)[1]["amount"]?.toInt() ?: 1
                    val meta = item.itemMeta
                    meta.displayName(ColorUtils().convert("<gold>" + (output.value as MutableList<MutableMap<String, String>>)[2]["name"] + " <gray>(" + (output.value as MutableList<MutableMap<String, String>>)[1]["amount"] + "x)"))
                    meta.persistentDataContainer.set(FusionKeys.FUSION_ITEMNAME.key, PersistentDataType.STRING, "<gold>" + (output.value as MutableList<MutableMap<String, String>>)[2]["name"])

                    val loreData = (output.value as MutableList<MutableMap<String, String>>)[3]["lore"]

                    val lore: MutableList<Component> = mutableListOf()

                    for (line in loreData!!.split("<newline>")) {
                        line.replace("<newline>", "")
                        lore.add(ColorUtils().convert(line))
                    }

                    if ((output.value as MutableList<MutableMap<String, String>>)[4]["data"] != "") {
                        lore.add(ColorUtils().convert(" "))
                        lore.add(ColorUtils().convert("<gray>Item Data: " + (output.value as MutableList<MutableMap<String, String>>)[4]["data"]))

                        for (singleData in (output.value as MutableList<MutableMap<String, String>>)[4]["data"]!!.split(";")) {
                            var data = singleData
                            if (data == "") continue
                            data = data.trim()
                            data = data.replace(" ", "_")

                            meta.persistentDataContainer.set(FusionKeys.valueOf("FUSION_${data.uppercase()}").key, PersistentDataType.STRING, "true")
                        }
                    }

                    meta.lore(lore)

                    item.itemMeta = meta

                    items.add(item)
                }
            }
        }

        val neededSlots = items.size

        when (neededSlots) {
            0 -> {
                return
            }

            1 -> {
                inv.setItem(25, items[0])
            }

            2 -> {
                inv.setItem(24, items[0])
                inv.setItem(25, items[1])
            }

            3 -> {
                inv.setItem(16, items[0])
                inv.setItem(25, items[1])
                inv.setItem(34, items[2])
            }

            4 -> {
                inv.setItem(16, items[0])
                inv.setItem(24, items[1])
                inv.setItem(25, items[2])
                inv.setItem(34, items[3])
            }

            5 -> {
                inv.setItem(15, items[0])
                inv.setItem(16, items[1])
                inv.setItem(25, items[2])
                inv.setItem(33, items[3])
                inv.setItem(34, items[4])
            }

            else -> {
                inv.setItem(15, items[0])
                inv.setItem(16, items[1])
                inv.setItem(24, items[2])
                inv.setItem(25, items[3])
                inv.setItem(33, items[4])
                inv.setItem(34, items[5])
            }
        }
    }
}