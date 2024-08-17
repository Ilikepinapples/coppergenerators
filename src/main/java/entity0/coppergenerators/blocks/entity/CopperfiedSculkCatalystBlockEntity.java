package entity0.coppergenerators.blocks.entity;

import com.google.common.annotations.VisibleForTesting;
import entity0.coppernetworks.CopperNetworkPowerClass;
import entity0.coppernetworks.copperNetworkPowerAPI;
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
import net.minecraft.particle.ParticleTypes;
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

public class CopperfiedSculkCatalystBlockEntity extends BlockEntity implements copperNetworkPowerAPI, GameEventListener.Holder<CopperfiedSculkCatalystBlockEntity.Listener> {
    private final CopperfiedSculkCatalystBlockEntity.Listener eventListener;
    private int PowerBuffer;

    public CopperfiedSculkCatalystBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntity.COPPERFIED_SCULK_CATALYST, pos, state);
        this.eventListener = new Listener(state, new BlockPositionSource(pos));
    }
    CopperNetworkPowerClass copperPowerInstance = new CopperNetworkPowerClass();

    @Override
    public CopperNetworkPowerClass copperNetworkAPI() {
        return copperPowerInstance;
    }

    public static void tick(World world, BlockPos pos, BlockState state, CopperfiedSculkCatalystBlockEntity blockEntity) {
        if (blockEntity.copperNetworkAPI().canGenerate(blockEntity.PowerBuffer)) {
            blockEntity.copperNetworkAPI().generate(blockEntity.PowerBuffer);
        } else {
            blockEntity.copperNetworkAPI().setNetworkmaxPower();
        }
        blockEntity.PowerBuffer = 0;
        blockEntity.copperNetworkAPI().cleanupNetwork();
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
