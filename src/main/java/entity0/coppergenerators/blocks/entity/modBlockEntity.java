package entity0.coppergenerators.blocks.entity;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppergenerators.blocks.ThermoPhotoVoltaicGenerator;
import entity0.coppergenerators.blocks.modBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class modBlockEntity {
    public static final BlockEntityType<SolarGeneratorBlockEntity> SOLAR_GENERATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(CopperGenerators.MOD_ID, "solar_generator_block_entity"),
            BlockEntityType.Builder.create(SolarGeneratorBlockEntity::new, modBlocks.SOLARCOPPERGENERATOR).build()
    );
    public static final BlockEntityType<ThermoPhotoVoltaicGeneratorBlockEntity> THERMO_PHOTOVOLTAIC_GENERATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(CopperGenerators.MOD_ID, "thermo_photovoltaic_generator_block_entity"),
            BlockEntityType.Builder.create(ThermoPhotoVoltaicGeneratorBlockEntity::new, modBlocks.THERMOPHOTOVOLTAICGENERATOR).build()
    );


    public static void registerBlockEntities() {
        CopperGenerators.LOGGER.info("block entities registered");
    }
}
