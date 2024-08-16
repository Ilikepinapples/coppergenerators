package entity0.coppergenerators.blocks.entity;

import entity0.coppernetworks.CopperNetworkPowerClass;
import entity0.coppernetworks.copperNetworkPowerAPI;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermoPhotoVoltaicGeneratorBlockEntity extends BlockEntity implements copperNetworkPowerAPI {
    public ThermoPhotoVoltaicGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.THERMO_PHOTOVOLTAIC_GENERATOR_BLOCK_ENTITY, pos, state);
    }

    CopperNetworkPowerClass copperPowerInstance = new CopperNetworkPowerClass();
    @Override
    public CopperNetworkPowerClass copperNetworkAPI() {
        return copperPowerInstance;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ThermoPhotoVoltaicGeneratorBlockEntity blockEntity) {
        if (world.getDimension().ultrawarm() && world.getBlockState(pos.down()).isOf(Blocks.LAVA)) {
            if (blockEntity.copperNetworkAPI().canGenerate(1)) {
                blockEntity.copperNetworkAPI().generate(1);
            }
        }
        blockEntity.copperNetworkAPI().cleanupNetwork();
    }
}
