package yv.tils.smp.mods.fusionCrafting.manager

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import yv.tils.smp.mods.fusionCrafting.FusionGUIHeads
import yv.tils.smp.mods.fusionCrafting.FusionKeys
import yv.tils.smp.mods.fusionCrafting.enchantments.DataTags
import yv.tils.smp.utils.color.ColorUtils
import java.util.*
import kotlin.system.exitProcess

class FusionRecipeItemManage {
    companion object {
        val fusionRecipeItemEdit = mutableMapOf<UUID, FusionRecipeItem>()
    }

    data class FusionRecipeItem(
        var material: Material,
        var name: String,
        var oldName: String = "",
        var amount: Int,
        var lore: List<Component>,
        var data: MutableList<String>,
        val type: String
    )

    fun parseDataToMap(player: Player) {
        val fusionRecipe = fusionRecipeItemEdit[player.uniqueId] ?: return
        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return

        val fusionInv = fusion.fusionInv

        when (fusionRecipe.type) {
            "input" -> {
                for (f in fusionInv) {
                    val fKey = f.key
                    val fSplited = fKey.split(".")
                    val fKey0 = fSplited[0]

                    if (fKey0 != "input") {
                        continue
                    }

                    val fKey1 = fSplited[1]

                    if (fKey1 != ColorUtils().strip(fusionRecipe.oldName)) {
                        continue
                    }

                    val value = f.value as MutableList<MutableMap<String, Any>>

                    for (v in value) {
                        if (v.containsKey("amount")) {
                            v["amount"] = fusionRecipe.amount.toString()
                        }

                        if (v.equals("data")) {
                            v["data"] = fusionRecipe.data.joinToString { it }
                        }
                    }

                    println("Old name: ${fusionRecipe.oldName}")
                    println("New name: ${fusionRecipe.name}")
                    println("Key: $fKey")
                    println("Splited: ${fSplited[0]}.${ColorUtils().strip(fusionRecipe.name)}.${fSplited[2]}")

                    // TODO: Fix error

                    val modifiedKey = if (fusionRecipe.oldName != fusionRecipe.name) {
                        "${fSplited[0]}.${ColorUtils().strip(fusionRecipe.name)}.${fSplited[2]}"
                    } else {
                        fKey
                    }

                    fusionInv.remove(fKey)
                    fusionInv[modifiedKey] = value
                }
            }
            "output" -> {
                val key = "output"
                // TODO: Add logic
            }
            else -> return
        }

        fusionRecipeItemEdit.remove(player.uniqueId)
    }

    fun openInventory(player: Player, item: ItemStack, type: String) {
        var inv = Bukkit.createInventory(null, 9*3, ColorUtils().convert("<gold>Edit Item"))

        val fusionItem = if (fusionRecipeItemEdit[player.uniqueId] == null) {
            parseItem(player, item, type)
        } else {
            fusionRecipeItemEdit[player.uniqueId] ?: return
        }

        inv = when (type) {
            "input" -> inputInventory(inv, fusionItem)
            "output" -> outputInventory(inv, fusionItem)
            else -> return
        }

        fusionRecipeItemEdit[player.uniqueId] = fusionItem
        player.openInventory(inv)
    }

