package com.github.MrMks.sapi.particle;

import com.github.MrMks.sapi.particle.animation.ParticleAnimation;
import com.github.MrMks.sapi.particle.animation.ParticleAnimationBukkit;
import com.github.MrMks.sapi.particle.animation.ParticleAnimationMaterial;
import com.github.MrMks.sapi.particle.animation.ParticleAnimationReflection;
import com.github.MrMks.sapi.particle.effect.ParticleEffectMechanic;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.SkillPlugin;
import com.sucy.skill.dynamic.ComponentRegistry;
import com.sucy.skill.dynamic.EffectComponent;
import com.sucy.skill.dynamic.custom.CustomEffectComponent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Main extends JavaPlugin implements SkillPlugin {
    @Override
    public void registerSkills(SkillAPI skillAPI) {
    }

    @Override
    public void registerClasses(SkillAPI skillAPI) {
    }

    @Override
    public List<CustomEffectComponent> getComponents() {
        registerBugFix();
        return ImmutableList.of(
                new ParticleAnimation(),
                new ParticleAnimationBukkit(),
                new ParticleAnimationMaterial(),
                new ParticleAnimationReflection()
        );
    }

    private void registerBugFix(){
        try {
            Method method = ComponentRegistry.class.getDeclaredMethod("register", EffectComponent.class);
            method.setAccessible(true);
            method.invoke(null,new ParticleEffectMechanic());
            method.setAccessible(false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
