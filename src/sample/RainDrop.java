package sample;

import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;

public class RainDrop
{
    // States
    final int STATE_READY_TO_FALL = 0;
    final int STATE_FALLING = 1;
    final int STATE_KICKING = 2;
    int STATE_CURRENT = STATE_READY_TO_FALL;

    // Floor animation parameters
    double[] xzFloorDirection = new double[2];
    double directionModule;
    double eachKickTimer = 0.0;
    double initialYVelocity = 2.0;
    double currentYVelocity = initialYVelocity;
    double initialXZVelocity = 0.05;
    double currentXZVelocity = initialXZVelocity;
    int floorKicksCount = 0;
    int floorMaxKicks = 2;
    boolean kickEnabled = true;
    boolean rotationAnimationEnabled = true;

    RainDropAnimation animation;
    Shape3D shape3D;
    Rotate xRotate;
    Rotate yRotate;
    Rotate zRotate;

    public RainDrop(Shape3D shape3D,
                    Rotate xRotate, Rotate yRotate, Rotate zRotate)
    {
        this.shape3D = shape3D;
        this.shape3D.setVisible(false);
        this.xRotate = xRotate;
        this.yRotate = yRotate;
        this.zRotate = zRotate;
        this.animation = new RainDropAnimation(xRotate, yRotate, zRotate);
    }

    void setXzFloorDirection(double[] direction)
    {
        xzFloorDirection = direction;
        directionModule = Math.sqrt(Math.pow(direction[0], 2) + Math.pow(direction[1], 2));
    }

    boolean exceededFloorKicks ()
    {
        return floorKicksCount > floorMaxKicks;
    }

    void resetParameters ()
    {
        currentYVelocity = initialYVelocity;
        currentXZVelocity = initialXZVelocity;
        floorKicksCount = 0;
    }

    void setEnableKicks (boolean enable)
    {
        kickEnabled = enable;
    }
}
