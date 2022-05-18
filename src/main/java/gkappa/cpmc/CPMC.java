package gkappa.cpmc;

import com.github.gamepiaynmo.custommodel.mixin.PlayerStatureHandler;
import com.github.gamepiaynmo.custommodel.mixin.RenderPlayerHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = CPMC.MODID, name = CPMC.NAME, version = CPMC.VERSION, dependencies = "required:mixinbooter@[4.2,);after:custommodel")
public class CPMC {
    public static final String MODID = "cpmc";
    public static final String NAME = "CPMC";
    public static final String VERSION = "0.1.1";
    public static Logger logger;
    public static boolean hasCNPC = false;
    public static boolean hasMW = false;
    public static boolean hasHBM = false;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        logger = event.getModLog();
        if(Loader.isModLoaded("customnpcs")){
            hasCNPC = true;
        }
        if(Loader.isModLoaded("modularwarfare")){
            hasMW = true;
        }
        if(Loader.isModLoaded("hbm")){
            hasHBM = true;
        }
        MinecraftForge.EVENT_BUS.unregister(PlayerStatureHandler.class);
        MinecraftForge.EVENT_BUS.unregister(RenderPlayerHandler.class);

        MinecraftForge.EVENT_BUS.register(gkappa.cpmc.util.PlayerStatureHandler.class);
        MinecraftForge.EVENT_BUS.register(gkappa.cpmc.util.RenderPlayerHandler.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
