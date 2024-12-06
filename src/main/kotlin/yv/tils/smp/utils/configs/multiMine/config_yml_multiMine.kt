package yv.tils.smp.utils.configs.multiMine

import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml_multiMine {
    private var file = File(YVtils.instance.dataFolder.path, "multiMine/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://docs.yvtils.net/smp/multiMine/config.yml")

        ymlFile.addDefault("defaultState", true)
        ymlFile.addDefault("animationTime", 3)
        ymlFile.addDefault("cooldownTime", 3)
        ymlFile.addDefault("breakLimit", 250)
        ymlFile.addDefault("blocks", createTemplateBlocks())

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }

    private fun createTemplateBlocks(): List<String> {
        val blocks = listOf(
            Material.OAK_LOG.name,
            Material.BIRCH_LOG.name,
            Material.SPRUCE_LOG.name,
            Material.JUNGLE_LOG.name,
            Material.ACACIA_LOG.name,
            Material.DARK_OAK_LOG.name,
            Material.CHERRY_LOG.name,
            Material.MANGROVE_LOG.name,
            Material.PALE_OAK_WOOD.name,
            Material.CRIMSON_STEM.name,
            Material.WARPED_STEM.name,
            Material.STRIPPED_OAK_LOG.name,
            Material.STRIPPED_BIRCH_LOG.name,
            Material.STRIPPED_SPRUCE_LOG.name,
            Material.STRIPPED_JUNGLE_LOG.name,
            Material.STRIPPED_ACACIA_LOG.name,
            Material.STRIPPED_DARK_OAK_LOG.name,
            Material.STRIPPED_CHERRY_LOG.name,
            Material.STRIPPED_MANGROVE_LOG.name,
            Material.STRIPPED_PALE_OAK_WOOD.name,
            Material.STRIPPED_CRIMSON_STEM.name,
            Material.STRIPPED_WARPED_STEM.name,

            Material.COAL_ORE.name,
            Material.IRON_ORE.name,
            Material.GOLD_ORE.name,
            Material.DIAMOND_ORE.name,
            Material.EMERALD_ORE.name,
            Material.LAPIS_ORE.name,
            Material.REDSTONE_ORE.name,
            Material.COPPER_ORE.name,
            Material.DEEPSLATE_COAL_ORE.name,
            Material.DEEPSLATE_IRON_ORE.name,
            Material.DEEPSLATE_GOLD_ORE.name,
            Material.DEEPSLATE_DIAMOND_ORE.name,
            Material.DEEPSLATE_EMERALD_ORE.name,
            Material.DEEPSLATE_LAPIS_ORE.name,
            Material.DEEPSLATE_REDSTONE_ORE.name,
            Material.DEEPSLATE_COPPER_ORE.name,
            Material.NETHER_QUARTZ_ORE.name,
            Material.NETHER_GOLD_ORE.name,
            Material.ANCIENT_DEBRIS.name,
            Material.GLOWSTONE.name,
        )

        return blocks
    }
}