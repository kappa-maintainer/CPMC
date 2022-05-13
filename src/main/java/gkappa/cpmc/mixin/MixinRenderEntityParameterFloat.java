package gkappa.cpmc.mixin;

import com.github.gamepiaynmo.custommodel.client.render.EntityPose;
import com.github.gamepiaynmo.custommodel.expression.RenderEntityParameterFloat;
import gkappa.cpmc.util.PlayerStatureHandler;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {RenderEntityParameterFloat.class}, remap = false)
public class MixinRenderEntityParameterFloat {
    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/PlayerStatureHandler;getPose(Lnet/minecraft/entity/EntityLivingBase;)Lcom/github/gamepiaynmo/custommodel/client/render/EntityPose;"))
    private static EntityPose redirectGetPose(EntityLivingBase entity){
        return PlayerStatureHandler.getPose(entity);
    }
}
