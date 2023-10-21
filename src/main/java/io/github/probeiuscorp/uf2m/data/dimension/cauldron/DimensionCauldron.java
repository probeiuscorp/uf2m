package io.github.probeiuscorp.uf2m.data.dimension.cauldron;

import io.github.probeiuscorp.uf2m.UF2M;
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DensityMeridian;
import io.github.probeiuscorp.uf2m.data.dimension.meridian.DimensionMeridian;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class DimensionCauldron {
    private static final ResourceLocation CAULDRON_LEVEL_ID = new ResourceLocation(UF2M.MODID, "cauldron");
    public static final ResourceKey<DimensionType> CAULDRON_LEVEL_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, CAULDRON_LEVEL_ID);
    public static final ResourceKey<Level> CAULDRON_LEVEL = ResourceKey.create(Registries.DIMENSION, CAULDRON_LEVEL_ID);
    public static final ResourceKey<LevelStem> CAULDRON_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, CAULDRON_LEVEL_ID);

    public static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
        context.register(CAULDRON_LEVEL_TYPE, new DimensionType(
                OptionalLong.empty(),
                false,
                false,
                false,
                true,
                1.0D,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                CAULDRON_LEVEL_ID,
                0.0F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        BiomeSource source = DimensionMeridian.MeridianBiomeSource.create(biomes);
        NoiseBasedChunkGenerator meridianChunkGen = new NoiseBasedChunkGenerator(
                source,
                noiseSettings.getOrThrow(DensityMeridian.MERIDIAN_NOISE)
        );
        context.register(CAULDRON_LEVEL_STEM, new LevelStem(dimensionTypes.getOrThrow(CAULDRON_LEVEL_TYPE), meridianChunkGen));
    }
}
