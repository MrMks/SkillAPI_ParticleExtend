package com.github.MrMks.sapi.particle.animation;

import com.github.MrMks.sapi.particle.Mach_Base;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.Settings;
import com.sucy.skill.api.util.ParticleHelper;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ParticleAnimationAbstract extends Mach_Base {
    private static final String FORWARD = "forward";
    private static final String UPWARD = "upward";
    private static final String RIGHT = "right";
    private static final String STEPS = "steps";
    private static final String FREQ = "frequency";
    private static final String ANGLE = "angle";
    private static final String START = "start";
    private static final String DURATION = "duration";
    private static final String H_TRANS = "h-translation";
    private static final String V_TRANS = "v-translation";
    private static final String H_CYCLES = "h-cycles";
    private static final String V_CYCLES = "v-cycles";

    private static final List<String> arrangement = ImmutableList.of("Circle", "Hemisphere", "Sphere");
    private static final List<String> direction = ImmutableList.of("XY", "XZ", "YZ");

    @Override
    public List<EditorOption> getOptions() {
        List<EditorOption> options = new ArrayList<>(Arrays.asList(
                EditorOption.text("steps","Steps","The number of times to play particles and apply translations each application.","1"),
                EditorOption.text("frequency", "Frequency", "How often to apply the animation in seconds. 0.05 is the fastest (1 tick). Lower than that will act the same.", "0.05"),
                EditorOption.text("yaw", "Yaw", "应用平面仰角", "0"),
                EditorOption.text("pitch", "Pitch", "应用平面偏转角", "0"),
                EditorOption.text("angle", "Angle", "How far the animation should rotate over the duration in degrees", "0"),
                EditorOption.text("start", "Start Angle", "The starting orientation of the animation. Horizontal translations and the forward/right offsets will be based off of this.", "0"),
                EditorOption.number("duration","Duration","How long the animation should last for in seconds",5,0),
                EditorOption.number("h-translation", "H-Translation", "How far the animation moves horizontally relative to the center over a cycle. Positive values make it expand from the center while negative values make it contract.",0,0),
                EditorOption.number("v-translation", "v-translation", "How far the animation moves vertically over a cycle. Positive values make it rise while negative values make it sink.",0,0),
                EditorOption.text("h-cycles", "H-Cycles", "How many times to move the animation position throughout the animation. Every other cycle moves it back to where it started. For example, two cycles would move it out and then back in.", "1"),
                EditorOption.text("v-cycles","V-Cycles","How many times to move the animation position throughout the animation. Every other cycle moves it back to where it started. For example, two cycles would move it up and then back down.","1"),
                EditorOption.text("forward", "Forward Offset" ,"How far forward in front of the target in blocks to play the particles. A negative value will go behind.", "0"),
                EditorOption.text("upward", "Upward Offset", "How far above the target in blocks to play the particles. A negative value will go below.", "0"),
                EditorOption.text("right","Right Offset","How far to the right of the target to play the particles. A negative value will go to the left.", "0"),
                EditorOption.dropdown("arrangement", "Arrangement", "The arrangement to use for the particles. Circle is a 2D circle, Hemisphere is half a 3D sphere, and Sphere is a 3D sphere", arrangement),
                EditorOption.number("radius", "Radius", "The radius of the arrangement in blocks", 4,0),
                EditorOption.number("particles", "Particles", "The amount of particles to play", 20, 0),
                EditorOption.dropdown("direction", "Circle Direction", "The orientation of the circle. XY and YZ are vertical circles while XZ is a horizontal circle.", direction),
                EditorOption.dropdown("particle", "Particle", "The type of particle to display. Particle effects that show the DX, DY, and DZ options are not compatible with Cauldron", getParticleOptions())
                ));
        options.addAll(getExtraParticleOptions());
        return options;
    }

    @Override
    public boolean execute(LivingEntity livingEntity, int i, List<LivingEntity> list) {
        if (list.size() > 0) {
            Settings var4 = new Settings(this.settings);
            var4.set("particles", this.parseValues(livingEntity, "particles", i, 1.0D), 0.0D);
            var4.set("radius", this.parseValues(livingEntity, "radius", i, 0.0D), 0.0D);
            var4.set("level", i);
            new ParticleTask(livingEntity, list, i, var4);
            return true;
        } else {
            return false;
        }
    }

    protected abstract List<String> getParticleOptions();
    protected abstract List<EditorOption> getExtraParticleOptions();
    private class ParticleTask extends BukkitRunnable {

        private List<LivingEntity> targets;
        private double[]           rots;
        private Vector             offset;
        private Vector             dir;
        private VectorWrapper      wrapper;

        private double forward;
        private double right;
        private double upward;

        private int    steps;
        private int    freq;
        private int    angle;
        private int    startAngle;
        private int    duration;
        private int    life;
        private int    hc;
        private int    vc;
        private int    hl;
        private int    vl;
        private double ht;
        private double vt;
        private double cos;
        private double sin;

        private Settings settings;

        ParticleTask(LivingEntity caster, List<LivingEntity> targets, int level, Settings settings)
        {
            this.targets = targets;
            this.settings = settings;

            double yaw = settings.getDouble("yaw", 0);
            double pitch = settings.getDouble("pitch", 0);
            this.wrapper = new VectorWrapper(yaw, pitch);

            this.forward = settings.getDouble(FORWARD, 0);
            this.upward = settings.getDouble(UPWARD, 0);
            this.right = settings.getDouble(RIGHT, 0);

            this.steps = settings.getInt(STEPS, 1);
            this.freq = (int) (settings.getDouble(FREQ, 1.0) * 20);
            this.angle = settings.getInt(ANGLE, 0);
            this.startAngle = settings.getInt(START, 0);
            this.duration = steps * (int) (20 * parseValues(caster, DURATION, level, 3.0));
            this.life = 0;
            this.ht = parseValues(caster, H_TRANS, level, 0);
            this.vt = parseValues(caster, V_TRANS, level, 0);
            this.hc = settings.getInt(H_CYCLES, 1);
            this.vc = settings.getInt(V_CYCLES, 1);
            this.hl = duration / hc;
            this.vl = duration / vc;

            this.cos = Math.cos(angle * Math.PI / (180 * duration));
            this.sin = Math.sin(angle * Math.PI / (180 * duration));

            rots = new double[targets.size() * 2];
            for (int i = 0; i < targets.size(); i++)
            {
                Vector dir = targets.get(i).getLocation().getDirection().setY(0).normalize();
                rots[i * 2] = dir.getX();
                rots[i * 2 + 1] = dir.getZ();
            }
            this.dir = new Vector(1, 0, 0);
            this.offset = new Vector(forward, upward, right);

            double sc = Math.cos(startAngle * Math.PI / 180);
            double ss = Math.sin(startAngle * Math.PI / 180);
            rotate(offset, sc, ss);
            rotate(dir, sc, ss);

            SkillAPI.schedule(this, 0, freq);
        }

        @Override
        public void run()
        {
            for (int i = 0; i < steps; i++)
            {
                // Play the effect
                int j = 0;
                for (LivingEntity target : targets)
                {
                    Location loc = target.getLocation();

                    Vector temp = wrapper.wrapVector(offset);
                    rotate(temp, rots[j], rots[j + 1]);
                    loc.add(temp);
                    ParticleHelper.play(loc, settings);
                    loc.subtract(temp);
                    //rotate(temp, rots[j++], -rots[j++]);
                }

                // Update the lifespan of the animation
                this.life++;

                // Apply transformations
                rotate(offset, cos, sin);
                rotate(dir, cos, sin);

                double dx = radAt(this.life) - radAt(this.life - 1);
                offset.setX(offset.getX() + dx * dir.getX());
                offset.setZ(offset.getZ() + dx * dir.getZ());
                offset.setY(upward + heightAt(this.life));
            }

            if (this.life >= this.duration)
            {
                cancel();
            }
        }

        private double heightAt(int step)
        {
            return vt * (vl - Math.abs(vl - step % (2 * vl))) / vl;
        }

        private double radAt(int step)
        {
            return ht * (hl - Math.abs(hl - step % (2 * hl))) / hl;
        }

        private void rotate(Vector vec, double cos, double sin)
        {
            double x = vec.getX() * cos - vec.getZ() * sin;
            vec.setZ(vec.getX() * sin + vec.getZ() * cos);
            vec.setX(x);
        }
    }
}
