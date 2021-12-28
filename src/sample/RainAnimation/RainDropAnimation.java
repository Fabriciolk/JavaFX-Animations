package sample.RainAnimation;

import javafx.animation.AnimationTimer;
import javafx.scene.transform.Rotate;

import java.util.Random;

class RainDropAnimation extends AnimationTimer
{
    Random random = new Random();
    double rotationSpeed = 1;

    Rotate xRotate;
    Rotate yRotate;
    Rotate zRotate;

    public RainDropAnimation(Rotate xRotate, Rotate yRotate, Rotate zRotate)
    {
        super();
        this.xRotate = xRotate;
        this.yRotate = yRotate;
        this.zRotate = zRotate;
    }

    void setRotationSpeed (double speed)
    {
        rotationSpeed = speed;
    }

    int generateDirection ()
    {
        int result = random.nextInt(2) - 1;
        while (result == 0) result = random.nextInt(2) - 1;
        return result;
    }

    @Override
    public void handle(long now) {
        xRotate.setAngle(xRotate.getAngle() + rotationSpeed * generateDirection());
        yRotate.setAngle(yRotate.getAngle() + rotationSpeed * generateDirection());
    }
}
