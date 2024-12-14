package io.github.probeiuscorp.uf2m.data

import io.github.probeiuscorp.uf2m.UF2M
import io.github.probeiuscorp.uf2m.data.dimension.RegisterDimensions
import net.minecraft.core.HolderLookup
import net.minecraft.core.RegistryAccess
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider
import java.util.concurrent.CompletableFuture

class UF2MRegistrySets(output: PackOutput, registries: CompletableFuture<HolderLookup.Provider>) :
    DatapackBuiltinEntriesProvider(output, registries, BUILDER, setOf(UF2M.MODID)) {
    companion object {
        val BUILDER: RegistrySetBuilder = RegistrySetBuilder()
            .add(Registries.BIOME, UF2MBiomes::bootstrap)
            .add(Registries.NOISE_SETTINGS, RegisterDimensions::bootstrapNoiseSettings)
            .add(Registries.DIMENSION_TYPE, RegisterDimensions::bootstrapDimensionTypes)
            .add(Registries.LEVEL_STEM, RegisterDimensions::bootstrapStems)

        fun createLookup(): HolderLookup.Provider {
            return BUILDER.buildPatch(
                RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY),
                VanillaRegistries.createLookup()
            )
        }
    }
}
