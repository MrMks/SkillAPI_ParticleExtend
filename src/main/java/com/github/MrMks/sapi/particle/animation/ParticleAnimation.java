package com.github.MrMks.sapi.particle.animation;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.custom.EditorOption;

import java.util.Collections;
import java.util.List;

public class ParticleAnimation extends ParticleAnimationAbstract {
    @Override
    protected List<String> getParticleOptions() {
        return ImmutableList.of("Barrier", "Death", "Hurt", "Sheep Eat", "Sweep Attack", "Wolf Hearts", "Wolf Shake", "Wolf Smoke");
    }

    @Override
    protected List<EditorOption> getExtraParticleOptions() {
        return Collections.emptyList();
    }

    @Override
    public String getKey() {
        return "Particle Anim A";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
