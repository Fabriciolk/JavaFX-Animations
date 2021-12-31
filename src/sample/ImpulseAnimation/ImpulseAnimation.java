package sample.ImpulseAnimation;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Shape3D;

public class ImpulseAnimation extends AnimationTimer
{
    private ShakeAnimation shakeAnimation;
    private final double[] directionVector = new double[3];
    Shape3D projectile;
    double scalar = 0.0;
    double speed;

    boolean shakeEnabled = true;
    long timeToImpulse = 3000;
    long initialTime;

    public ImpulseAnimation(Shape3D projectile, double[] directionFromProjectile, double speed)
    {
        if (directionFromProjectile.length != 3) return;
        shakeAnimation = new ShakeAnimation(projectile, 0.03, 10);
        directionVector[0] = directionFromProjectile[0] - projectile.getTranslateX();
        directionVector[1] = directionFromProjectile[1] - projectile.getTranslateY();
        directionVector[2] = directionFromProjectile[2] - projectile.getTranslateZ();
        this.projectile = projectile;
        this.speed = speed;
    }

    public ShakeAnimation getShakeAnimation ()
    {
        return shakeAnimation;
    }

    public void setShakeEnabled (boolean enabled, long timeToImpulse)
    {
        if (timeToImpulse < 0) return;
        shakeEnabled = enabled;
        this.timeToImpulse = timeToImpulse;
    }

    @Override
    public void start() {
        if (shakeEnabled) shakeAnimation.start();
        initialTime = System.currentTimeMillis();
        super.start();
    }

    @Override
    public void stop() {
        shakeAnimation.stop();
        super.stop();
    }

    @Override
    public void handle(long now)
    {
        // Ready to impulse
        if (System.currentTimeMillis() - initialTime >= timeToImpulse)
        {
            shakeAnimation.stop();
            projectile.setTranslateX(scalar * directionVector[0] + projectile.getTranslateX());
            projectile.setTranslateY(scalar * directionVector[1] + projectile.getTranslateY());
            projectile.setTranslateZ(scalar * directionVector[2] + projectile.getTranslateZ());
            scalar += speed;
        }
    }
}
