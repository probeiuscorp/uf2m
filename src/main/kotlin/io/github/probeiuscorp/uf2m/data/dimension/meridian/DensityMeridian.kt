package io.github.probeiuscorp.uf2m.data.dimension.meridian

import io.github.probeiuscorp.uf2m.data.DBP.createNoiseKey
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.*

object DensityMeridian {
  val MERIDIAN_NOISE = createNoiseKey("meridian_noise")

  fun bootstrap(context: BootstapContext<NoiseGeneratorSettings>) {
    context.register(MERIDIAN_NOISE, meridianNoiseSettings(context))
  }

  private fun meridianFinalDensity(context: BootstapContext<NoiseGeneratorSettings>): DensityFunction {
    val noise = context.lookup(Registries.NOISE)
    //        DensityFunction density = getFunction(densityFunctions, UF2MDensityFunctions.BASE_3D_NOISE_AETHER);
//        DensityFunction density = getFunction(densityFunctions, );
//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));
//        density = DensityFunctions.add(density, DensityFunctions.constant(-0.05));
//        density = DensityFunctions.blendDensity(density);
//        density = DensityFunctions.interpolated(density);
//        density = density.squeeze();
//        DensityFunction density = densityFunctions.getOrThrow(NoiseRouterData.CONTINENTS_LARGE).value();
    val surface = DensityFunctions.yClampedGradient(-32, 256, 1.0, -1.0)
    val random = DensityFunctions.noise(noise.getOrThrow(Noises.GRAVEL), 0.5, 0.5)
    //        random = DensityFunctions
    val density = DensityFunctions.add(surface, random)
    //        density = slide(density, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
    return density
  }

  fun meridianNoiseRouter(context: BootstapContext<NoiseGeneratorSettings>): NoiseRouter {
    val noise = context.lookup(Registries.NOISE)
    val finalDensity = meridianFinalDensity(context)
    //        return (NoiseRouterData.CONTINENTS_LARGE)
//            DensityFunction shiftX = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
//            DensityFunction shiftZ = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));
//            DensityFunction something = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(Noises.VEGETATION));
//        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(AetherNoises.VEGETATION));
    return NoiseRouter(
      DensityFunctions.zero(),  // barrier noise
      DensityFunctions.zero(),  // fluid level floodedness noise
      DensityFunctions.zero(),  // fluid level spread noise
      DensityFunctions.zero(),  // temperature
      DensityFunctions.zero(),  // vegetation
      DensityFunctions.zero(),  // continents
      DensityFunctions.zero(),  // continentalness noise
      DensityFunctions.zero(),  // erosion noise
      DensityFunctions.zero(),  // depth
      DensityFunctions.zero(),  // ridges
      DensityFunctions.zero(),  // initial density without jaggedness, not sure if this is needed. Some vanilla dimensions use this while others don't.
      finalDensity,  // finaldensity
      DensityFunctions.zero(),  // veinToggle
      DensityFunctions.zero(),  // veinRidged
      DensityFunctions.zero()
    ) // veinGap
  }

  fun meridianNoiseSettings(context: BootstapContext<NoiseGeneratorSettings>): NoiseGeneratorSettings {
    return NoiseGeneratorSettings(
      NoiseSettings.create(
        -32,
        256,
        2,
        1
      ),
      Blocks.STONE.defaultBlockState(),  // defaultBlock
      Blocks.WATER.defaultBlockState(),  // defaultFluid
      meridianNoiseRouter(context),  // noiseRouter
      SurfaceMeridian.meridianSurface(),  // surfaceRule
      listOf(),  // spawnTarget
      0,  // seaLevel
      false,  // disableMobGeneration
      false,  // aquifersEnabled
      false,  // oreVeinsEnabled
      false // useLegacyRandomSource
    )
  }
}
