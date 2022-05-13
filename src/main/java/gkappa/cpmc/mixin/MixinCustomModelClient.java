package gkappa.cpmc.mixin;

import com.github.gamepiaynmo.custommodel.client.ClientModelManager;
import com.github.gamepiaynmo.custommodel.client.CustomModelClient;
import com.github.gamepiaynmo.custommodel.client.gui.GuiModelSelection;
import com.github.gamepiaynmo.custommodel.client.render.RenderNpc;
import com.github.gamepiaynmo.custommodel.entity.CustomModelFemaleNpc;
import com.github.gamepiaynmo.custommodel.entity.CustomModelMaleNpc;
import com.github.gamepiaynmo.custommodel.entity.NpcHelper;
import com.github.gamepiaynmo.custommodel.network.NetworkHandler;
import com.github.gamepiaynmo.custommodel.network.PacketList;
import com.github.gamepiaynmo.custommodel.server.CustomModel;
import gkappa.cpmc.util.RenderPlayerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;



@Mixin(value = {CustomModelClient.class}, remap = false)
public class MixinCustomModelClient {

/*
    @Redirect(method = "onInitializeClient", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;register(Ljava/lang/Object;)V"))
    private static void onInitializeClient(EventBus instance, Object eventType){
        instance.register(RenderPlayerHandler.class);
    }*/

    @Redirect(method = "initPlayerRenderer", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/RenderPlayerHandler;customize(Lnet/minecraft/client/renderer/entity/RenderPlayer;)V"))
    private static void redirectCustomize(RenderPlayer renderer){
        RenderPlayerHandler.customize(renderer);
    }

    @Redirect(method = "onWorldTick", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/RenderPlayerHandler;tick(Lnet/minecraft/entity/EntityLivingBase;)V"))
    private static void redirectTick(EntityLivingBase playerEntity) {
        RenderPlayerHandler.tick(playerEntity);
    }
}
