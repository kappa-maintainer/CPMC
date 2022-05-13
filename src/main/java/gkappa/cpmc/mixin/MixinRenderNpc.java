package gkappa.cpmc.mixin;

import com.github.gamepiaynmo.custommodel.client.render.EntityParameter;
import com.github.gamepiaynmo.custommodel.client.render.RenderContext;
import com.github.gamepiaynmo.custommodel.client.render.RenderNpc;
import com.github.gamepiaynmo.custommodel.entity.CustomModelNpc;
import gkappa.cpmc.util.RenderPlayerHandler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.entity.EntityCustomNpc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = {RenderNpc.class}, remap = false)
public class MixinRenderNpc extends RenderLivingBase<CustomModelNpc> {

    @Shadow
    @Final
    private final ModelPlayer modelPlayer = null;

    public MixinRenderNpc(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/RenderPlayerHandler;getContext()Lcom/github/gamepiaynmo/custommodel/client/render/RenderContext;"))
    private static RenderContext redirectGetContext(){
        return RenderPlayerHandler.getContext();
    }

    @Inject(method = "doRender(Lcom/github/gamepiaynmo/custommodel/entity/CustomModelNpc;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void doRender(CustomModelNpc entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci){
        EntityParameter parameter = new EntityParameter(entity);
        EntityCustomNpc npc = entity.getParent();
        parameter.yaw = npc.rotationYaw;
        parameter.bodyYaw = npc.renderYawOffset;
        parameter.pitch = npc.rotationPitch;
        parameter.headYaw = npc.rotationYawHead;
        RenderPlayerHandler.renderPre(entity, modelPlayer, x, y, z, partialTicks, parameter);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        RenderPlayerHandler.renderPost();
        ci.cancel();
    }

    @Inject(method = "renderModel(Lcom/github/gamepiaynmo/custommodel/entity/CustomModelNpc;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    protected void renderModel(CustomModelNpc entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        RenderPlayerHandler.renderPre(entitylivingbaseIn, modelPlayer);
        if (!RenderPlayerHandler.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor))
            super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        ci.cancel();
    }

    @Nullable
    @Override
    @Shadow
    protected ResourceLocation getEntityTexture(CustomModelNpc entity) {
        return null;
    }
}
