package entity0.coppergenerators.mixin;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppernetworks.API.ItemCopperPowerAPI;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceMixin implements ItemCopperPowerAPI {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;setLastRecipe(Lnet/minecraft/recipe/RecipeEntry;)V", shift = At.Shift.AFTER), method = "tick")
	private static void tick2(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
		if (!world.isClient) {
			((ItemCopperPowerAPI) blockEntity).generateIfCan(world.getServer(), 500, pos, (ServerWorld) world);
		}
	}


}
