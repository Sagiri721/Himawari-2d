package Engine.Gfx;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;

import Engine.Utils.GameMaths;
import Engine.Utils.Renderer;
import Engine.Utils.Geom.Vec2;

public class ParticleEmitter {
    
    public int lifetime = 100, speed = 300, emission = 1, maxParticles = 40;
    public float fluctuation = 20, speedRate = 0, sizeRate = 0;

    public Vec2 origin = Vec2.ZERO, direction = Vec2.ONE;

    private List<Particle> particles = new ArrayList<Particle>();
    private Sprite particle;

    public boolean emitting = false;

    public void startEmitting(){

        emitting = true;
    }

    public void stopEmitting(){

        emitting = false;
    }

    public ParticleEmitter(Sprite particle, Vec2 origin, Vec2 direction){

        this.particle = particle;
        this.origin = origin;
        this.direction = direction;
    }

    public void render(Graphics2D g) {

        Particle[] particleToIterate = particles.toArray(new Particle[particles.size()]);
        for (Particle pt : particleToIterate) {
            
            Debugging.drawDebugSprite(particle, pt.position, Vec2.fromValue(pt.size), g);
            // Update particle
            
            pt.position = pt.position.sumWith(pt.direction.times(pt.speed).times(Renderer.deltaTime));
            
            if(pt.speed > 0) pt.speed -= speedRate; else pt.speed = 0;
            if(pt.size > 0) pt.size -= sizeRate; else pt.size = 0;

            pt.life--;
	        if(pt.life <= 0) particles.remove(pt);
        }

        emission();
    }

    private Vec2 calculateRandomDirection(){

        double angle = Math.acos(Vec2.RIGHT.normalize().dotProduct(direction.normalize()) / (Vec2.RIGHT.thisMagnitude() * direction.thisMagnitude()));
        double newAngle = Math.toDegrees(angle) + GameMaths.randomInteger(-(int)fluctuation, (int)fluctuation);
        double generalDirection = Math.atan(direction.y / direction.x) * 2;

        return new Vec2((float) Math.cos(Math.toRadians(newAngle) + generalDirection),
                (float) Math.sin(Math.toRadians(newAngle) + generalDirection));
    }

    private void emission(){

        if(particles.size() >= maxParticles || !emitting) return;

        Particle p = new Particle(lifetime, origin, fluctuation == 0 ? direction : calculateRandomDirection(), speed, particle.width);
        particles.add(p);
    }

    public int particleCount() {return particles.size(); }

    public class Particle{

        protected int life, speed = 1, size = 1;
        protected Vec2 position, direction;
        
        protected Particle(int life, Vec2 origin, Vec2 direction, int speed, int size) {

            this.life = life;
            this.position = origin;
            this.direction = direction;
            this.speed = speed;
            this.size = size;
        }
    }
}
