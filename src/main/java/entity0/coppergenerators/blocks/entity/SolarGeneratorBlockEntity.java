package entity0.coppergenerators.blocks.entity;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppernetworks.API.CopperPowerAPI;
import entity0.coppernetworks.CopperBlockanblockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.UUID;

public class SolarGeneratorBlockEntity extends BlockEntity implements CopperPowerAPI {
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


    public SolarGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.SOLAR_GENERATOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state,SolarGeneratorBlockEntity blockEntity) {
        //TODO make sure EVERYTHING else checks if its on the client
        if (!world.isClient) {
            if (world.isSkyVisible(pos.up()) && world.isDay() && (!world.isRaining())) {
                blockEntity.generateIfCan(world.getServer(), 1);
            }
        }
    }
}
