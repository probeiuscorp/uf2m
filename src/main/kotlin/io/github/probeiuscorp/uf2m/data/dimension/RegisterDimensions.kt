package io.github.probeiuscorp.uf2m.data.dimension

import io.github.probeiuscorp.uf2m.data.dimension.cauldron.DimensionCauldron
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DensityMeridian
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DimensionMeridian
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings

object RegisterDimensions {
    fun bootstrapDimensionTypes(context: BootstapContext<DimensionType>) {
        DimensionMeridian.bootstrapDimensionType(context)
        DimensionCauldron.bootstrapDimensionType(context)
    }

    fun bootstrapStems(context: BootstapContext<LevelStem>) {
        DimensionMeridian.bootstrapStem(context)
        DimensionCauldron.bootstrapStem(context)
    }

    fun bootstrapBiomeSources() {
        DimensionMeridian.bootstrapBiomeSource()
    }

    fun bootstrapNoiseSettings(context: BootstapContext<NoiseGeneratorSettings>) {
        DensityMeridian.bootstrap(context)
    }
}
