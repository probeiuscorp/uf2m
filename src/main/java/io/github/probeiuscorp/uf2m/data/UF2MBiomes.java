package io.github.probeiuscorp.uf2m.data;

import io.github.probeiuscorp.uf2m.data.dimension.meridian.BiomeMeridianMidnightZone;
import io.github.probeiuscorp.uf2m.data.dimension.meridian.BiomeMeridianWastes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class UF2MBiomes {
    public static final ResourceKey<Biome> MERIDIAN_WASTES = DBP.createBiomeKey("meridian_wastes");
    public static final ResourceKey<Biome> MERIDIAN_MIDNIGHT_ZONE = DBP.createBiomeKey("meridian_midnight_zone");

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(MERIDIAN_WASTES, BiomeMeridianWastes.createMeridianWastesBiome(context));
        context.register(MERIDIAN_MIDNIGHT_ZONE, BiomeMeridianMidnightZone.createMeridianMidnightZoneBiome(context));
    }
}
