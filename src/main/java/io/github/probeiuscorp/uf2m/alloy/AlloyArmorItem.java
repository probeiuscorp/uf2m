package io.github.probeiuscorp.uf2m.alloy;

import com.mojang.logging.LogUtils;
import io.github.probeiuscorp.uf2m.UF2M;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public class AlloyArmorItem extends ArmorItem {
    public AlloyArmorItem(ArmorItem.Type type) {
        super(ArmorMaterials.IRON, type, new Item.Properties());
    }

    @Mod.EventBusSubscriber(modid = UF2M.MODID)
    public class AlloyArmorItemListener {
        private static final Logger LOGGER = LogUtils.getLogger();

        private static final Map<EquipmentSlot, Float> EQUIPMENT_PROTECTION_RATIOS = Map.of(
                EquipmentSlot.HEAD, .15f,
                EquipmentSlot.CHEST, .4f,
                EquipmentSlot.LEGS, .3f,
                EquipmentSlot.FEET, .15f
        );

        @SubscribeEvent
        public static void onLevelTick(TickEvent.LevelTickEvent event) {
            if(event.phase != TickEvent.Phase.START || event.isCanceled()) {
                return;
            }

            Level level = event.level;
            List<? extends Player> players = level.players();
            for(Player player : players) {
                DamageSource source = player.damageSources().onFire();
                player.hurt(source, 0.5f);
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if(event.isCanceled()) {
                return;
            }

            Entity entity = event.getEntity();
            DamageSource source = event.getSource();
            if(source.is(DamageTypeTags.IS_FIRE)) {
                event.setCanceled(true);
            }

            float damage = event.getAmount();
            for(ItemStack stack : entity.getArmorSlots()) {
                if(stack.getItem() instanceof AlloyArmorItem item) {
                    EquipmentSlot slot = item.getEquipmentSlot();
                    float protectionRatio = EQUIPMENT_PROTECTION_RATIOS.get(slot);
                    float amountProtected = damage * protectionRatio;
                    damage = damage - amountProtected;
                }
            }
            event.setAmount(damage);
        }

//        @SubscribeEvent
//        public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
//            LOGGER.info("Jumped!");
//        }
    }
}
