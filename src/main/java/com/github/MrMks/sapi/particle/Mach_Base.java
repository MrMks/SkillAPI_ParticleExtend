package com.github.MrMks.sapi.particle;

import com.sucy.skill.dynamic.ComponentType;
import com.sucy.skill.dynamic.custom.CustomEffectComponent;

public abstract class Mach_Base extends CustomEffectComponent {
    @Override
    public ComponentType getType() {
        return ComponentType.MECHANIC;
    }
}
