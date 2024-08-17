package entity0.coppergenerators.blocks;

import entity0.coppergenerators.blocks.entity.PassiveVoidGeneratorBlockEntity;
import entity0.coppergenerators.blocks.entity.SolarGeneratorBlockEntity;
import entity0.coppergenerators.blocks.entity.modBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkCatalystBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PassiveVoidGenerator extends Block implements BlockEntityProvider {
    public PassiveVoidGenerator(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PassiveVoidGeneratorBlockEntity(pos, state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, modBlockEntity.PASSIVE_VOID_GENERATOR_BLOCK_ENTITY, PassiveVoidGeneratorBlockEntity::tick);
    }

    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(
            BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker
    ) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
