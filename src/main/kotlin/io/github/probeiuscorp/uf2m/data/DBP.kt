package io.github.probeiuscorp.uf2m.data

import io.github.probeiuscorp.uf2m.UF2M
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource

/**
 * Deboilerplate this god forsaken language
 */
object DBP {
    @JvmStatic
    fun createNoiseKey(key: String): ResourceKey<NoiseGeneratorSettings> {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation(UF2M.MODID, key))
    }

    @JvmStatic
    fun createBiomeKey(key: String): ResourceKey<Biome> {
        return ResourceKey.create(Registries.BIOME, ResourceLocation(UF2M.MODID, key))
    }

    @JvmStatic
    fun block(block: Block): RuleSource {
        return SurfaceRules.state(block.defaultBlockState())
    }
}