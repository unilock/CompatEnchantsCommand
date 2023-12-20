package cc.unilock.compatenchants.proxy;

import cc.unilock.compatenchants.CompatEnchantsCommand;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ClientCommandHandler.instance.registerCommand(new CompatEnchantsCommand());
    }
}
