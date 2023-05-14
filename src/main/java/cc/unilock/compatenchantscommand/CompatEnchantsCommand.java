package cc.unilock.compatenchantscommand;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompatEnchantsCommand implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("CompatEnchantsCommand");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}
