package entity0.coppergenerators.blocks.entity;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppernetworks.CopperNetworkPowerClass;
import entity0.coppernetworks.copperNetworkPowerAPI;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

public class PassiveVoidGeneratorBlockEntity extends BlockEntity implements copperNetworkPowerAPI {
    public PassiveVoidGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.PASSIVE_VOID_GENERATOR_BLOCK_ENTITY, pos, state);
    }

    CopperNetworkPowerClass copperPowerInstance = new CopperNetworkPowerClass();
    @Override
    public CopperNetworkPowerClass copperNetworkAPI() {
        return copperPowerInstance;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PassiveVoidGeneratorBlockEntity blockEntity) {
        if (world.getRegistryKey().getValue().equals(DimensionTypes.THE_END_ID)) {
            if (overVoid(world, pos)) {
                if (blockEntity.copperNetworkAPI().canGenerate(1)) {
                    blockEntity.copperNetworkAPI().generate(1);
                }
            }

        }
        blockEntity.copperNetworkAPI().cleanupNetwork();
    }
    private static boolean overVoid(World world, BlockPos pos) {
        boolean isVoidBelow = true;
        for (BlockPos i = pos.down(); 0 < i.getY(); i = i.down()) {
            if (!world.getBlockState(i).isAir()) {
                isVoidBelow = false;
                break;
            }
        }
        return isVoidBelow;
    }
}
