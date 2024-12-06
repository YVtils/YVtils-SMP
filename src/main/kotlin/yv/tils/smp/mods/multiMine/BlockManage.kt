package yv.tils.smp.mods.multiMine

import org.bukkit.Material
import org.bukkit.block.ShulkerBox
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.inventory.meta.BundleMeta
import yv.tils.smp.mods.multiMine.MultiMineHandler.Companion.blocks
import yv.tils.smp.utils.configs.language.LangStrings
import yv.tils.smp.utils.configs.language.Language
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig
import yv.tils.smp.utils.internalAPI.Placeholder

class BlockManage {
    fun addBlock(sender: CommandSender, block: Any?) {
        val block = block as ItemStack?
        var material = block?.type
        if (sender !is Player) {
            if (material == null) {
                sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_BLOCK))
                return
            }

            val b = modifyBlockList("+", material, sender)
            if (b) {
                sender.sendMessage(Placeholder().replacer(
                    Language().getMessage(LangStrings.MODULE_MULTIMINE_ADD_BLOCK),
                    listOf("block"),
                    listOf(material.name)
                ))
            }
        } else {
            if (material == null) {
                if (sender.inventory.itemInMainHand.type == Material.AIR) {
                    sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_NO_BLOCK_IN_HAND))
                    return
                } else {
                    material = sender.inventory.itemInMainHand.type
                }
            }

            val b = modifyBlockList("+", material, sender)
            if (b) {
                sender.sendMessage(Placeholder().replacer(
                    Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_ADD_BLOCK),
                    listOf("block"),
                    listOf(material.name)
                ))
            }
        }
    }

    fun removeBlock(sender: CommandSender, block: Any?) {
        val block = block as ItemStack?
        var material = block?.type
        if (sender !is Player) {
            if (material == null) {
                sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_BLOCK))
                return
            }

            val b = modifyBlockList("-", material, sender)
            if (b) {
                sender.sendMessage(Placeholder().replacer(
                    Language().getMessage(LangStrings.MODULE_MULTIMINE_REMOVE_BLOCK),
                    listOf("block"),
                    listOf(material.name)
                ))
            }
        } else {
            if (material == null) {
                if (sender.inventory.itemInMainHand.type == Material.AIR) {
                    sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_BLOCK_IN_HAND))
                    return
                } else {
                    material = sender.inventory.itemInMainHand.type
                }
            }

            val b = modifyBlockList("-", material, sender)

            if (b) {
                sender.sendMessage(
                    Placeholder().replacer(
                        Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_REMOVE_BLOCK),
                        listOf("block"),
                        listOf(material.name)
                    )
                )
            }
        }
    }

    private fun checkForContainer(item: Material): Boolean {
        val containerList = mutableListOf<Material>()
        containerList.addAll(shulkerList())
        containerList.addAll(bundleList())

        return containerList.contains(item)
    }

    private fun shulkerList(): MutableList<Material> {
        val shulkerList = mutableListOf<Material>()
        shulkerList.add(Material.SHULKER_BOX)
        shulkerList.add(Material.BLACK_SHULKER_BOX)
        shulkerList.add(Material.BLUE_SHULKER_BOX)
        shulkerList.add(Material.BROWN_SHULKER_BOX)
        shulkerList.add(Material.CYAN_SHULKER_BOX)
        shulkerList.add(Material.GRAY_SHULKER_BOX)
        shulkerList.add(Material.GREEN_SHULKER_BOX)
        shulkerList.add(Material.LIGHT_BLUE_SHULKER_BOX)
        shulkerList.add(Material.LIGHT_GRAY_SHULKER_BOX)
        shulkerList.add(Material.LIME_SHULKER_BOX)
        shulkerList.add(Material.MAGENTA_SHULKER_BOX)
        shulkerList.add(Material.ORANGE_SHULKER_BOX)
        shulkerList.add(Material.PINK_SHULKER_BOX)
        shulkerList.add(Material.PURPLE_SHULKER_BOX)
        shulkerList.add(Material.RED_SHULKER_BOX)
        shulkerList.add(Material.WHITE_SHULKER_BOX)
        shulkerList.add(Material.YELLOW_SHULKER_BOX)
        return shulkerList
    }

    private fun bundleList(): MutableList<Material> {
        val bundleList = mutableListOf<Material>()
        bundleList.add(Material.BUNDLE)
        bundleList.add(Material.BLACK_BUNDLE)
        bundleList.add(Material.BLUE_BUNDLE)
        bundleList.add(Material.BROWN_BUNDLE)
        bundleList.add(Material.CYAN_BUNDLE)
        bundleList.add(Material.GRAY_BUNDLE)
        bundleList.add(Material.GREEN_BUNDLE)
        bundleList.add(Material.LIGHT_BLUE_BUNDLE)
        bundleList.add(Material.LIGHT_GRAY_BUNDLE)
        bundleList.add(Material.LIME_BUNDLE)
        bundleList.add(Material.MAGENTA_BUNDLE)
        bundleList.add(Material.ORANGE_BUNDLE)
        bundleList.add(Material.PINK_BUNDLE)
        bundleList.add(Material.PURPLE_BUNDLE)
        bundleList.add(Material.RED_BUNDLE)
        bundleList.add(Material.WHITE_BUNDLE)
        bundleList.add(Material.YELLOW_BUNDLE)
        return bundleList
    }

    private fun loadContainerContent(container: ItemStack): List<Material> {
        val content = mutableListOf<Material>()

        if (bundleList().contains(container.type)) {
            val bundle = container.itemMeta as BundleMeta
            for (item in bundle.items) {
                content.add(item.type)
            }
        } else {
            val shulkerBox = container.itemMeta as BlockStateMeta
            val inventory = shulkerBox.blockState as ShulkerBox
            for (item in inventory.inventory.contents) {
                if (item != null) {
                    content.add(item.type)
                }
            }
        }

        return content
    }

    fun addMultiple(sender: CommandSender) {
        if (sender !is Player) {
            sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_MULTIPLE_CONSOLE))
            return
        }

        if (!checkForContainer(sender.inventory.itemInMainHand.type)) {
            sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_CONTAINER_IN_HAND))
            return
        }

        val blocks = mutableListOf<Material>()

        for (block in loadContainerContent(sender.inventory.itemInMainHand)) {
            val b = modifyBlockList("+", block, sender)
            if (!b) {
                continue
            }

            blocks.add(block)
        }

        sender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_ADD_MULTIPLE),
                listOf("blocks"),
                listOf(blocks.joinToString(", ") { it.name })
            )
        )
    }

    fun removeMultiple(sender: CommandSender) {
        if (sender !is Player) {
            sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_MULTIPLE_CONSOLE))
            return
        }

        if (!checkForContainer(sender.inventory.itemInMainHand.type)) {
            sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_CONTAINER_IN_HAND))
            return
        }

        val blocks = mutableListOf<Material>()

        for (block in loadContainerContent(sender.inventory.itemInMainHand)) {
            val b = modifyBlockList("-", block, sender)
            if (!b) {
                continue
            }

            blocks.add(block)
        }

        sender.sendMessage(
            Placeholder().replacer(
                Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_REMOVE_MULTIPLE),
                listOf("blocks"),
                listOf(blocks.joinToString(", ") { it.name })
            )
        )
    }

    private fun modifyBlockList(identifier: String, block: Material, sender: CommandSender): Boolean {
        if (identifier == "+") {
            if (blocks.contains(block)) {
                sender.sendMessage(Placeholder().replacer(
                    Language().getMessage(LangStrings.MODULE_MULTIMINE_BLOCK_ALREADY_IN_LIST),
                    listOf("block"),
                    listOf(block.name)
                ))
                return false
            }

            blocks.add(block)
        } else if (identifier == "-") {
            if (!blocks.contains(block)) {
                sender.sendMessage(Placeholder().replacer(
                    Language().getMessage(LangStrings.MODULE_MULTIMINE_BLOCK_NOT_IN_LIST),
                    listOf("block"),
                    listOf(block.name)
                ))
                return false
            }

            blocks.remove(block)
        }
        MultiMineConfig().updateBlockList(blocks)

        return true
    }
}