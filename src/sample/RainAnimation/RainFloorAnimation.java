package sample.RainAnimation;

import javafx.animation.AnimationTimer;

import java.util.List;

class RainFloorAnimation extends AnimationTimer
{
    List<RainDrop> dropList;
    double timeIncrement = 0.011;
    double yVelocityDivider = 1.5;
    double xzVelocityDivider = 1.5;
    double timeDelay = 0.2;

    RainFloorAnimation(List<RainDrop> dropList)
    {
        this.dropList = dropList;
    }

    @Override
    public void handle(long now)
    {
        for (RainDrop rainDrop : dropList)
        {
            if (rainDrop.STATE_CURRENT == rainDrop.STATE_KICKING)
            {
                if (rainDrop.kickEnabled) moveDrop(rainDrop);
                else rainDrop.floorKicksCount = rainDrop.floorMaxKicks;
            }
        }
    }

    /*****************************
     **                         **
     **     Private Methods     **
     **                         **
     ****************************/

    private double nextXValue (RainDrop rainDrop)
    {
        return (rainDrop.currentXZVelocity * rainDrop.eachKickTimer * rainDrop.xzFloorDirection[0]) / rainDrop.directionModule;
    }

    private double nextYValue (RainDrop rainDrop)
    {
        return -(rainDrop.floorHeight + rainDrop.currentYVelocity * rainDrop.eachKickTimer - 5 * Math.pow(rainDrop.eachKickTimer, 2));
    }

    private double nextZValue (RainDrop rainDrop)
    {
        return (rainDrop.currentXZVelocity * rainDrop.eachKickTimer * rainDrop.xzFloorDirection[1]) / rainDrop.directionModule;
    }

    private void moveDrop (RainDrop rainDrop)
    {
        rainDrop.shape3D.setTranslateX(rainDrop.shape3D.getTranslateX() + nextXValue(rainDrop));
        rainDrop.shape3D.setTranslateY(nextYValue(rainDrop));
        rainDrop.shape3D.setTranslateZ(rainDrop.shape3D.getTranslateZ() + nextZValue(rainDrop));
        rainDrop.eachKickTimer += timeIncrement;

        if (rainDrop.eachKickTimer > timeDelay && rainDrop.shape3D.getTranslateY() >= rainDrop.floorHeight)
        {
            rainDrop.currentYVelocity /= yVelocityDivider;
            rainDrop.currentXZVelocity /= xzVelocityDivider;
            rainDrop.eachKickTimer = 0;
            rainDrop.floorKicksCount++;
        }
    }
}
