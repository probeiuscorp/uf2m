package io.github.probeiuscorp.uf2m.data;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import io.github.probeiuscorp.uf2m.UF2M;
import io.github.probeiuscorp.uf2m.data.dimension.RegisterDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class UF2MRegistrySets extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, UF2MBiomes::bootstrap)
			.add(Registries.NOISE_SETTINGS, RegisterDimensions::bootstrapNoiseSettings)
			.add(Registries.DIMENSION_TYPE, RegisterDimensions::bootstrapDimensionTypes)
			.add(Registries.LEVEL_STEM, RegisterDimensions::bootstrapStems);
	
	public UF2MRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(UF2M.MODID));
    }

    public static HolderLookup.Provider createLookup() {
        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
    }
}
