package com.github.x3rmination.common.enchantments.bow.rare;

import com.github.x3rmination.Pitchants;
import com.github.x3rmination.init.EnchantmentInit;
import com.github.x3rmination.init.PotionInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


 


public class EnchantmentLuckyShot extends Enchantment{
    public EnchantmentLuckyShot() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName("lucky_shot");
        this.setRegistryName(new ResourceLocation(Pitchants.MODID + ":lucky_shot"));

        EnchantmentInit.ENCHANTMENTS.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10 * enchantmentLevel;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) event.getSource().getTrueSource();
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MEGA_LONGBOW, entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));
            float reqAmount = (float) (Math.pow(level, 2) + 1);
            if (level > 0 && (Math.random()*100) < reqAmount && !(entityLivingBase.isPotionActive(PotionInit.VENOM) || EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SOMBER, entityLivingBase.getItemStackFromSlot(EntityEquipmentSlot.LEGS)) > 0)) {
                event.setAmount(event.getAmount()*4);
            }
        }
    }
}
