package io.github.probeiuscorp.uf2m.alloy

import com.mojang.logging.LogUtils
import io.github.probeiuscorp.uf2m.UF2M
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterials
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.LevelTickEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import org.slf4j.Logger

class AlloyArmorItem(type: Type) : ArmorItem(ArmorMaterials.IRON, type, Properties()) {
    @EventBusSubscriber(modid = UF2M.MODID)
    object AlloyArmorItemListener {
        private val LOGGER: Logger = LogUtils.getLogger()

        private val EQUIPMENT_PROTECTION_RATIOS: Map<EquipmentSlot, Float> = java.util.Map.of(
            EquipmentSlot.HEAD, .15f,
            EquipmentSlot.CHEST, .4f,
            EquipmentSlot.LEGS, .3f,
            EquipmentSlot.FEET, .15f
        )

        @SubscribeEvent
        fun onLevelTick(event: LevelTickEvent) {
            if (event.phase != TickEvent.Phase.START || event.isCanceled) {
                return
            }

            val level = event.level
            val players = level.players()
            for (player in players) {
                val source = player.damageSources().onFire()
                player.hurt(source, 0.5f)
            }
        }

        @SubscribeEvent
        fun onLivingHurt(event: LivingHurtEvent) {
            if (event.isCanceled) {
                return
            }

            val entity: Entity = event.entity
            val source = event.source
            if (source.`is`(DamageTypeTags.IS_FIRE)) {
                event.isCanceled = true
            }

            var damage = event.amount
            for (stack in entity.armorSlots) {
                val item = stack.item
                if (item is AlloyArmorItem) {
                    val slot: EquipmentSlot = item.getEquipmentSlot()
                    val protectionRatio = EQUIPMENT_PROTECTION_RATIOS[slot]!!
                    val amountProtected = damage * protectionRatio
                    damage = damage - amountProtected
                }
            }
            event.amount = damage
        } //        @SubscribeEvent
        //        public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        //            LOGGER.info("Jumped!");
        //        }
    }
}
