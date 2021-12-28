package sample.RainAnimation;

import javafx.animation.AnimationTimer;

import java.util.LinkedList;
import java.util.Random;

class RainFallAnimation extends AnimationTimer
{
    Random random = new Random();
    LinkedList<RainDrop> availableDropList;

    // Rain area on sky (differs to floor area depending on fall angle)
    double[] xRainRange = {-2, 2};
    double[] zRainRange = {-2, 2};

    // Rain attributes
    double fallXAngle = 10;
    double fallZAngle = 0;
    double skyHeight = -4;
    double gapTimeBetweenDrops = 20;
    double fallSpeed = 0.025;
    double lastTimeDropFallen;

    RainFallAnimation (LinkedList<RainDrop> dropList)
    {
        availableDropList = dropList;
        lastTimeDropFallen = System.currentTimeMillis();
    }


    void startAllDropAnimations()
    {
        for (RainDrop rainDrop : availableDropList)
        {
            if (rainDrop.rotationAnimationEnabled &&
                rainDrop.xRotate != null &&
                rainDrop.yRotate != null &&
                rainDrop.zRotate != null)
            {
                rainDrop.animation.start();
                rainDrop.rotationAnimationEnabled = true;
            }
        }
    }

    void stopAllDropAnimations()
    {
        for (RainDrop rainDrop : availableDropList)
        {
            if (rainDrop.rotationAnimationEnabled) {
                rainDrop.animation.stop();
                rainDrop.rotationAnimationEnabled = false;
            }
        }
    }

    void setRandomSkyPosition (RainDrop rainDrop)
    {
        Random random = new Random();

        rainDrop.shape3D.setTranslateX(random.nextDouble() * (xRainRange[1] - xRainRange[0]) + xRainRange[0]);
        rainDrop.shape3D.setTranslateY(skyHeight);
        rainDrop.shape3D.setTranslateZ(random.nextDouble() * (zRainRange[1] - zRainRange[0]) + zRainRange[0]);
    }

    @Override
    public synchronized void handle(long now) {

        for (RainDrop rainDrop : availableDropList)
        {

            if (rainDrop.STATE_CURRENT == rainDrop.STATE_READY_TO_FALL)
            {
                if (passedGap(System.currentTimeMillis() - lastTimeDropFallen))
                {
                    rainDrop.STATE_CURRENT = rainDrop.STATE_FALLING;
                    lastTimeDropFallen = System.currentTimeMillis();
                    rainDrop.shape3D.setVisible(true);
                }
            }

            if (rainDrop.STATE_CURRENT == rainDrop.STATE_FALLING)
            {
                moveDrop(rainDrop);

                if (reachedFloor(rainDrop))
                {
                    rainDrop.STATE_CURRENT = rainDrop.STATE_KICKING;
                    setRandomXZDirection(rainDrop);
                }
            }

            if (rainDrop.STATE_CURRENT == rainDrop.STATE_KICKING)
            {
                if (rainDrop.exceededFloorKicks() || !rainDrop.kickEnabled)
                {
                    rainDrop.STATE_CURRENT = rainDrop.STATE_READY_TO_FALL;
                    rainDrop.shape3D.setVisible(false);
                    setRandomSkyPosition(rainDrop);
                    rainDrop.resetParameters();
                }
            }
        }
    }

    /*****************************
     **                         **
     **     Private Methods     **
     **                         **
     ****************************/

    private boolean reachedFloor (RainDrop rainDrop)
    {
        return rainDrop.shape3D.getTranslateY() >= rainDrop.floorHeight;
    }

    private boolean reachFloorNextMove (RainDrop rainDrop)
    {
        return rainDrop.shape3D.getTranslateY() + fallSpeed >= rainDrop.floorHeight;
    }

    private boolean passedGap (double timePassed)
    {
        return timePassed > gapTimeBetweenDrops;
    }

    private void setRandomXZDirection (RainDrop rainDrop)
    {
        double xCoordinate = random.nextDouble() * 2 - 1;
        double zCoordinate = random.nextDouble() * 2 - 1;

        while (xCoordinate == 0 && zCoordinate == 0)
        {
            xCoordinate = random.nextDouble() * 2 - 1;
            zCoordinate = random.nextDouble() * 2 - 1;
        }

        rainDrop.setXzFloorDirection(new double[] {xCoordinate, zCoordinate});
    }

    private void moveDrop (RainDrop rainDrop)
    {
        rainDrop.shape3D.setTranslateX(rainDrop.shape3D.getTranslateX() + Math.sin(fallXAngle * (Math.PI / 180)) * fallSpeed);
        if (reachFloorNextMove(rainDrop)) rainDrop.shape3D.setTranslateY(rainDrop.floorHeight);
        else rainDrop.shape3D.setTranslateY(rainDrop.shape3D.getTranslateY() + fallSpeed);
        rainDrop.shape3D.setTranslateZ(rainDrop.shape3D.getTranslateZ() + Math.sin(fallZAngle * (Math.PI / 180)) * fallSpeed);
    }
}
