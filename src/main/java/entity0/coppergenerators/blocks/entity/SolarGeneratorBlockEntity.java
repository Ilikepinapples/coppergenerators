package entity0.coppergenerators.blocks.entity;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppernetworks.CopperNetworkPowerClass;
import entity0.coppernetworks.CopperNetworks;
import entity0.coppernetworks.blockentity.ModBlockEntity;
import entity0.coppernetworks.copperNetworkPowerAPI;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;

public class SolarGeneratorBlockEntity extends BlockEntity implements copperNetworkPowerAPI {
    CopperNetworkPowerClass copperPowerInstance = new CopperNetworkPowerClass();
    @Override
    public CopperNetworkPowerClass copperNetworkAPI() {
        return copperPowerInstance;
    }
    public SolarGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.SOLAR_GENERATOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state,SolarGeneratorBlockEntity blockEntity) {
            if (world.isSkyVisible(pos.up()) && world.isDay() && (!world.isRaining())) {
                if (blockEntity.copperNetworkAPI().canGenerate(1)) {
                    blockEntity.copperNetworkAPI().generate(1);
                }
            }
            blockEntity.copperNetworkAPI().cleanupNetwork();
    }
}
