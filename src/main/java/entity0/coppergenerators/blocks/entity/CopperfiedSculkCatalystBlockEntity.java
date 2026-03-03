package entity0.coppergenerators.blocks.entity;

import com.google.common.annotations.VisibleForTesting;
import entity0.coppernetworks.API.CopperPowerAPI;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkCatalystBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkCatalystBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Nullables;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

import java.util.UUID;

public class CopperfiedSculkCatalystBlockEntity extends BlockEntity implements CopperPowerAPI, GameEventListener.Holder<CopperfiedSculkCatalystBlockEntity.Listener> {
    private final CopperfiedSculkCatalystBlockEntity.Listener eventListener;
    private long PowerBuffer;

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



    public CopperfiedSculkCatalystBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.COPPERFIED_SCULK_CATALYST, pos, state);
        this.eventListener = new Listener(state, new BlockPositionSource(pos));
    }


    public static void tick(World world, BlockPos pos, BlockState state, CopperfiedSculkCatalystBlockEntity blockEntity) {
        if (!world.isClient) {
            if (blockEntity.PowerBuffer > 0) {
                if (blockEntity.canGenerate(world.getServer(), blockEntity.PowerBuffer)) {
                    blockEntity.generate(world.getServer(), blockEntity.PowerBuffer);
                    blockEntity.PowerBuffer = 0;
                } else {
                    blockEntity.generateIfCan(world.getServer(), 1);
                    blockEntity.PowerBuffer--;
                }
            }
        }
    }

    @Override
    public Listener getEventListener() {
        return this.eventListener;
    }



    public class Listener implements GameEventListener { //this apparently does not need to be static
                                private final BlockState state;
                                private final PositionSource positionSource;

                                public Listener(BlockState state, PositionSource positionSource) {
                                    this.state = state;
                                    this.positionSource = positionSource;
                                }

                                public PositionSource getPositionSource() {
                                    return this.positionSource;
                                }

                                public int getRange() {
                                    return 8;
                                }


        @Override
        public GameEventListener.TriggerOrder getTriggerOrder() {
            return TriggerOrder.BY_DISTANCE;
        }


        public boolean listen(ServerWorld world, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d emitterPos) {
                                    if (event.matches(GameEvent.ENTITY_DIE)) {
                                        Entity var6 = emitter.sourceEntity();
                                        if (var6 instanceof LivingEntity) {
                                            LivingEntity livingEntity = (LivingEntity) var6;
                                            if (!livingEntity.isExperienceDroppingDisabled()) {
                                                DamageSource damageSource = livingEntity.getRecentDamageSource();
                                                int i = livingEntity.getXpToDrop(world, (Entity) Nullables.map(damageSource, DamageSource::getAttacker));
                                                if (livingEntity.shouldDropXp() && i > 0) {
                                                    PowerBuffer = (i * 150) + PowerBuffer;
                                                }
                                                livingEntity.disableExperienceDropping();
                                            }

                                            return true;
                                        }
                                    }
                                    return false;
                                }
    }
}
