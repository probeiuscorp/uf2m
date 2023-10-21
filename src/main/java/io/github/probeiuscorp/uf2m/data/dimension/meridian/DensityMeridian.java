package io.github.probeiuscorp.uf2m.data.dimension.meridian;

import io.github.probeiuscorp.uf2m.data.DBP;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class DensityMeridian {
    public static final ResourceKey<NoiseGeneratorSettings> MERIDIAN_NOISE = DBP.createNoiseKey("meridian_noise");

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(DensityMeridian.MERIDIAN_NOISE, meridianNoiseSettings(context));
    }

    private static DensityFunction meridianFinalDensity(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
//        DensityFunction density = getFunction(densityFunctions, UF2MDensityFunctions.BASE_3D_NOISE_AETHER);
//        DensityFunction density = getFunction(densityFunctions, );
//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));
//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.05));
//        density = DensityFunctions.blendDensity(density);
//        density = DensityFunctions.interpolated(density);
//        density = density.squeeze();
//        DensityFunction density = densityFunctions.getOrThrow(NoiseRouterData.CONTINENTS_LARGE).value();
        DensityFunction surface = DensityFunctions.yClampedGradient(-32, 256, 1, -1);
        DensityFunction random = DensityFunctions.noise(noise.getOrThrow(Noises.GRAVEL), 0.5, 0.5);
//        random = DensityFunctions
        DensityFunction density = DensityFunctions.add(surface, random);
//        density = slide(density, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
        return density;
    }

    public static NoiseRouter meridianNoiseRouter(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        DensityFunction finalDensity = meridianFinalDensity(context);
//        return (NoiseRouterData.CONTINENTS_LARGE)
//            DensityFunction shiftX = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
//            DensityFunction shiftZ = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));
//            DensityFunction something = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(Noises.VEGETATION));
//        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(AetherNoises.VEGETATION));
            return new NoiseRouter(
                    DensityFunctions.zero(), // barrier noise
                    DensityFunctions.zero(), // fluid level floodedness noise
                    DensityFunctions.zero(), // fluid level spread noise
                    DensityFunctions.zero(), // temperature
                    DensityFunctions.zero(), // vegetation
                    DensityFunctions.zero(), // continents
                    DensityFunctions.zero(), // continentalness noise
                    DensityFunctions.zero(), // erosion noise
                    DensityFunctions.zero(), // depth
                    DensityFunctions.zero(), // ridges
                    DensityFunctions.zero(), // initial density without jaggedness, not sure if this is needed. Some vanilla dimensions use this while others don't.
                    finalDensity, // finaldensity
                    DensityFunctions.zero(), // veinToggle
                    DensityFunctions.zero(), // veinRidged
                    DensityFunctions.zero()); // veinGap
    }

    public static NoiseGeneratorSettings meridianNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        return new NoiseGeneratorSettings(
                NoiseSettings.create(
                        -32,
                        256,
                        2,
                        1
                ),
                Blocks.STONE.defaultBlockState(), // defaultBlock
                Blocks.WATER.defaultBlockState(), // defaultFluid
                meridianNoiseRouter(context), // noiseRouter
                SurfaceMeridian.meridianSurface(), // surfaceRule
                List.of(), // spawnTarget
                0, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // useLegacyRandomSource
        );
    }
}
