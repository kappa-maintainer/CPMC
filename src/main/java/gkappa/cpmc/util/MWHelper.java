package gkappa.cpmc.util;

import com.modularwarfare.api.AnimationUtils;
import com.modularwarfare.common.backpacks.ItemBackpack;
import com.modularwarfare.common.guns.ItemAttachment;
import com.modularwarfare.common.type.BaseItem;
import com.modularwarfare.common.type.BaseType;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.item.ItemStack;

public class MWHelper {
    public static void handleMWItem(ItemStack itemstack, ModelPlayer playerEntityModel_1, AbstractClientPlayer abstractClientPlayerEntity_1) {
        if (itemstack != ItemStack.EMPTY && !itemstack.isEmpty()) {
            if (!(itemstack.getItem() instanceof BaseItem)) {
                return;
            }
            BaseType type = ((BaseItem) itemstack.getItem()).baseType;
            if (!type.hasModel()) {
                return;
            }
            if (itemstack.getItem() instanceof ItemAttachment) {
                return;
            }
            if (itemstack.getItem() instanceof ItemBackpack) {
                return;
            }


            if (type.id == 1) {
                if (AnimationUtils.isAiming.containsKey((abstractClientPlayerEntity_1).getName())) {
                    playerEntityModel_1.rightArmPose = ArmPose.BOW_AND_ARROW;
                } else {
                    playerEntityModel_1.rightArmPose = ArmPose.BLOCK;
                    playerEntityModel_1.leftArmPose = ArmPose.BLOCK;
                }
            } else {
                playerEntityModel_1.rightArmPose = ArmPose.BLOCK;
            }
        }
    }
}