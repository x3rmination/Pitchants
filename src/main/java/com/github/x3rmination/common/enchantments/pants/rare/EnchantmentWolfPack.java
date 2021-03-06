package com.github.x3rmination.common.enchantments.pants.rare;

import com.github.x3rmination.Pitchants;
import com.github.x3rmination.init.EnchantmentInit;
import com.github.x3rmination.init.PotionInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

 


public class EnchantmentWolfPack extends Enchantment {

    private int wolfcount = 0;
    private int killcount = 0;
    private EntityWolf wolf;

    public EnchantmentWolfPack() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
        this.setName("wolf_pack");
        this.setRegistryName(new ResourceLocation(Pitchants.MODID + ":wolf_pack"));

        EnchantmentInit.ENCHANTMENTS.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 8 * enchantmentLevel;
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
    public void onDeath(LivingDeathEvent event) {
        if(event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer entityLiving = (EntityPlayer) event.getSource().getTrueSource();
            World world = event.getEntityLiving().getEntityWorld();
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WOLF_PACK, entityLiving.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
            if(level > 0 && wolfcount < (2 * level) + 3 && !(entityLiving.isPotionActive(PotionInit.VENOM) || EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SOMBER, entityLiving.getItemStackFromSlot(EntityEquipmentSlot.LEGS)) > 0)) {
                killcount+=1;
                if(killcount >= ((Math.pow(level, 2) * 0.5) - (2.5*level) + 6)){
                    killcount = 0;
                    wolf = new EntityWolf(world);
                    wolf.setDropItemsWhenDead(false);
                    wolf.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, 0.0F, 0.0F);
                    wolf.setOwnerId(entityLiving.getUniqueID());
                    wolf.setTamedBy(entityLiving);
                    wolf.setCollarColor(EnumDyeColor.RED);
                    world.spawnEntity(wolf);
                    wolfcount += 1;
                    wolf.setHealth(wolf.getMaxHealth());
                }
            }
        }
        if(event.getEntityLiving() == wolf) {
            wolfcount -= 1;
        }
    }
}
