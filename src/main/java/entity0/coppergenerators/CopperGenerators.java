package entity0.coppergenerators;

import entity0.coppergenerators.blocks.modBlocks;
import entity0.coppergenerators.blocks.entity.modBlockEntity;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopperGenerators implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("coppergenerators");
	public static final String MOD_ID = "coppergenerators";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initialising coppergenerators.");
		modBlocks.initialize();
		modBlockEntity.registerBlockEntities();
	}
}