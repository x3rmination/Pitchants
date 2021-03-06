package com.github.x3rmination.common.enchantments.sword.rare;

import com.github.x3rmination.Pitchants;
import com.github.x3rmination.init.EnchantmentInit;
import com.github.x3rmination.init.PotionInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class EnchantmentComboPerunsWrath extends Enchantment {

    private static boolean handled = false;
    private int hitCount = 0;

    public EnchantmentComboPerunsWrath() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setName("combo_peruns_wrath");
        this.setRegistryName(new ResourceLocation(Pitchants.MODID + ":combo_peruns_wrath"));
        EnchantmentInit.ENCHANTMENTS.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 20 * enchantmentLevel;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 10;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
        if (handled){
            handled = false;
            return;
        }
        if(!(user.isPotionActive(PotionInit.VENOM) || EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SOMBER, user.getItemStackFromSlot(EntityEquipmentSlot.LEGS)) > 0)) {
            hitCount += 1;
            int hitReq = (int) ((Math.pow(level, 2) * 0.5) - (2.5 * level) + 7);
            if (hitCount >= hitReq && !user.isSwingInProgress) {
                hitCount = 0;
                target.attackEntityFrom(EnchantmentInit.TRUE_DAMAGE, (float) (2.0 * level));
                user.world.addWeatherEffect(new EntityLightningBolt(user.world, target.posX, target.posY, target.posZ, true));
            }
        }
        handled = true;
    }
}