    private fun inputInventory(inv: Inventory, fusion: FusionRecipeItem): Inventory {
        var inv = inv

        val acceptedItems = ItemStack(Material.BARREL)
        val acceptedItemsMeta = acceptedItems.itemMeta
        val acceptedItemsLore = mutableListOf<Component>()

        acceptedItemsMeta.displayName(ColorUtils().convert("<gold>Usable Items"))

        acceptedItemsLore.add(ColorUtils().convert(" "))
        acceptedItemsLore.add(ColorUtils().convert("<gray>Click to view"))

        acceptedItemsMeta.lore(acceptedItemsLore)
        acceptedItems.itemMeta = acceptedItemsMeta
        inv.setItem(11, acceptedItems)


        val displayName = ItemStack(Material.NAME_TAG)
        val displayNameMeta = displayName.itemMeta
        val displayNameContent = fusion.name
        val displayNameLore = mutableListOf<Component>()

        displayNameMeta.displayName(ColorUtils().convert("<gold>Display Name"))

        displayNameLore.add(ColorUtils().convert(" "))
        displayNameLore.add(ColorUtils().convert("<aqua>$displayNameContent"))

        displayNameMeta.lore(displayNameLore)
        displayName.itemMeta = displayNameMeta
        inv.setItem(12, displayName)


        val amountItem = ItemStack(Material.PAPER)
        val amountItemMeta = amountItem.itemMeta
        val amountItemLore = mutableListOf<Component>()

        amountItemMeta.displayName(ColorUtils().convert("<gold>Amount"))

        amountItemLore.add(ColorUtils().convert(" "))
        amountItemLore.add(ColorUtils().convert("<gray>Amount: <aqua>${fusion.amount}"))

        amountItemMeta.lore(amountItemLore)
        amountItem.itemMeta = amountItemMeta
        amountItem.amount = fusion.amount
        inv.setItem(13, amountItem)


        val tags = ItemStack(Material.BOOK)
        val tagsMeta = tags.itemMeta
        val tagsLore = mutableListOf<Component>()

        tagsMeta.displayName(ColorUtils().convert("<gold>Data Tags"))

        tagsLore.add(ColorUtils().convert(" "))
        for (tag in fusion.data) {
            tagsLore.add(ColorUtils().convert("<gray>$tag"))
        }

        tagsMeta.lore(tagsLore)
        tags.itemMeta = tagsMeta
        inv.setItem(14, tags)

        inv = generalContent(inv)

        return inv
    }

    private fun outputInventory(inv: Inventory, fusion: FusionRecipeItem): Inventory {
        var inv = inv

        val thumbnail = ItemStack(fusion.material)
        val displayMeta: ItemMeta = thumbnail.itemMeta
        displayMeta.displayName(ColorUtils().convert("<gold>Display Item"))

        thumbnail.itemMeta = displayMeta
        inv.setItem(11, thumbnail)


        val displayName = ItemStack(Material.NAME_TAG)
        val displayNameMeta = displayName.itemMeta
        val displayNameContent = fusion.name
        val displayNameLore = mutableListOf<Component>()

        displayNameMeta.displayName(ColorUtils().convert("<gold>Display Name"))

        displayNameLore.add(ColorUtils().convert(" "))
        displayNameLore.add(ColorUtils().convert("<aqua>$displayNameContent"))

        displayNameMeta.lore(displayNameLore)
        displayName.itemMeta = displayNameMeta
        inv.setItem(12, displayName)


        val amountItem = ItemStack(Material.PAPER)
        val amountItemMeta = amountItem.itemMeta
        val amountItemLore = mutableListOf<Component>()

        amountItemMeta.displayName(ColorUtils().convert("<gold>Amount"))

        amountItemLore.add(ColorUtils().convert(" "))
        amountItemLore.add(ColorUtils().convert("<gray>Amount: <aqua>${fusion.amount}"))

        amountItemMeta.lore(amountItemLore)
        amountItem.itemMeta = amountItemMeta
        amountItem.itemMeta = amountItemMeta
        inv.setItem(13, amountItem)


        val description = ItemStack(Material.MAP)
        val descriptionMeta = description.itemMeta
        val descriptionLore = mutableListOf<Component>()

        descriptionMeta.displayName(ColorUtils().convert("<gold>Item Lore"))

        descriptionLore.add(ColorUtils().convert(" "))
        for (descLine in fusion.lore) {
            descriptionLore.add(ColorUtils().convert("<white>${ColorUtils().convert(descLine)}"))
        }

        descriptionMeta.lore(descriptionLore)
        description.itemMeta = descriptionMeta
        inv.setItem(14, description)


        val tags = ItemStack(Material.BOOK)
        val tagsMeta = tags.itemMeta
        val tagsLore = mutableListOf<Component>()

        tagsMeta.displayName(ColorUtils().convert("<gold>Data Tags"))

        tagsLore.add(ColorUtils().convert(" "))
        for (tag in fusion.data) {
            tagsLore.add(ColorUtils().convert("<gray>$tag"))
        }

        tagsMeta.lore(tagsLore)
        tags.itemMeta = tagsMeta
        inv.setItem(15, tags)

        inv = generalContent(inv)

        return inv
    }

