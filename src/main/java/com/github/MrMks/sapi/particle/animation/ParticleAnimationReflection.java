package com.github.MrMks.sapi.particle.animation;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.custom.EditorOption;

import java.util.List;

public class ParticleAnimationReflection extends ParticleAnimationAbstract{
    @Override
    protected List<String> getParticleOptions() {
        return ImmutableList.of(
                "Angry Villager",
                "Bubble",
                "Cloud",
                "Crit",
                "Damage Indicator",
                "Death Suspend",
                "Dragon Breath",
                "Drip Lava",
                "Drip Water",
                "Enchantment Table",
                "End Rod",
                "Explode",
                "Firework Spark",
                "Flame",
                "Footstep",
                "Happy Villager",
                "Heart",
                "Huge Explosion",
                "Instant Spell",
                "Large Explode",
                "Large Smoke",
                "Lava",
                "Magic Crit",
                "Mob Spell",
                "Mob Spell Ambient",
                "Note",
                "Portal",
                "Red Dust",
                "Slime",
                "Snowball Poof",
                "Snow Shovel",
                "Spell",
                "Splash",
                "Suspend",
                "Town Aura",
                "Water Drop",
                "Water Wake",
                "Witch Magic"
                );
    }

    @Override
    protected List<EditorOption> getExtraParticleOptions() {
        return ImmutableList.of(
                EditorOption.text("visible-radius", "Visible Radius", "How far away players can see the particles from in blocks", "25"),
                EditorOption.text("dx","DX","A packet variable that varies between particles. It generally is used for how far from the position a particle can move in the X direction.", "0"),
                EditorOption.text("dy","DY", "A packet variable that varies between particles. It generally is used for how far from the position a particle can move in the Y direction.", "0"),
                EditorOption.text("dz","DZ", "A packet variable that varies between particles. It generally is used for how far from the position a particle can move in the Z direction.", "0"),
                EditorOption.text("speed", "Particle Speed", "A packet variable that varies between particles. It generally controlls the color or velocity of the particle.","1"),
                EditorOption.text("amount","Packet Amount", "A packet variable that varies between particles. Setting this to 0 lets you control the color of some particles.", "1")
        );
    }

    @Override
    public String getKey() {
        return "Particle Anim R";
    }

    @Override
    public String getDescription() {
        return "";
    }

}
