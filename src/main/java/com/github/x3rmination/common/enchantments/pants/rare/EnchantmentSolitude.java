package com.github.x3rmination.common.enchantments.pants.rare;

import com.github.x3rmination.init.EnchantmentInit;
import com.github.x3rmination.init.PotionInit;
import com.github.x3rmination.Pitchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSolitude extends Enchantment {

    public EnchantmentSolitude() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
        this.setName("solitude");
        this.setRegistryName(new ResourceLocation(Pitchants.MODID + ":solitude"));

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
    public void onHit(LivingHurtEvent event) {
        if(event.getEntityLiving() != null) {
            EntityLivingBase entityLiving = event.getEntityLiving();
            World world = event.getEntityLiving().getEntityWorld();
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SOLITUDE, entityLiving.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
            if(level > 0 && !(entityLiving.isPotionActive(PotionInit.VENOM) || EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SOMBER, entityLiving.getItemStackFromSlot(EntityEquipmentSlot.LEGS)) > 0)) {
                AxisAlignedBB bounding = new AxisAlignedBB(entityLiving.getPosition().getX() - 3.5D, entityLiving.getPosition().getY() - 3.5D, entityLiving.getPosition().getZ() - 3.5D, entityLiving.getPosition().getX() + 3.5D, entityLiving.getPosition().getY() + 3.5D, entityLiving.getPosition().getZ() + 3.5D);
                int reqEntities = (int) ((Math.pow(level, 2)*(-0.5))+(2.5*level)-1);
                if(world.getEntitiesWithinAABB(EntityLivingBase.class, bounding).size() <= reqEntities) {
                    event.setAmount(event.getAmount()-(event.getAmount()*(30+(10*level))));
                }
            }
        }
    }
}
