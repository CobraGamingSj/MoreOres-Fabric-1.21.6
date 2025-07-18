package net.cobra.moreores.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EnergyIngotItem extends Item {
    public EnergyIngotItem(Settings settings) {
        super(settings);
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();

        if(!world.isClient()) {

            if(!attacker.isInCreativeMode()) {
                if (stack.getDamage() < stack.getMaxDamage()) {
                    stack.setDamage(stack.getDamage() + 1);
                }
            }

            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.setPos(target.getX(), target.getY(), target.getZ());
            world.spawnEntity(lightning);
        }

        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 4800, 4));
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 40, 4));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BRUSH;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 9600, 4, false, false, false));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 9600, 4, false, false, false));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 9600, 4, false, false, false));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 9600, 4, false, false, false));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 9600, 4, false, false, false));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 9600, 4, false, false, false));
            if(!user.isInCreativeMode()) {
                if (stack.getDamage() < stack.getMaxDamage()) {
                    stack.setDamage(stack.getDamage() + 1);
                }
            }
        } else if(!world.isClient() && hand == Hand.OFF_HAND && Screen.hasControlDown()) {
            EntityType<LightningEntity> lightningType = EntityType.LIGHTNING_BOLT;
            LightningEntity lightning = new LightningEntity(lightningType, world);
            lightning.setPos(user.getX(), user.getY(), user.getZ());
            world.spawnEntity(lightning);

            user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 60));
            user.clearStatusEffects();
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.PLAYERS, 2.0f, 1.0f);
        }

        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 2.0f, 1.0f);
        }

        return super.use(world, user, hand);
    }
}