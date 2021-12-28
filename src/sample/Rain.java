package sample;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

import java.util.LinkedList;
import java.util.List;

public class Rain extends MeshView
{
    LinkedList<RainDrop> dropList;
    RainFallAnimation fallAnimation;
    RainFloorAnimation floorAnimation;

    Rain (LinkedList<RainDrop> dropList)
    {
        super();
        this.dropList = dropList;
        fallAnimation = new RainFallAnimation(dropList);
        floorAnimation = new RainFloorAnimation(dropList);
    }

    void setFallAngles (double fallXAngle, double fallZAngle)
    {
        fallAnimation.fallXAngle = fallXAngle;
        fallAnimation.fallZAngle = fallZAngle;
    }

    void setFallSpeed (double speed)
    {
        fallAnimation.fallSpeed = speed;
    }

    void setDropRotationSpeed (double speed)
    {
        for (RainDrop drop : dropList) drop.animation.rotationSpeed = speed;
    }

    void setRainArea (double[] xRange, double[] zRange)
    {
        fallAnimation.xRainRange = xRange;
        fallAnimation.zRainRange = zRange;
    }

    void addRainDrop (RainDrop shape)
    {
        dropList.add(shape);
    }

    void addAllRainDrops (List<RainDrop> list)
    {
        dropList.addAll(list);
    }

    void setAllDropsColor (Color color)
    {
        for (RainDrop drop : dropList) drop.shape3D.setMaterial(new PhongMaterial(color));
    }

    void play ()
    {
        fallAnimation.start();
        floorAnimation.start();
    }

    void stop ()
    {
        fallAnimation.stop();
        floorAnimation.stop();
    }
}
