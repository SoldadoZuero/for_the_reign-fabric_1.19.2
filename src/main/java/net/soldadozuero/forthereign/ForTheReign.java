package net.soldadozuero.forthereign;

import net.fabricmc.api.ModInitializer;
import net.soldadozuero.forthereign.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForTheReign implements ModInitializer {
	public static final String MOD_ID = "forthereign";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModRegistries.registerModStuffs();
	}
}
