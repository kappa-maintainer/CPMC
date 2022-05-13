package gkappa.cpmc.util;

import com.hbm.interfaces.IHoldableWeapon;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.item.ItemStack;

public class HBMHelper {
    public static void handleHBMItem(ItemStack mainitem, ItemStack offitem, ModelPlayer model) {
        if(mainitem.getItem() instanceof IHoldableWeapon) {
            model.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            // renderer.getMainModel().bipedLeftArm.rotateAngleY = 90;
        }
        if(offitem.getItem() instanceof IHoldableWeapon) {
            model.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
        }
    }
}
