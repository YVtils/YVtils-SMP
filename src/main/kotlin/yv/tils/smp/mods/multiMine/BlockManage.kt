package yv.tils.smp.mods.multiMine

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.itemStackArgument
import dev.jorel.commandapi.kotlindsl.stringArgument
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
    val command = commandTree("multiMine") {
        withPermission("yvtils.smp.command.multiMine")
        withUsage("multiMine <add/remove/addMultiple/removeMultiple> [block]")
        withAliases("mm")

        stringArgument("action") {
            replaceSuggestions(
                ArgumentSuggestions.strings(
                    "add",
                    "remove",
                    "addMultiple",
                    "removeMultiple"
                )
            )
            itemStackArgument("block", true) {
                anyExecutor { sender, args ->
                    when (args[0]) {
                        "add" -> {
                            addBlock(sender, args[1])
                        }

                        "remove" -> {
                            removeBlock(sender, args[1])
                        }

                        "addMultiple" -> {
                            addMultiple(sender)
                        }

                        "removeMultiple" -> {
                            removeMultiple(sender)
                        }
                    }
                }
            }
        }
    }

    private fun addBlock(sender: CommandSender, block: Any?) {
        val block = block as ItemStack?
        var material = block?.type
        if (sender !is Player) {
            if (material == null) {
                sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_BLOCK))
                return
            }

            modifyBlockList("+", material, sender)
            sender.sendMessage(Placeholder().replacer(
                Language().getMessage(LangStrings.MODULE_MULTIMINE_ADD_BLOCK),
                listOf("block"),
                listOf(material.name)
            ))
        } else {
            if (material == null) {
                if (sender.inventory.itemInMainHand.type == Material.AIR) {
                    sender.sendMessage(Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_NO_BLOCK_IN_HAND))
                    return
                } else {
                    material = sender.inventory.itemInMainHand.type
                }
            }

            modifyBlockList("+", material, sender)
            sender.sendMessage(Placeholder().replacer(
                Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_ADD_BLOCK),
                listOf("block"),
                listOf(material.name)
            ))
        }
    }

    private fun removeBlock(sender: CommandSender, block: Any?) {
        val block = block as ItemStack?
        var material = block?.type
        if (sender !is Player) {
            if (material == null) {
                sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_BLOCK))
                return
            }

            modifyBlockList("-", material, sender)
            sender.sendMessage(Placeholder().replacer(
                Language().getMessage(LangStrings.MODULE_MULTIMINE_REMOVE_BLOCK),
                listOf("block"),
                listOf(material.name)
            ))
        } else {
            if (material == null) {
                if (sender.inventory.itemInMainHand.type == Material.AIR) {
                    sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_NO_BLOCK_IN_HAND))
                    return
                } else {
                    material = sender.inventory.itemInMainHand.type
                }
            }

            modifyBlockList("-", material, sender)
            sender.sendMessage(
                Placeholder().replacer(
                    Language().getMessage(sender.uniqueId, LangStrings.MODULE_MULTIMINE_REMOVE_BLOCK),
                    listOf("block"),
                    listOf(material.name)
                )
            )
        }
    }

    private fun checkForContainer(item: Material): Boolean {
        val containerList = mutableListOf<Material>()
        containerList.add(Material.SHULKER_BOX)
        containerList.add(Material.BLACK_SHULKER_BOX)
        containerList.add(Material.BLUE_SHULKER_BOX)
        containerList.add(Material.BROWN_SHULKER_BOX)
        containerList.add(Material.CYAN_SHULKER_BOX)
        containerList.add(Material.GRAY_SHULKER_BOX)
        containerList.add(Material.GREEN_SHULKER_BOX)
        containerList.add(Material.LIGHT_BLUE_SHULKER_BOX)
        containerList.add(Material.LIGHT_GRAY_SHULKER_BOX)
        containerList.add(Material.LIME_SHULKER_BOX)
        containerList.add(Material.MAGENTA_SHULKER_BOX)
        containerList.add(Material.ORANGE_SHULKER_BOX)
        containerList.add(Material.PINK_SHULKER_BOX)
        containerList.add(Material.PURPLE_SHULKER_BOX)
        containerList.add(Material.RED_SHULKER_BOX)
        containerList.add(Material.WHITE_SHULKER_BOX)
        containerList.add(Material.YELLOW_SHULKER_BOX)
        containerList.add(Material.BUNDLE)

        return containerList.contains(item)
    }

    private fun loadContainerContent(container: ItemStack): List<Material> {
        val content = mutableListOf<Material>()

        if (container.type == Material.BUNDLE) {
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

    private fun addMultiple(sender: CommandSender) {
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
            modifyBlockList("+", block, sender)
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

    private fun removeMultiple(sender: CommandSender) {
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
            modifyBlockList("-", block, sender)
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

    private fun modifyBlockList(identifier: String, block: Material, sender: CommandSender) {
        if (identifier == "+") {
            if (blocks.contains(block)) {
                sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_BLOCK_ALREADY_IN_LIST))
                return
            }

            blocks.add(block)
        } else if (identifier == "-") {
            if (!blocks.contains(block)) {
                sender.sendMessage(Language().getMessage(LangStrings.MODULE_MULTIMINE_BLOCK_NOT_IN_LIST))
                return
            }

            blocks.remove(block)
        }
        MultiMineConfig().updateBlockList(blocks)
    }
}