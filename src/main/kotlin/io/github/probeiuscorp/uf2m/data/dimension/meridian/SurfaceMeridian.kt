package io.github.probeiuscorp.uf2m.data.dimension.meridian

import com.google.common.collect.ImmutableList
import io.github.probeiuscorp.uf2m.data.UF2MBiomes
import net.minecraft.data.worldgen.SurfaceRuleData
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource

object SurfaceMeridian {
    fun meridianSurface(): RuleSource {
        val wastes = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(UF2MBiomes.MERIDIAN_WASTES),
                BiomeMeridianWastes.meridianWastesSurface()
            )
        )

        val midnight_zone = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(UF2MBiomes.MERIDIAN_MIDNIGHT_ZONE),
                BiomeMeridianMidnightZone.meridianMidnightZoneSurface()
            )
        )

        val builder = ImmutableList.builder<RuleSource>()
        builder
            .add(wastes)
            .add(midnight_zone)
            .add(SurfaceRuleData.overworld())

        return SurfaceRules.sequence(*builder.build().toTypedArray()) // .toArray<RuleSource> { _Dummy_.__Array__() })
    }
}