    private fun generalContent(inv: Inventory): Inventory {
        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        back.itemMeta = backMeta

        inv.setItem(18, back)

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        outerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, outerFiller)
            }
        }

        for (i in 0 until inv.size) {
            val item = inv.getItem(i) ?: continue
            item.itemMeta.persistentDataContainer.set(FusionKeys.FUSION_GUI.key, PersistentDataType.STRING, "fusion")
            inv.setItem(i, item)
        }

        return inv
    }

    fun editDisplayItem(player: Player) {
        val displayItem = fusionRecipeItemEdit[player.uniqueId] ?: return
        val item = ItemStack(displayItem.material)
        val inv = Bukkit.createInventory(null, 9, ColorUtils().convert("<gold>Edit Display Item"))

        inv.setItem(4, item)

        val accept = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val acceptMeta = accept.itemMeta
        acceptMeta.displayName(ColorUtils().convert("<green>Update DisplayItem"))
        acceptMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)

        accept.itemMeta = acceptMeta
        inv.setItem(0, accept)

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        outerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, outerFiller)
            }
        }

        player.openInventory(inv)
    }

    fun editAcceptedItems(player: Player) {
        val itemList = parseItemList(player)

        val inv = Bukkit.createInventory(null, 9*5, ColorUtils().convert("<gold>Modify accepted items"))

        val itemSlots = mutableListOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34)

        try {
            for (i in 0 until itemList.size) {
                inv.setItem(itemSlots[i], itemList[i])
            }
        } catch (_: IndexOutOfBoundsException) {
            player.sendMessage(ColorUtils().convert("<red>There was an error parsing the items (Probably too many items, max 21)"))
            return
        }

        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        back.itemMeta = backMeta
        inv.setItem(40, back)

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        outerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                if (i !in itemSlots) {
                    inv.setItem(i, outerFiller)
                }
            }
        }

        player.openInventory(inv)
    }

    fun editDisplayName(player: Player) {
        FusionCraftManage.playerListen[player.uniqueId] = "fusionRecipeItemDisplayName"

        val name = fusionRecipeItemEdit[player.uniqueId]?.name ?: return

        player.sendMessage(ColorUtils().convert(
            "<gold>Editing Fusion Recipe Item Name<newline>" +
                    "<gray>Current Name: <white>${name}<newline>" +
                    "<red>'c' to cancel"
        ))

        player.closeInventory()
    }

    fun editAmount(player: Player, clickType: ClickType, item: ItemStack): ItemStack {
        FusionCraftManage.playerListen[player.uniqueId] = "fusionRecipeItemAmount"

        val amount = fusionRecipeItemEdit[player.uniqueId]?.amount ?: return item

        when (clickType) {
            ClickType.LEFT -> {
                if (amount + 1 > 64) {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = 64
                    player.sendMessage(ColorUtils().convert("<red>Amount cannot be more than 64"))
                } else {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = amount + 1
                }
            }
            ClickType.RIGHT -> {
                if (amount - 1 < 1) {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = 1
                    player.sendMessage(ColorUtils().convert("<red>Amount cannot be less than 1"))
                } else {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = amount - 1
                }
            }
            ClickType.SHIFT_LEFT -> {
                if (amount + 10 > 64) {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = 64
                    player.sendMessage(ColorUtils().convert("<red>Amount cannot be more than 64"))
                } else {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = amount + 10
                }
            }
            ClickType.SHIFT_RIGHT -> {
                if (amount - 10 < 1) {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = 1
                    player.sendMessage(ColorUtils().convert("<red>Amount cannot be less than 1"))
                } else {
                    fusionRecipeItemEdit[player.uniqueId]?.amount = amount - 10
                }
            }
            else -> return item
        }

        item.amount = fusionRecipeItemEdit[player.uniqueId]?.amount ?: return item

        val meta = item.itemMeta
        val lore = meta.lore() ?: return item
        val newLore = mutableListOf<Component>()

        for (line in lore) {
            val stringLine = ColorUtils().convert(line)

            if (stringLine.contains("Amount:")) {
                newLore.add(ColorUtils().convert("<gray>Amount: <aqua>${fusionRecipeItemEdit[player.uniqueId]?.amount}"))
            } else {
                newLore.add(line)
            }
        }

        meta.lore(newLore)
        item.itemMeta = meta

        return item
    }

    fun editLore(player: Player) {
        FusionCraftManage.playerListen[player.uniqueId] = "fusionRecipeItemLore"

        val lore = fusionRecipeItemEdit[player.uniqueId]?.lore ?: return
        val stringLore = mutableListOf<String>()

        stringLore.add("<gray>---")

        for (line in lore) {
            stringLore.add(ColorUtils().convert(line))
        }

        stringLore.add("<gray>---")

        player.sendMessage(ColorUtils().convert(
            "<gold>Editing Fusion Recipe Item Lore<newline>" +
                    "<gray>Current Lore:<newline>" +
                    "<white>${stringLore.joinToString("<newline>")}<newline>" +
                    "<red>'c' to cancel"
        ))

        player.closeInventory()
    }

    fun editDataTags(player: Player) {
        val dataTags = fusionRecipeItemEdit[player.uniqueId]?.data ?: return
        val inv = Bukkit.createInventory(null, 9*4, ColorUtils().convert("<gold>Edit Data Tags"))

        val tagSlots = mutableListOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25) // Support for 14 tags

        for (i in 0 until dataTags.size) {
            val tag = ItemStack(Material.PAPER)
            val tagMeta = tag.itemMeta
            val tagLore = mutableListOf<Component>()

            tagMeta.displayName(ColorUtils().convert("<gold>${dataTags[i]}"))

            tagLore.add(ColorUtils().convert(" "))
            tagLore.add(ColorUtils().convert("<gray>Click to remove"))

            tagMeta.lore(tagLore)
            tag.itemMeta = tagMeta
            inv.setItem(tagSlots[i], tag)
        }

        val createHead = ItemStack(Material.PLAYER_HEAD, 1)
        val createHeadMeta = createHead.itemMeta as SkullMeta
        val gameProfile = GameProfile(UUID.randomUUID(), "PageHead")
        gameProfile.properties.put("textures", Property("textures", FusionGUIHeads.ADD_FUSION.texture))

        try {
            val profileField = createHeadMeta.javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField.set(createHeadMeta, gameProfile)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        createHeadMeta.displayName(ColorUtils().convert("<green>Create Fusion"))
        createHeadMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        createHead.itemMeta = createHeadMeta
        inv.setItem(4, createHead)

        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        back.itemMeta = backMeta
        inv.setItem(31, back)

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        outerFiller.itemMeta = fillerMeta

        val innerFiller = ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
        innerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                if (i in tagSlots) {
                    inv.setItem(i, innerFiller)
                } else {
                    inv.setItem(i, outerFiller)
                }
            }
        }

        player.openInventory(inv)
    }

    // TODO: Add support for custom data tags with no functionality
    fun appendDataTag(player: Player) {
        val dataTags = fusionRecipeItemEdit[player.uniqueId]?.data ?: return
        val availableDataTags = mutableListOf<String>()

        for (tag in DataTags.entries) {
            if (dataTags.contains(tag.key.key.toString())) {
                continue
            }

            availableDataTags.add(tag.toString())
        }

        val inv = Bukkit.createInventory(null, 9*4, ColorUtils().convert("<gold>Append Data Tag"))

        val tagSlots = mutableListOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25) // Support for 14 tags

        for (i in 0 until availableDataTags.size) {
            val tag = ItemStack(Material.PAPER)
            val tagMeta = tag.itemMeta
            val tagLore = mutableListOf<Component>()

            tagMeta.displayName(ColorUtils().convert("<gold>${availableDataTags[i]}"))

            tagLore.add(ColorUtils().convert(" "))
            tagLore.add(ColorUtils().convert("<gray>Click to add"))

            tagMeta.lore(tagLore)
            tag.itemMeta = tagMeta
            inv.setItem(tagSlots[i], tag)
        }

        val back = ItemStack(Material.TIPPED_ARROW)
        val backMeta = back.itemMeta as PotionMeta
        backMeta.color = Color.fromRGB(150, 85, 95)
        backMeta.displayName(ColorUtils().convert("<red>Back"))
        backMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        back.itemMeta = backMeta
        inv.setItem(31, back)

        val outerFiller = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val fillerMeta = outerFiller.itemMeta
        fillerMeta.displayName(ColorUtils().convert(" "))
        fillerMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        outerFiller.itemMeta = fillerMeta

        val innerFiller = ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
        innerFiller.itemMeta = fillerMeta

        for (i in 0..<inv.size) {
            if (inv.getItem(i) == null) {
                if (i in tagSlots) {
                    inv.setItem(i, innerFiller)
                } else {
                    inv.setItem(i, outerFiller)
                }
            }
        }

        player.openInventory(inv)
    }

    private fun parseItem(player: Player, item: ItemStack, type: String): FusionRecipeItem {
        val material = item.type
        val name = item.itemMeta.persistentDataContainer.get(FusionKeys.FUSION_ITEMNAME.key, PersistentDataType.STRING)
        val oldName = name
        val amount = item.amount
        val lore = item.itemMeta.lore()
        val data: MutableList<String> = mutableListOf()

        if (name == null || lore == null) {
            return FusionRecipeItem(Material.BARRIER, "null", "null", 0, listOf(Component.text("null")), mutableListOf("null"), "null")
        }

        for (line in lore) {
            val stringLine = ColorUtils().convert(line)

            if (stringLine.contains("Item Data:")) {
                val split = stringLine.split(": ")
                val dataTags = split[1].split(", ")

                for (tag in dataTags) {
                    data.add(tag)
                }
            }
        }

        val fusionRecipeItem = FusionRecipeItem(material, name, oldName!!, amount, lore, data, type)

        fusionRecipeItemEdit[player.uniqueId] = fusionRecipeItem

        return fusionRecipeItem
    }

    private fun parseItemList(player: Player): MutableList<ItemStack> {
        val itemList = mutableListOf<ItemStack>()

        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return itemList
        val fusionRecipe = fusionRecipeItemEdit[player.uniqueId] ?: return itemList

        for (f in fusion.fusionInv) {
            val keySplit0 = f.key.split(".")[0]
            if (keySplit0 != "input") {
                continue
            }

            val keySplit1 = f.key.split(".")[1]
            val requiredKey = ColorUtils().strip(fusionRecipe.name)
            if (keySplit1 != requiredKey) {
                continue
            }

            val value = f.value as MutableList<MutableMap<String, Any>>

            for (v in value) {
                if (v.containsKey("item")) {
                    val item = ItemStack(Material.valueOf((v["item"] as String).uppercase()))
                    itemList.add(item)
                    break
                }
            }
        }

        return itemList
    }

    // TODO: When using a previous unused slot, the item will be ignored
    fun saveItemList(player: Player, inv: Inventory) {
        val itemList = mutableListOf<ItemStack>()
        val itemSlots = mutableListOf(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34)

        for (slot in itemSlots) {
            val item = inv.getItem(slot) ?: continue

            if (item.type == Material.AIR) {
                continue
            }

            itemList.add(item)
        }

        val fusionRecipe = fusionRecipeItemEdit[player.uniqueId] ?: return
        val fusion = FusionManagerGUI.playerManager[player.uniqueId] ?: return

        for (f in fusion.fusionInv) {
            val keySplit0 = f.key.split(".")[0]
            if (keySplit0 != "input") {
                continue
            }

            val keySplit1 = f.key.split(".")[1]
            val requiredKey = ColorUtils().strip(fusionRecipe.name)
            if (keySplit1 != requiredKey) {
                continue
            }

            val value = f.value as MutableList<MutableMap<String, Any>>

            for (v in value) {
                if (v.containsKey("item")) {
                    val item = itemList.removeAt(0)
                    v["item"] = item.type.toString().lowercase()
                    break
                }
            }
        }
    }
}