package entity0.coppergenerators.blocks.entity;

import entity0.coppernetworks.API.CopperPowerAPI;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class ThermoPhotoVoltaicGeneratorBlockEntity extends BlockEntity implements CopperPowerAPI {
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

    public ThermoPhotoVoltaicGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.THERMO_PHOTOVOLTAIC_GENERATOR_BLOCK_ENTITY, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, ThermoPhotoVoltaicGeneratorBlockEntity blockEntity) {
        if (!world.isClient) {
            if (world.getDimension().ultrawarm() && world.getBlockState(pos.down()).isOf(Blocks.LAVA)) {
                blockEntity.generateIfCan(world.getServer(), 1);
            }
        }
    }
}
