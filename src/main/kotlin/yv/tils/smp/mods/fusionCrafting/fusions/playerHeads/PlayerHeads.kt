package yv.tils.smp.mods.fusionCrafting.fusions.playerHeads

import org.bukkit.configuration.file.YamlConfiguration

class PlayerHeads {
    fun configFile(ymlFile: YamlConfiguration): YamlConfiguration {
        ymlFile.addDefault("enabled", true)
        ymlFile.addDefault("name", "Player Heads")
        ymlFile.addDefault("displayItem", "PLAYER_HEAD")
        ymlFile.addDefault("description", "<white>Craft an <gold>Player Head <white>from a <gold>specific Player")

        val inputItems = inputItems()
        val outputItems = outputItems()

        for (i in 0 until inputItems.size) {
            for (j in 0 until inputItems.values.elementAt(i).size) {
                ymlFile.addDefault("input.${inputItems.keys.elementAt(i)}.$j", inputItems.values.elementAt(i)[j])
            }
        }

        for (i in 0 until outputItems.size) {
            ymlFile.addDefault("output.$i", outputItems[i])
        }

        ymlFile.options().copyDefaults(true)

        return ymlFile
    }

    private fun inputItems(): MutableMap<String, MutableList<MutableList<MutableMap<String, String>>>> {
        val items = mutableMapOf<String, MutableList<MutableList<MutableMap<String, String>>>>()

        val armorStand = mutableMapOf("item" to "ARMOR_STAND")
        val armorStandAmount = mutableMapOf("amount" to "1")
        val armorStandData = mutableMapOf("data" to "")

        items["Armor Stand"] = mutableListOf(
            mutableListOf(armorStand, armorStandAmount, armorStandData),
        )

        val nameTag = mutableMapOf("item" to "NAME_TAG")
        val nameTagAmount = mutableMapOf("amount" to "1")
        val nameTagData = mutableMapOf("data" to "")

        items["NameTag with Player Name"] = mutableListOf(
            mutableListOf(nameTag, nameTagAmount, nameTagData)
        )

        val leatherHelmet = mutableMapOf("item" to "LEATHER_HELMET")
        val leatherHelmetAmount = mutableMapOf("amount" to "1")
        val leatherHelmetData = mutableMapOf("data" to "")

        items["Leather Helmet"] = mutableListOf(
            mutableListOf(leatherHelmet, leatherHelmetAmount, leatherHelmetData)
        )

        return items
    }

    private fun outputItems(): MutableList<MutableList<MutableMap<String, String>>> {
        val items: MutableList<MutableList<MutableMap<String, String>>> = mutableListOf()

        val item = mutableMapOf("item" to "PLAYER_HEAD")
        val amount = mutableMapOf("amount" to "1")
        val name = mutableMapOf("name" to "<gold>Player Head")
        val lore = mutableMapOf("lore" to "<white>A fake player head made from an <yellow>armor stand<white>, <yellow>leather <white>and a <yellow>name tag")
        val data = mutableMapOf("data" to "Player Head")

        items.add(mutableListOf(item, amount, name, lore, data))

        return items
    }
}