package io.github.probeiuscorp.uf2m.data.dimension.meridian;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.probeiuscorp.uf2m.UF2M;
import io.github.probeiuscorp.uf2m.data.UF2MBiomes;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;
import java.util.stream.Stream;

public class DimensionMeridian {
    private static final ResourceLocation MERIDIAN_LEVEL_ID = new ResourceLocation(UF2M.MODID, "meridian");
    public static final ResourceKey<DimensionType> MERIDIAN_LEVEL_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, MERIDIAN_LEVEL_ID);
    public static final ResourceKey<Level> MERIDIAN_LEVEL = ResourceKey.create(Registries.DIMENSION, MERIDIAN_LEVEL_ID);
    public static final ResourceKey<LevelStem> MERIDIAN_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, MERIDIAN_LEVEL_ID);

    public static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
        context.register(MERIDIAN_LEVEL_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
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
                MERIDIAN_LEVEL_ID,
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
        context.register(MERIDIAN_LEVEL_STEM, new LevelStem(dimensionTypes.getOrThrow(MERIDIAN_LEVEL_TYPE), meridianChunkGen));
    }

    public static void bootstrapBiomeSource() {
        Registry.register(BuiltInRegistries.BIOME_SOURCE, new ResourceLocation(UF2M.MODID, "meridian_biome_source"), MeridianBiomeSource.CODEC);
    }

    public static class MeridianBiomeSource extends BiomeSource {
        public static final Codec<DimensionMeridian.MeridianBiomeSource> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
                RegistryOps.retrieveElement(UF2MBiomes.MERIDIAN_WASTES),
                RegistryOps.retrieveElement(UF2MBiomes.MERIDIAN_MIDNIGHT_ZONE)
        ).apply(builder, builder.stable(DimensionMeridian.MeridianBiomeSource::new)));

        private final Holder<Biome> wastes;
        private final Holder<Biome> midnightZone;

        public static DimensionMeridian.MeridianBiomeSource create(HolderGetter<Biome> biomes) {
            return new DimensionMeridian.MeridianBiomeSource(
                    biomes.getOrThrow(UF2MBiomes.MERIDIAN_WASTES),
                    biomes.getOrThrow(UF2MBiomes.MERIDIAN_MIDNIGHT_ZONE)
            );
        }

        private MeridianBiomeSource(Holder<Biome> wastes, Holder<Biome> midnightZone) {
            this.wastes = wastes;
            this.midnightZone = midnightZone;
        }

        @Override
        protected Codec<? extends BiomeSource> codec() {
            return CODEC;
        }

        @Override
        protected Stream<Holder<Biome>> collectPossibleBiomes() {
            return Stream.of(
                    this.wastes,
                    this.midnightZone
            );
        }

        @Override
        public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
            int i = QuartPos.toBlock(x);
            int k = QuartPos.toBlock(z);
            int l = SectionPos.blockToSectionCoord(i);
            int i1 = SectionPos.blockToSectionCoord(k);
            long radius = (long)l * (long)l + (long)i1 * (long)i1;
            if(radius <= 4096L) {
                return this.wastes;
            } else {
                return this.midnightZone;
            }
        }
    }
}
