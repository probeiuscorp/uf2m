package io.github.probeiuscorp.uf2m.data.dimension.cauldron

import com.google.common.collect.ImmutableList
import net.minecraft.data.worldgen.SurfaceRuleData
import net.minecraft.world.level.levelgen.SurfaceRules
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource

object SurfaceCauldron {
    fun cauldronSurface(): RuleSource {
        val builder = ImmutableList.builder<RuleSource>()
        builder
            .add(SurfaceRuleData.overworld())

        return SurfaceRules.sequence(*builder.build().toTypedArray()) // .toArray<RuleSource> { _Dummy_.__Array__() })
    }
}
