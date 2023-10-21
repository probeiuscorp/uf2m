package io.github.probeiuscorp.uf2m.data.dimension.meridian;

import io.github.probeiuscorp.uf2m.data.DBP;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeMeridianMidnightZone {
    public static Biome createMeridianMidnightZoneBiome(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(1.0f)
                .downfall(0.0f)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .fogColor(0x282847)
                                .skyColor(0x282847)
                                .waterColor(0x3f76e4)
                                .waterFogColor(0x050533)
                                .grassColorOverride(0xc7bead)
                                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                                .build()
                )
                .mobSpawnSettings(
                        new MobSpawnSettings.Builder()
                                .build()
                )
                .generationSettings(
                        new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                                .build()
                )
                .build();
    }

    public static SurfaceRules.RuleSource meridianMidnightZoneSurface() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, DBP.block(Blocks.SNOW_BLOCK))
        );
    }
}
