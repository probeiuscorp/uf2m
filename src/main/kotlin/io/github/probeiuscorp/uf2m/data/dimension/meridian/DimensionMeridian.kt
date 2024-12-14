package io.github.probeiuscorp.uf2m.data.dimension.meridian

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.probeiuscorp.uf2m.UF2M
import io.github.probeiuscorp.uf2m.data.UF2MBiomes
import net.minecraft.core.*
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.resources.RegistryOps
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeSource
import net.minecraft.world.level.biome.Climate.Sampler
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import java.util.*
import java.util.function.BiFunction
import java.util.stream.Stream

object DimensionMeridian {
  private val MERIDIAN_LEVEL_ID = ResourceLocation(UF2M.MODID, "meridian")
  val MERIDIAN_LEVEL_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, MERIDIAN_LEVEL_ID)
  val MERIDIAN_LEVEL = ResourceKey.create(Registries.DIMENSION, MERIDIAN_LEVEL_ID)
  val MERIDIAN_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, MERIDIAN_LEVEL_ID)

  fun bootstrapDimensionType(context: BootstapContext<DimensionType>) {
    context.register(
      MERIDIAN_LEVEL_TYPE, DimensionType(
        OptionalLong.empty(),
        true,
        false,
        false,
        true,
        1.0,
        true,
        false,
        0,
        256,
        256,
        BlockTags.INFINIBURN_OVERWORLD,
        MERIDIAN_LEVEL_ID,
        0.0f,
        MonsterSettings(false, false, UniformInt.of(0, 7), 0)
      )
    )
  }

  fun bootstrapStem(context: BootstapContext<LevelStem>) {
    val biomes = context.lookup(Registries.BIOME)
    val noiseSettings = context.lookup(Registries.NOISE_SETTINGS)
    val dimensionTypes = context.lookup(Registries.DIMENSION_TYPE)
    val source: BiomeSource = MeridianBiomeSource.create(biomes)
    val meridianChunkGen = NoiseBasedChunkGenerator(
      source,
      noiseSettings.getOrThrow(DensityMeridian.MERIDIAN_NOISE)
    )
    context.register(
      MERIDIAN_LEVEL_STEM,
      LevelStem(dimensionTypes.getOrThrow(MERIDIAN_LEVEL_TYPE), meridianChunkGen)
    )
  }

  fun bootstrapBiomeSource() {
    Registry.register(
      BuiltInRegistries.BIOME_SOURCE,
      ResourceLocation(UF2M.MODID, "meridian_biome_source"),
      MeridianBiomeSource.CODEC
    )
  }

  class MeridianBiomeSource private constructor(
    private val wastes: Holder<Biome>,
    private val midnightZone: Holder<Biome>
  ) : BiomeSource() {
    override fun codec(): Codec<out BiomeSource> {
      return CODEC
    }

    override fun collectPossibleBiomes(): Stream<Holder<Biome>> {
      return Stream.of(
        this.wastes,
        this.midnightZone
      )
    }

    override fun getNoiseBiome(x: Int, y: Int, z: Int, sampler: Sampler): Holder<Biome> {
      val i = QuartPos.toBlock(x)
      val k = QuartPos.toBlock(z)
      val l = SectionPos.blockToSectionCoord(i)
      val i1 = SectionPos.blockToSectionCoord(k)
      val radius = l.toLong() * l.toLong() + i1.toLong() * i1.toLong()
      return if (radius <= 4096L) {
        wastes
      } else {
        midnightZone
      }
    }

    companion object {
      val CODEC: Codec<MeridianBiomeSource> =
        RecordCodecBuilder.create { builder: RecordCodecBuilder.Instance<MeridianBiomeSource> ->
          builder.group(
            RegistryOps.retrieveElement(UF2MBiomes.MERIDIAN_WASTES),
            RegistryOps.retrieveElement(UF2MBiomes.MERIDIAN_MIDNIGHT_ZONE)
          ).apply(
            builder,
            builder.stable(
              BiFunction { wastes: Holder.Reference<Biome>, midnightZone: Holder.Reference<Biome> ->
                MeridianBiomeSource(
                  wastes,
                  midnightZone
                )
              })
          )
        }

      fun create(biomes: HolderGetter<Biome>): MeridianBiomeSource {
        return MeridianBiomeSource(
          biomes.getOrThrow(UF2MBiomes.MERIDIAN_WASTES),
          biomes.getOrThrow(UF2MBiomes.MERIDIAN_MIDNIGHT_ZONE)
        )
      }
    }
  }
}
