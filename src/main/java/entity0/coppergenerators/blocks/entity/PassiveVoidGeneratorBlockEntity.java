package entity0.coppergenerators.blocks.entity;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppernetworks.API.CopperPowerAPI;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.UUID;

public class PassiveVoidGeneratorBlockEntity extends BlockEntity implements CopperPowerAPI {
    public PassiveVoidGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.PASSIVE_VOID_GENERATOR_BLOCK_ENTITY, pos, state);
    }
    UUID uuid;
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        boolean readvalid = nbt.getBoolean("isvalid");
        UUID readnbt = nbt.getUuid("netUUID");
        if (readvalid) {
            uuid = readnbt;
        } else {
            uuid = null;
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if (null != uuid) {
            nbt.putBoolean("isvalid", true);
            nbt.putUuid("netUUID", uuid);
        } else {
            nbt.putBoolean("isvalid", false);
            nbt.putUuid("netUUID", UUID.randomUUID());
        }
    }
    @Override
    public void setnetUUID(UUID netuuid) {
        uuid = netuuid;
    }
    @Override
    public UUID getnetUUID() {
        return uuid;
    }



    public static void tick(World world, BlockPos pos, BlockState state, PassiveVoidGeneratorBlockEntity blockEntity) {
        if (!world.isClient) {

            if (world.getRegistryKey().getValue().equals(DimensionTypes.THE_END_ID)) {
                if (overVoid(world, pos)) {
                    blockEntity.generateIfCan(world.getServer(), 1);
                }
            }

        }
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
