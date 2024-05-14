package yv.tils.smp.mods.multiMine

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.itemStackArgument
import dev.jorel.commandapi.kotlindsl.stringArgument
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import yv.tils.smp.mods.multiMine.MultiMineHandler.Companion.blocks
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig

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
                            addBlock(sender, args[1] as Material)
                        }

                        "remove" -> {
                            removeBlock(sender, args[1] as Material)
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

    private fun addBlock(sender: CommandSender, block: Material) {
        var block = block
        if (sender !is Player) {
            if (block == null) {
                // TODO: Non Player -> No block specified
                return
            }
        } else {
            if (block == null) {
                if (sender.inventory.itemInMainHand.type == Material.AIR) {
                    // TODO: Player -> No block in hand or specified
                } else {
                    block = sender.inventory.itemInMainHand.type
                }
            }

            modifyBlockList("+", block)
        }
    }

    private fun removeBlock(sender: CommandSender, block: Material) {
        var block = block
        if (sender !is Player) {
            if (block == null) {
                // TODO: Non Player -> No block specified
                return
            }
        } else {
            if (block == null) {
                if (sender.inventory.itemInMainHand.type == Material.AIR) {
                    // TODO: Player -> No block in hand or specified
                } else {
                    block = sender.inventory.itemInMainHand.type
                }
            }

            modifyBlockList("-", block)
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



        return content
    }

    private fun addMultiple(sender: CommandSender) {
        if (sender !is Player) {
            // TODO: Non Player -> Not able to add multiple blocks
            return
        }

        val player = sender

        if (checkForContainer(player.inventory.itemInMainHand.type)) {
            // TODO: Player -> No container in hand
            return
        }

        for (block in loadContainerContent(player.inventory.itemInMainHand)) {
            modifyBlockList("+", block)
        }
    }

    private fun removeMultiple(sender: CommandSender) {
        if (sender !is Player) {
            // TODO: Non Player -> Not able to remove multiple blocks
            return
        }

        val player = sender

        if (checkForContainer(player.inventory.itemInMainHand.type)) {
            // TODO: Player -> No container in hand
            return
        }

        for (block in loadContainerContent(player.inventory.itemInMainHand)) {
            modifyBlockList("-", block)
        }
    }

    private fun modifyBlockList(identifier: String, block: Material) {
        if (identifier == "+") {
            blocks.add(block)
        } else if (identifier == "-") {
            blocks.remove(block)
        }
        MultiMineConfig().updateBlockList(blocks)
    }
}