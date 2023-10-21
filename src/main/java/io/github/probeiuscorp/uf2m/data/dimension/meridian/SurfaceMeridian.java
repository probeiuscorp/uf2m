package io.github.probeiuscorp.uf2m.data.dimension.meridian;

import com.google.common.collect.ImmutableList;
import io.github.probeiuscorp.uf2m.data.UF2MBiomes;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SurfaceMeridian {
    public static SurfaceRules.RuleSource meridianSurface() {
        SurfaceRules.RuleSource wastes = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(UF2MBiomes.MERIDIAN_WASTES),
                        BiomeMeridianWastes.meridianWastesSurface()
                )
        );

        SurfaceRules.RuleSource midnight_zone = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(UF2MBiomes.MERIDIAN_MIDNIGHT_ZONE),
                        BiomeMeridianMidnightZone.meridianMidnightZoneSurface()
                )
        );

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder
                .add(wastes)
                .add(midnight_zone)
                .add(SurfaceRuleData.overworld());

        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }
}
