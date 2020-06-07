package com.github.MrMks.sapi.particle.animation;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.custom.EditorOption;

import java.util.List;

public class ParticleAnimationBukkit extends ParticleAnimationAbstract {
    @Override
    protected List<String> getParticleOptions() {
        return ImmutableList.of("Ender Signal", "Mobspawner Flames", "Potion Break", "Smoke");
    }

    @Override
    protected List<EditorOption> getExtraParticleOptions() {
        return ImmutableList.of(
                EditorOption.text("data", "Data", "The data value to use for the particle. The effect changes between particles such as the orientation for smoke particles or the color for potion break", "0")
        );
    }

    @Override
    public String getKey() {
        return "Particle Anim B";
    }

    @Override
    public String getDescription() {
        return "";
    }

}
