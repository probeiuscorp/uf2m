package io.github.probeiuscorp.uf2m.data

import io.github.probeiuscorp.uf2m.data.DBP.createBiomeKey
import io.github.probeiuscorp.uf2m.data.dimension.meridian.BiomeMeridianMidnightZone
import io.github.probeiuscorp.uf2m.data.dimension.meridian.BiomeMeridianWastes
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome

object UF2MBiomes {
  @JvmField
  val MERIDIAN_WASTES = createBiomeKey("meridian_wastes")
  @JvmField
  val MERIDIAN_MIDNIGHT_ZONE = createBiomeKey("meridian_midnight_zone")

  fun bootstrap(context: BootstapContext<Biome>) {
    context.register(MERIDIAN_WASTES, BiomeMeridianWastes.createMeridianWastesBiome(context))
    context.register(MERIDIAN_MIDNIGHT_ZONE, BiomeMeridianMidnightZone.createMeridianMidnightZoneBiome(context))
  }
}
