package gkappa.cpmc.mixin;

import com.github.gamepiaynmo.custommodel.client.CustomModelClient;
import com.github.gamepiaynmo.custommodel.client.ModelPack;
import com.github.gamepiaynmo.custommodel.client.render.EntityDimensions;
import com.github.gamepiaynmo.custommodel.client.render.EntityPose;
import com.github.gamepiaynmo.custommodel.entity.CustomModelNpc;
import com.github.gamepiaynmo.custommodel.server.CustomModel;
import com.github.gamepiaynmo.custommodel.server.ModConfig;
import com.github.gamepiaynmo.custommodel.server.ModelInfo;
import gkappa.cpmc.util.PlayerStatureHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.entity.EntityCustomNpc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {CustomModelNpc.class},remap = false)
public class MixinCustomModelNpc extends EntityCustomNpc {



    public World world;

    public MixinCustomModelNpc(World world) {
        super(world);
    }

    @Shadow
    private float eyeHeight;
    @Shadow
    public EntityCustomNpc getParent(){return null;}

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo ci) {
        EntityPose pose = PlayerStatureHandler.getPose(((CustomModelNpc)(Object)this));

        if (world.isRemote) {
            ModelPack pack = CustomModelClient.manager.getModelForEntity(((CustomModelNpc)(Object)this));
            if (pack != null) {
                if (CustomModelClient.serverConfig.customEyeHeight) {
                    Float eyeHeight = pack.getModel().getModelInfo().eyeHeightMap.get(pose);
                    if (eyeHeight != null)
                        this.eyeHeight = eyeHeight;
                    else this.eyeHeight = 1.62f;
                }

                if (CustomModelClient.serverConfig.customBoundingBox) {
                    EntityDimensions dimensions = pack.getModel().getModelInfo().dimensionsMap.get(pose);
                    if (dimensions == null)
                        dimensions = PlayerStatureHandler.defaultDimensions.get(pose);
                    if (dimensions != null && (dimensions.width != width || dimensions.height != height)) {
                        PlayerStatureHandler.setSize(((CustomModelNpc)(Object)this), dimensions);
                        getParent().updateHitbox();
                    }
                }
            }
        } else {
            ModelInfo pack = CustomModel.manager.getModelForEntity(getParent().getUniqueID());
            if (pack != null) {
                if (ModConfig.isCustomEyeHeight()) {
                    Float eyeHeight = pack.eyeHeightMap.get(pose);
                    if (eyeHeight != null)
                        this.eyeHeight = eyeHeight;
                    else this.eyeHeight = 1.62f;
                }

                if (ModConfig.isCustomBoundingBox()) {
                    EntityDimensions dimensions = pack.dimensionsMap.get(pose);
                    if (dimensions == null)
                        dimensions = PlayerStatureHandler.defaultDimensions.get(pose);
                    if (dimensions != null && (dimensions.width != width || dimensions.height != height)) {
                        PlayerStatureHandler.setSize(((CustomModelNpc)(Object)this), dimensions);
                        getParent().updateHitbox();
                    }
                }
            }
        }
        ci.cancel();
    }


    @Override
    protected void entityInit() {

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {

    }
}
