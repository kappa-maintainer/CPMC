package gkappa.cpmc.mixin;

import com.github.gamepiaynmo.custommodel.client.render.CustomJsonModel;
import com.github.gamepiaynmo.custommodel.mixin.RenderPlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {CustomJsonModel.class}, remap = false)
public class MixinCustomJsonModel {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/RenderPlayerHandler;tick(Lnet/minecraft/entity/EntityLivingBase;)V"))
    public void redirectTick(EntityLivingBase playerEntity) {
        RenderPlayerHandler.tick(playerEntity);
    }
}
