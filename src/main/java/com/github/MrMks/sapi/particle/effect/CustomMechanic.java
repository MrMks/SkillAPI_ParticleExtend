package com.github.MrMks.sapi.particle.effect;

import com.github.MrMks.sapi.particle.Mach_Base;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.custom.EditorOption;

import java.util.List;

public abstract class CustomMechanic extends Mach_Base {
    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public List<EditorOption> getOptions() {
        return ImmutableList.of();
    }
}
