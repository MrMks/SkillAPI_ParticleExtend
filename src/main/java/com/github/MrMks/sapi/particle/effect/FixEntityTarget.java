package com.github.MrMks.sapi.particle.effect;

import com.sucy.skill.api.particle.target.EffectTarget;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class FixEntityTarget implements EffectTarget {
    private final Entity entity;

    public FixEntityTarget(Entity var1) {
        this.entity = var1;
    }

    public Location getLocation() {
        return this.entity.getLocation().clone();
    }

    public Entity getEntity() {
        return this.entity;
    }

    public boolean isValid() {
        return this.entity.isValid() && !this.entity.isDead();
    }

    public int hashCode() {
        return entity.hashCode();
    }

    public boolean equals(Object var1) {
        if (!(var1 instanceof FixEntityTarget)) {
            return false;
        } else {
            FixEntityTarget var2 = (FixEntityTarget)var1;
            return var2.entity == this.entity;
        }
    }
}
