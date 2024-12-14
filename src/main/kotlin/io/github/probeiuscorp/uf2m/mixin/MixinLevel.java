package io.github.probeiuscorp.uf2m.mixin;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.extensions.IForgeLevel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Level.class)
public abstract class MixinLevel implements LevelAccessor, AutoCloseable, IForgeLevel {
  @Override
  public float getTimeOfDay(float p_46943_) {
    return 0;
  }
}
