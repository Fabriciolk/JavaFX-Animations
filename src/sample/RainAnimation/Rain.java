package sample.RainAnimation;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

import java.util.LinkedList;

public class Rain extends MeshView
{
    private final LinkedList<RainDrop> dropList = new LinkedList<>();
    private final RainFallAnimation fallAnimation;
    private final RainFloorAnimation floorAnimation;

    public Rain()
    {
        super();
        fallAnimation = new RainFallAnimation(dropList);
        floorAnimation = new RainFloorAnimation(dropList);
    }

    public void addRainDrop (RainDrop shape)
    {
        dropList.add(shape);
        fallAnimation.setRandomSkyPosition(shape);
        shape.shape3D.setVisible(false);
    }

    public void start()
    {
        fallAnimation.start();
        fallAnimation.startAllDropAnimations();
        floorAnimation.start();
    }

    public void stop ()
    {
        fallAnimation.stop();
        fallAnimation.stopAllDropAnimations();
        floorAnimation.stop();
    }

    /*****************************
     **                         **
     **       Set Methods       **
     **                         **
     ****************************/

    public void setFallAngles (double fallXAngle, double fallZAngle)
    {
        fallAnimation.fallXAngle = fallXAngle;
        fallAnimation.fallZAngle = fallZAngle;
    }

    public void setFallSpeed (double speed)
    {
        fallAnimation.fallSpeed = speed;
    }

    public void setSkyHeight (double height)
    {
        fallAnimation.skyHeight = height;
    }

    public void setDropRotationSpeed (double speed)
    {
        for (RainDrop drop : dropList) drop.animation.rotationSpeed = speed;
    }

    public void setGapTimeBetweenDrops (double time)
    {
        fallAnimation.gapTimeBetweenDrops = time;
    }

    public void setRainArea (double[] xRange, double[] zRange)
    {
        fallAnimation.xRainRange = xRange;
        fallAnimation.zRainRange = zRange;
    }

    public void setAllDropsColor (Color color)
    {
        for (RainDrop drop : dropList) drop.shape3D.setMaterial(new PhongMaterial(color));
    }

    public void setKickFloorSpeed (double speed)
    {
        floorAnimation.timeIncrement = speed;
    }

    public void setKickYVelocityDivider (double divider)
    {
        floorAnimation.yVelocityDivider = divider;
    }

    public void setKickXZVelocityDivider (double divider)
    {
        floorAnimation.xzVelocityDivider = divider;
    }

    public void setKickInitialVelocity (RainDrop rainDrop, double yVelocity, double xzVelocity)
    {
        rainDrop.initialYVelocity = yVelocity;
        rainDrop.initialXZVelocity = xzVelocity;
    }

    public void setKickInitialVelocity (double yVelocity, double xzVelocity)
    {
        for (RainDrop rainDrop : dropList)
        {
            setKickInitialVelocity(rainDrop, yVelocity, xzVelocity);
        }
    }

    public void setMaxFloorKicks (RainDrop rainDrop, int max)
    {
        rainDrop.floorMaxKicks = max;
    }

    public void setMaxFloorKicks (int max)
    {
        for (RainDrop rainDrop : dropList)
        {
            setMaxFloorKicks(rainDrop, max);
        }
    }

    public void setKickAnimationEnabled (RainDrop rainDrop, boolean enabled)
    {
        rainDrop.kickEnabled = enabled;
    }

    public void setKickAnimationEnabled (boolean enabled)
    {
        for (RainDrop rainDrop : dropList)
        {
            setKickAnimationEnabled(rainDrop, enabled);
        }
    }

    /*****************************
     **                         **
     **       Get Methods       **
     **                         **
     ****************************/

    public double[] getFallAngles ()
    {
        return new double[] {fallAnimation.fallXAngle, fallAnimation.fallXAngle};
    }

    public double getFallSpeed ()
    {
        return fallAnimation.fallSpeed;
    }

    public double getSkyHeight ()
    {
        return fallAnimation.skyHeight;
    }

    public double getDropRotationSpeed (RainDrop rainDrop)
    {
        return rainDrop.animation.rotationSpeed;
    }

    public double getGapTimeBetweenDrops ()
    {
        return fallAnimation.gapTimeBetweenDrops;
    }

    public double[][] getRainArea ()
    {
        return new double[][] {fallAnimation.xRainRange, fallAnimation.zRainRange};
    }

    public double getKickFloorSpeed ()
    {
        return floorAnimation.timeIncrement;
    }

    public double getKickYVelocityDivider ()
    {
        return floorAnimation.yVelocityDivider;
    }

    public double getKickXZVelocityDivider ()
    {
        return floorAnimation.xzVelocityDivider;
    }

    public int getMaxFloorKicks (RainDrop rainDrop)
    {
        return rainDrop.floorMaxKicks;
    }

    public boolean getKickAnimationEnabled (RainDrop rainDrop)
    {
        return rainDrop.kickEnabled;
    }
}
