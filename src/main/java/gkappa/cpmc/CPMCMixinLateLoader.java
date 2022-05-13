package gkappa.cpmc;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;

public class CPMCMixinLateLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Lists.newArrayList("artemis.mixin.json", "mwcompact.mixin.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        if(mixinConfig.equals("artemis.mixin.json")) {
            return Loader.isModLoaded("artemislib");
        }
        if(mixinConfig.equals("mwcompact.mixin.json")) {
            return Loader.isModLoaded("modularwarfare");
        }
        return false;
    }
}
