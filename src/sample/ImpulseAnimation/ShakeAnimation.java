package sample.ImpulseAnimation;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Shape3D;

import java.util.Random;

public class ShakeAnimation extends AnimationTimer
{
    final Random random = new Random();
    boolean incrementsChanged = false;
    final Shape3D shape;
    double[] initialPos;

    double maxLengthMove;
    double incrementScale = 0.0;
    long incrementGapMove = 0;

    long timeGapIncrementMaxLengthMove;
    long timeGapIncrementGapMove;
    long timeGapMove;

    long lastTimeMoved;
    long lastTimeMaxLengthMoveIncremented;
    long lastTimeGapMoveIncremented;

    public ShakeAnimation (Shape3D shape, double maxLengthMove, long timeGapMove)
    {
        this.initialPos = new double[] {shape.getTranslateX(), shape.getTranslateY(), shape.getTranslateZ()};
        this.timeGapMove = timeGapMove;
        this.shape = shape;
        this.maxLengthMove = maxLengthMove;
    }

    public void setIncrementMaxLengthMove (double increment, long gapBetweenIncrement)
    {
        incrementsChanged = true;
        incrementScale = increment;
        timeGapIncrementMaxLengthMove = gapBetweenIncrement;
    }

    public void setIncrementGapMove (long increment, long gapBetweenIncrement)
    {
        incrementsChanged = true;
        incrementGapMove = increment;
        timeGapIncrementGapMove = gapBetweenIncrement;
    }

    @Override
    public void start() {
        lastTimeMoved = System.currentTimeMillis();
        lastTimeMaxLengthMoveIncremented = System.currentTimeMillis();
        lastTimeGapMoveIncremented = System.currentTimeMillis();
        super.start();
    }

    private void verifyIncrements ()
    {
        if (System.currentTimeMillis() - lastTimeMaxLengthMoveIncremented >= timeGapIncrementMaxLengthMove)
        {
            maxLengthMove += incrementScale;
            lastTimeMaxLengthMoveIncremented = System.currentTimeMillis();
        }

        if (System.currentTimeMillis() - lastTimeGapMoveIncremented >= timeGapIncrementGapMove)
        {
            timeGapMove += incrementGapMove;
            lastTimeGapMoveIncremented = System.currentTimeMillis();
        }
    }

    @Override
    public void handle(long now) {

        if (System.currentTimeMillis() - lastTimeMoved >= timeGapMove)
        {
            shape.setTranslateX(initialPos[0] + (random.nextDouble() * 2 - 1) * maxLengthMove);
            shape.setTranslateY(initialPos[1] + (random.nextDouble() * 2 - 1) * maxLengthMove);
            shape.setTranslateZ(initialPos[2] + (random.nextDouble() * 2 - 1) * maxLengthMove);
            lastTimeMoved = System.currentTimeMillis();
        }

        if (incrementsChanged) verifyIncrements();
    }
}
