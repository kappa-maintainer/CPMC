package gkappa.cpmc.mixin;

import com.fuzs.aquaacrobatics.entity.player.IPlayerResizeable;
import com.fuzs.aquaacrobatics.integration.IntegrationManager;
import com.fuzs.aquaacrobatics.integration.chiseledme.ChiseledMeIntegration;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static gkappa.cpmc.util.ArtemisHelper.getArtemisHeight;

@Mixin(value = {EntityPlayer.class}, priority = 2000)
public class MixinEntityPlayer {


    @Dynamic
    @Inject(method = "findEyeScaleFactor",remap = false , at = @At("HEAD"), cancellable = true)
    private void findEyeScaleFactor(CallbackInfoReturnable<Float> cir) {
        float finalFactor = 1f;
        if(((IPlayerResizeable) this).isVisuallySwimming()){
            double heightAttribute = ((EntityPlayer)(Object)this).getAttributeMap().getAttributeInstance(getArtemisHeight()).getAttributeValue();
            finalFactor *= heightAttribute * 3;
        }
        /*if((Object)this instanceof AbstractClientPlayer){
            ModelPack pack = CustomModelClient.manager.getModelForPlayer((AbstractClientPlayer)(Object)this);
            if(pack != null) {
                if (CustomModelClient.serverConfig.customEyeHeight) {
                    finalFactor *= pack.getModel().getModelInfo().eyeHeightMap.get(EntityPose.STANDING) / 1.62;

                }
            }
        } else {
            ModelInfo pack = CustomModel.manager.getModelForPlayer(((EntityPlayer)(Object)this));
            if (pack != null) {
                if (ModConfig.isCustomEyeHeight()) {
                    Float eyeHeight = pack.eyeHeightMap.get(EntityPose.STANDING);
                    if (eyeHeight != null)
                        finalFactor *= eyeHeight / 1.62;
                }

            }
        }*/
        if(IntegrationManager.isChiseledMeEnabled())
            finalFactor *= ChiseledMeIntegration.getResizeFactor((EntityPlayer)(Object)this);
        cir.setReturnValue(finalFactor);
    }

}
