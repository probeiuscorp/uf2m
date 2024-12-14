package io.github.probeiuscorp.uf2m.data.dimension.meridian

import io.github.probeiuscorp.uf2m.data.DBP.block
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Biome.BiomeBuilder
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeSpecialEffects
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource

object BiomeMeridianWastes {
  fun createMeridianWastesBiome(context: BootstapContext<Biome>): Biome {
    val placedFeatures = context.lookup(Registries.PLACED_FEATURE)
    val worldCarvers = context.lookup(Registries.CONFIGURED_CARVER)

    return BiomeBuilder()
      .hasPrecipitation(false)
      .temperature(1.0f)
      .downfall(0.0f)
      .specialEffects(
        BiomeSpecialEffects.Builder()
          .fogColor(0x9393bc)
          .skyColor(0xc0c0ff)
          .waterColor(0x3f76e4)
          .waterFogColor(0x050533)
          .grassColorOverride(0xc7bead)
          .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
          .build()
      )
      .mobSpawnSettings(
        MobSpawnSettings.Builder()
          .build()
      )
      .generationSettings(
        BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
          .build()
      )
      .build()
  }

  fun meridianWastesSurface(): RuleSource {
    return SurfaceRules.sequence(
      SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, block(Blocks.SAND)),
      SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, block(Blocks.SANDSTONE))
    )
  }
}
