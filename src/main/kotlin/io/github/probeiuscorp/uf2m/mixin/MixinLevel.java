package io.github.probeiuscorp.uf2m.mixin;

import io.github.probeiuscorp.uf2m.data.dimension.meridian.DimensionMeridian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Level.class)
public abstract class MixinLevel implements LevelAccessor, AutoCloseable, IForgeLevel {
  @Shadow
  private ResourceKey<DimensionType> dimensionTypeId;

  @Override
  public float getTimeOfDay(float p_46943_) {
    if(!this.dimensionTypeId.equals(DimensionMeridian.INSTANCE.getMERIDIAN_LEVEL_TYPE()))
      return this.dimensionType().timeOfDay(this.dayTime());;

    LocalPlayer player = Minecraft.getInstance().player;
    if(player == null) return 0;

    Vec3 position = player.position();
    double distance = position.distanceTo(new Vec3(0, position.y, 0));

    double midnight = 0.75;
    double night = 0.3;
    double twilight = 0.257;
    double sunset = 0.252;
    if(distance < 1000) {
      return linear(distance, 0, 1000, 0, sunset);
    } else if(distance < 1250) {
      return linear(distance, 1000, 1250, sunset, twilight);
    } else if(distance < 3800) {
      return linear(distance, 1250, 1900, twilight, night);
    } else {
      return linear(distance, 1900, 3000, night, midnight);
    }
  }

  private float linear(double distance, double x1, double x2, double y1, double y2) {
    return (float) Math.min(((distance - x1) / (x2 - x1)) * (y2 - y1) + y1, 1);
  }
}
