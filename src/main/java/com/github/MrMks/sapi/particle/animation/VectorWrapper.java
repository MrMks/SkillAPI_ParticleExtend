package com.github.MrMks.sapi.particle.animation;

import org.bukkit.util.Vector;

public class VectorWrapper extends Vector{

    private final Vector x,y,z;
    public VectorWrapper(double yaw, double pitch){
        yaw = Math.max(Math.min(90,yaw), -90);
        pitch = Math.max(Math.min(90,pitch), -90);
        x = initVector(yaw, pitch);
        y = initVector(yaw, pitch + 90);
        z = initVector(yaw + 90, 0);
    }

    private Vector initVector(double yaw, double pitch){
        yaw = rad(yaw);
        pitch = rad(pitch);
        double t = Math.cos(pitch);
        return new Vector(t * Math.cos(yaw), Math.sin(pitch), t * Math.sin(yaw)).normalize();
    }

    private double rad(double angle) {
        return angle * Math.PI / 180;
    }

    public Vector wrapVector(Vector vector){
        double nx = vector.getX() * x.getX() + vector.getY() * y.getX() + vector.getZ() * z.getX();
        double ny = vector.getX() * x.getY() + vector.getY() * y.getY() + vector.getZ() * z.getY();
        double nz = vector.getX() * x.getZ() + vector.getY() * y.getZ() + vector.getZ() * z.getZ();
        return new Vector(nx,ny,nz);
    }

}
