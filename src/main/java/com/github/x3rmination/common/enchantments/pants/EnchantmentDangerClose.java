package com.github.x3rmination.common.enchantments.pants;

import com.github.x3rmination.init.EnchantmentInit;
import com.github.x3rmination.pitchants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid=pitchants.MODID)
public class EnchantmentDangerClose extends Enchantment {

    private boolean isReady = true;
    private int coolDown = 100;
    public EnchantmentDangerClose() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
        this.setName("danger_close");
        this.setRegistryName(new ResourceLocation(pitchants.MODID + ":danger_close"));
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
    public void onTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DANGER_CLOSE, player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
        if (level > 0 && event.player.getHealth() <= 8 && isReady) {
            coolDown = 100;
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, (3*level)*20, 2, true, true));
            isReady = false;
            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    isReady = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}