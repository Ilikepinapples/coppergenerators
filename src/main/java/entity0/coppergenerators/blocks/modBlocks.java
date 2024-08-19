package entity0.coppergenerators.blocks;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppernetworks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class modBlocks {
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of(CopperGenerators.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }
    public static void initialize() {
        CopperGenerators.LOGGER.info("initialising blocks");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> {
            itemGroup.add(modBlocks.SOLARCOPPERGENERATOR.asItem());
            itemGroup.add(modBlocks.THERMOPHOTOVOLTAICGENERATOR.asItem());
            itemGroup.add(modBlocks.PASSIVEVOIDGENERATOR.asItem());
            itemGroup.add(modBlocks.COPPERFIEDSCULKCATALYST.asItem());
        });
    }
    public static final Block SOLARCOPPERGENERATOR = register(
            new SolarGenerator(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)),
            "solar_generator",
            true
    );
    public static final Block THERMOPHOTOVOLTAICGENERATOR = register(
            new ThermoPhotoVoltaicGenerator(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)),
            "thermophotovoltaic_generator",
            true
    );
    public static final Block PASSIVEVOIDGENERATOR = register(
            new PassiveVoidGenerator(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)),
            "passive_void_generator",
            true
    );
    public static final Block COPPERFIEDSCULKCATALYST = register(
            new CopperfiedSculkCatalyst(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER)),
            "copperfied_sculk_catalyst",
            true
    );
}
