package io.github.probeiuscorp.uf2m.data;

import io.github.probeiuscorp.uf2m.UF2M;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;

/**
 * Deboilerplate this god forsaken language
 */
public class DBP {
    public static ResourceKey<NoiseGeneratorSettings> createNoiseKey(String key) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(UF2M.MODID, key));
    }

    public static ResourceKey<Biome> createBiomeKey(String key) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(UF2M.MODID, key));
    }

    public static SurfaceRules.RuleSource block(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}