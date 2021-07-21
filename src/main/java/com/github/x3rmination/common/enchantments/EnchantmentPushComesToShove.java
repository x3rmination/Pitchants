package com.github.x3rmination.common.enchantments;

import com.github.x3rmination.init.EnchantmentInit;
import com.github.x3rmination.pitchants;
import net.minecraft.enchantment.Enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentKnockback;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid=pitchants.MODID)
public class EnchantmentPushComesToShove extends Enchantment {

    private static int hitcount = 0;
    public EnchantmentPushComesToShove() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.BOW, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName("push_comes_to_shove");
        this.setRegistryName(new ResourceLocation(pitchants.MODID + ":push_comes_to_shove"));

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

    @SubscribeEvent
    public void onKnockBack(LivingKnockBackEvent event) {
        System.out.println("knockbacked");
        if (event.getAttacker() instanceof EntityPlayer) {
            System.out.println("player");
            EntityPlayer player = (EntityPlayer) event.getAttacker();
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.PUSH_COMES_TO_SHOVE, player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));
            if (level > 0) {
                if(hitcount < 3) {
                    hitcount++;
                }
                if(hitcount >=3) {
                    hitcount=0;
                    double amountToAdd = (-0.6 * (Math.pow(level, 2)) + (4.2 * level) - 3);
                    System.out.println("amount" + amountToAdd);
                    event.setStrength((float) (event.getStrength() + amountToAdd));
                }

            }
        }
    }

}
