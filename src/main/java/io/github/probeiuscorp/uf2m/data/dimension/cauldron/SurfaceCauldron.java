package io.github.probeiuscorp.uf2m.data.dimension.cauldron;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SurfaceCauldron {
    public static SurfaceRules.RuleSource cauldronSurface() {
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder
                .add(SurfaceRuleData.overworld());

        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }
}
