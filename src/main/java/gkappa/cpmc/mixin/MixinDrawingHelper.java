package gkappa.cpmc.mixin;

import com.github.gamepiaynmo.custommodel.mixin.DrawEntityInventoryHandler;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xzeroair.trinkets.util.helpers.DrawingHelper;

@Mixin(value = {DrawingHelper.class}, remap = false)
public class MixinDrawingHelper {
    @Inject(method = "drawEntityOnScreen(IIIZFFFLnet/minecraft/entity/EntityLivingBase;)V", at = @At("HEAD"))
    private static void preDraw(int posX, int posY, int scale, boolean flip, float rotation, float mouseX, float mouseY, EntityLivingBase ent, CallbackInfo ci) {
        DrawEntityInventoryHandler.preDrawEntityInventory(ent);
    }

    @Inject(method = "drawEntityOnScreen(IIIZFFFLnet/minecraft/entity/EntityLivingBase;)V", at = @At("RETURN"))
    private static void postDraw(int posX, int posY, int scale, boolean flip, float rotation, float mouseX, float mouseY, EntityLivingBase ent, CallbackInfo ci) {
        DrawEntityInventoryHandler.postDrawEntityInventory();
    }
}
