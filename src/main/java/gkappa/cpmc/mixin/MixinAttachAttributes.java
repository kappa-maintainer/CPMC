package gkappa.cpmc.mixin;

import com.artemis.artemislib.compatibilities.sizeCap.ISizeCap;
import com.artemis.artemislib.util.AttachAttributes;
import com.fuzs.aquaacrobatics.entity.player.IPlayerResizeable;
import com.github.gamepiaynmo.custommodel.client.CustomModelClient;
import com.github.gamepiaynmo.custommodel.client.ModelPack;
import com.github.gamepiaynmo.custommodel.client.render.EntityDimensions;
import com.github.gamepiaynmo.custommodel.client.render.EntityPose;
import com.github.gamepiaynmo.custommodel.server.CustomModel;
import com.github.gamepiaynmo.custommodel.server.ModConfig;
import com.github.gamepiaynmo.custommodel.server.ModelInfo;
import gkappa.cpmc.CPMC;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static gkappa.cpmc.util.PlayerStatureHandler.getPose;

@Mixin(value = {AttachAttributes.class}, remap = false)
public class MixinAttachAttributes {
    @Inject(method = "onPlayerTick", at = @At(value = "INVOKE", ordinal = 0,
            target = "Lcom/artemis/artemislib/compatibilities/sizeCap/ISizeCap;setTrans(Z)V"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    public void setCPMSize(TickEvent.PlayerTickEvent event, CallbackInfo ci, EntityPlayer player, ISizeCap cap, boolean hasHeightModifier, boolean hasWidthModifier, double heightAttribute, double widthAttribute, float height, float width){
        EntityPose pose = getPose(player);
        if(event.side == Side.CLIENT){
            ModelPack pack = CustomModelClient.manager.getModelForPlayer((AbstractClientPlayer) player);
            if(pack != null){
                if (CustomModelClient.serverConfig.customBoundingBox) {
                    EntityDimensions dimensions = pack.getModel().getModelInfo().dimensionsMap.get(pose);
                    if (dimensions != null){
                        cap.setDefaultHeight(dimensions.height);
                        cap.setDefaultWidth(dimensions.width);
                    }
                }
            }
        }else{
            ModelInfo pack = CustomModel.manager.getModelForPlayer(player);
            if(pack != null){
                if (CustomModelClient.serverConfig.customBoundingBox) {
                    EntityDimensions dimensions = pack.dimensionsMap.get(pose);
                    if (dimensions != null){
                        cap.setDefaultHeight(dimensions.height);
                        cap.setDefaultWidth(dimensions.width);
                    }
                }
            }
        }
    }

    @Redirect(method = "onPlayerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getDefaultEyeHeight()F"), expect = 2)
    public float redirectGetEyeHeight(EntityPlayer player){
        float factor = 1;
        if (((IPlayerResizeable)player).isVisuallySwimming()) {
            factor /= 3.0;
        }
        if(player instanceof AbstractClientPlayer){
            ModelPack pack = CustomModelClient.manager.getModelForPlayer((AbstractClientPlayer) player);
            if (pack != null) {
                if (CustomModelClient.serverConfig.customEyeHeight) {
                    return pack.getModel().getModelInfo().eyeHeightMap.get(EntityPose.STANDING)*factor;
                }
            }
        } else {
            ModelInfo pack = CustomModel.manager.getModelForPlayer(player);
            if (pack != null) {
                if (ModConfig.isCustomEyeHeight()) {
                    Float eyeHeight = pack.eyeHeightMap.get(EntityPose.STANDING);
                    if (eyeHeight != null)
                        return eyeHeight*factor;
                }

            }
        }
        return player.getDefaultEyeHeight()*factor;
    }
}
