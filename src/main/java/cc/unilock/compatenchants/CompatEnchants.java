package cc.unilock.compatenchants;

import cc.unilock.compatenchants.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class CompatEnchants {
    public static final Logger LOGGER = LogManager.getLogger(Tags.MODID);

    @SidedProxy(clientSide = "cc.unilock.compatenchants.proxy.ClientProxy", serverSide = "cc.unilock.compatenchants.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}
