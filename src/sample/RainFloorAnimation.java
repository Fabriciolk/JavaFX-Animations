package sample;

import javafx.animation.AnimationTimer;

import java.util.List;

public class RainFloorAnimation extends AnimationTimer
{
    List<RainDrop> dropList;
    double timeIncrement = 0.011;
    double yVelocityDivider = 1.5;
    double xzVelocityDivider = 1.5;
    double yFloor = 0;

    RainFloorAnimation(List<RainDrop> dropList)
    {
        this.dropList = dropList;
    }

    double nextXValue (RainDrop rainDrop)
    {
        return (rainDrop.currentXZVelocity * rainDrop.eachKickTimer * rainDrop.xzFloorDirection[0]) / rainDrop.directionModule;
    }

    double nextYValue (RainDrop rainDrop)
    {
        return -(yFloor + rainDrop.currentYVelocity * rainDrop.eachKickTimer - 5 * Math.pow(rainDrop.eachKickTimer, 2));
    }

    double nextZValue (RainDrop rainDrop)
    {
        return (rainDrop.currentXZVelocity * rainDrop.eachKickTimer * rainDrop.xzFloorDirection[1]) / rainDrop.directionModule;
    }

    void moveDrop (RainDrop rainDrop)
    {
        rainDrop.shape3D.setTranslateX(rainDrop.shape3D.getTranslateX() + nextXValue(rainDrop));
        rainDrop.shape3D.setTranslateY(nextYValue(rainDrop));
        rainDrop.shape3D.setTranslateZ(rainDrop.shape3D.getTranslateZ() + nextZValue(rainDrop));
        rainDrop.eachKickTimer += timeIncrement;

        if (rainDrop.eachKickTimer > 0.2 && rainDrop.shape3D.getTranslateY() >= yFloor)
        {
            rainDrop.currentYVelocity /= yVelocityDivider;
            rainDrop.currentXZVelocity /= xzVelocityDivider;
            rainDrop.eachKickTimer = 0;
            rainDrop.floorKicksCount++;
        }
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
}
