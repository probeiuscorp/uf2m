package io.github.probeiuscorp.uf2m.data.dimension.cauldron

import io.github.probeiuscorp.uf2m.UF2M
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DensityMeridian
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DimensionMeridian.MeridianBiomeSource
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.BiomeSource
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import java.util.*

object DimensionCauldron {
    private val CAULDRON_LEVEL_ID = ResourceLocation(UF2M.MODID, "cauldron")
    val CAULDRON_LEVEL_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, CAULDRON_LEVEL_ID)
    val CAULDRON_LEVEL = ResourceKey.create(Registries.DIMENSION, CAULDRON_LEVEL_ID)
    val CAULDRON_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, CAULDRON_LEVEL_ID)

    fun bootstrapDimensionType(context: BootstapContext<DimensionType>) {
        context.register(
            CAULDRON_LEVEL_TYPE, DimensionType(
                OptionalLong.empty(),
                false,
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
                CAULDRON_LEVEL_ID,
                0.0f,
                MonsterSettings(false, false, UniformInt.of(0, 7), 0)
            )
        )
    }

    fun bootstrapStem(context: BootstapContext<LevelStem>) {
        val biomes = context.lookup(Registries.BIOME)
        val noiseSettings = context.lookup(Registries.NOISE_SETTINGS)
        val dimensionTypes = context.lookup(Registries.DIMENSION_TYPE)
        val source: BiomeSource = MeridianBiomeSource.Companion.create(biomes)
        val meridianChunkGen = NoiseBasedChunkGenerator(
            source,
            noiseSettings.getOrThrow(DensityMeridian.MERIDIAN_NOISE)
        )
        context.register(
            CAULDRON_LEVEL_STEM,
            LevelStem(dimensionTypes.getOrThrow(CAULDRON_LEVEL_TYPE), meridianChunkGen)
        )
    }
}
