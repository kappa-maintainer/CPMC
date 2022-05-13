package gkappa.cpmc.mixin;

import gkappa.cpmc.util.RenderPlayerHandler;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({RenderPlayer.class})
public class MixinRenderPlayer {

    @Dynamic
    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/RenderPlayerHandler;renderModel(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)Z"))
    private boolean redirectRenderModel(EntityLivingBase paramEntityLivingBase, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6){
        return RenderPlayerHandler.renderModel(paramEntityLivingBase, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5,  paramFloat6);
    }

    @Dynamic
    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/RenderPlayerHandler;renderRightArm(Lnet/minecraft/client/entity/AbstractClientPlayer;)Z"))
    private boolean redirectRenderRightArm(AbstractClientPlayer player){
        return RenderPlayerHandler.renderRightArm(player);
    }

    @Dynamic
    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lcom/github/gamepiaynmo/custommodel/mixin/RenderPlayerHandler;renderLeftArm(Lnet/minecraft/client/entity/AbstractClientPlayer;)Z"))
    private boolean redirectRenderLeftArm(AbstractClientPlayer player){
        return RenderPlayerHandler.renderLeftArm(player);
    }

    /*
    @Shadow
    public ModelPlayer getMainModel(){return null;}
    @Shadow
    private void setModelVisibilities(AbstractClientPlayer clientPlayer){}

    @Inject(method = "renderRightArm", at = @At("HEAD"), cancellable = true)
    public void renderCPMRightArm(AbstractClientPlayer clientPlayer, CallbackInfo ci){
        if(RenderPlayerHandler.renderRightArm(clientPlayer)){
            ci.cancel();
        }
        float f = 1.0F;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        float f1 = 0.0625F;
        ModelPlayer modelplayer = this.getMainModel();
        this.setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.swingProgress = 0.0F;
        modelplayer.isSneak = false;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
        modelplayer.bipedRightArm.render(0.0625F);
        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedRightArmwear.render(0.0625F);
        GlStateManager.disableBlend();
        ci.cancel();
    }

    @Inject(method = "renderLeftArm", at = @At("HEAD"), cancellable = true)
    public void renderCPMLeftArm(AbstractClientPlayer clientPlayer, CallbackInfo ci){
        if(RenderPlayerHandler.renderLeftArm(clientPlayer)){
            ci.cancel();
        }
        float f = 1.0F;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        float f1 = 0.0625F;
        ModelPlayer modelplayer = this.getMainModel();
        this.setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.isSneak = false;
        modelplayer.swingProgress = 0.0F;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArm.render(0.0625F);
        modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }*/




}
