package entity0.coppergenerators.mixin;

import entity0.coppergenerators.CopperGenerators;
import entity0.coppernetworks.CopperNetworkPowerClass;
import entity0.coppernetworks.copperNetworkPowerAPI;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceMixin implements copperNetworkPowerAPI {
	CopperNetworkPowerClass copperPowerInstance = new CopperNetworkPowerClass();
	@Override
	public CopperNetworkPowerClass copperNetworkAPI() {
		return copperPowerInstance;
	}
	@Inject(at = @At("TAIL"), method = "tick")
	private static void tick(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo info) {
		if (((copperNetworkPowerAPI) blockEntity).copperNetworkAPI().canGenerate(1)) {
			((copperNetworkPowerAPI) blockEntity).copperNetworkAPI().generate(1);
		}
		((copperNetworkPowerAPI) blockEntity).copperNetworkAPI().cleanupNetwork();
		CopperGenerators.LOGGER.info("Hello");


	}

}
