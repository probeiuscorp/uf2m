package io.github.probeiuscorp.uf2m.data.dimension;

import io.github.probeiuscorp.uf2m.data.dimension.cauldron.DimensionCauldron;
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DensityMeridian;
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DimensionMeridian;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class RegisterDimensions {
    public static void bootstrapDimensionTypes(BootstapContext<DimensionType> context) {
        DimensionMeridian.bootstrapDimensionType(context);
        DimensionCauldron.bootstrapDimensionType(context);
    }

    public static void bootstrapStems(BootstapContext<LevelStem> context) {
        DimensionMeridian.bootstrapStem(context);
        DimensionCauldron.bootstrapStem(context);
    }

    public static void bootstrapBiomeSources() {
        DimensionMeridian.bootstrapBiomeSource();
    }

    public static void bootstrapNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        DensityMeridian.bootstrap(context);
    }
}
