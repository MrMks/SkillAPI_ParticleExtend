package com.github.MrMks.sapi.particle.effect;

import com.sucy.skill.api.particle.EffectPlayer;
import com.sucy.skill.dynamic.mechanic.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class ParticleEffectMechanic extends MechanicComponent
{
    private static final String DURATION = "duration";
    private static final String KEY      = "effect-key";

    @Override
    public String getKey() {
        return "particle effect";
    }

    @Override
    public boolean execute(LivingEntity caster, int level, List<LivingEntity> targets)
    {
        String key = settings.getString(KEY, skill.getName());
        int duration = (int) (20 * parseValues(caster, DURATION, level, 5));

        EffectPlayer player = new EffectPlayer(settings);
        for (LivingEntity target : targets){
            player.start(new FixEntityTarget(target), key, duration, level, true);
        }

        return targets.size() > 0;
    }
}
