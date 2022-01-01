package sample.IllusionAnimation;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Shape3D;

import java.util.ArrayList;
import java.util.Random;

public class IllussionAnimation extends AnimationTimer
{
    private final ArrayList<double[]> positions = new ArrayList<>();
    private double[] positionWhenStop;
    private int currentIndex = 0;
    Shape3D realShape;

    IllussionAnimation(Shape3D realShape, double[][] clonePositions)
    {
        if (clonePositions[0].length != 3 || realShape == null) return;
        this.realShape = realShape;
        positionWhenStop = new double[] {realShape.getTranslateX(), realShape.getTranslateY(), realShape.getTranslateZ()};

        for (double[] clonePosition : clonePositions) {
            addPositionXYZ(clonePosition);
        }
    }

    public IllussionAnimation(Shape3D realShape, int cloneAmount, double[] xyzRange)
    {
        if (xyzRange.length != 6 || cloneAmount <= 0 || realShape == null) return;
        if (xyzRange[0] > xyzRange[1] || xyzRange[2] > xyzRange[3] || xyzRange[4] > xyzRange[5]) return;
        this.realShape = realShape;
        positionWhenStop = new double[] {realShape.getTranslateX(), realShape.getTranslateY(), realShape.getTranslateZ()};

        Random random = new Random();
        for (int i = 0; i < cloneAmount; i++)
        {
            addPositionXYZ(new double[] {
                    (xyzRange[1] - xyzRange[0]) * random.nextDouble() + xyzRange[0],
                    (xyzRange[3] - xyzRange[2]) * random.nextDouble() + xyzRange[2],
                    (xyzRange[5] - xyzRange[4]) * random.nextDouble() + xyzRange[4]});
        }
    }

    public void addPositionXYZ (double[] positionXYZ)
    {
        if (positionXYZ.length != 3) return;
        System.out.println("posicao adicionada: [" + positionXYZ[0] + ", " + positionXYZ[1] + ", " + positionXYZ[2] + "]");
        positions.add(positionXYZ);
    }

    public void removePositionXYZ (double[] positionXYZ)
    {
        if (positionXYZ.length != 3) return;
        for (double[] position : positions)
        {
            if (position[0] == positionXYZ[0] &&
                position[1] == positionXYZ[1] &&
                position[2] == positionXYZ[2])
            {
                positions.remove(position);
                break;
            }
        }
    }

    public void setPositionWhenStop (double[] position)
    {
        if (position.length != 3) return;
        positionWhenStop = position;
    }

    @Override
    public void stop() {
        super.stop();
        realShape.setTranslateX(positionWhenStop[0]);
        realShape.setTranslateY(positionWhenStop[1]);
        realShape.setTranslateZ(positionWhenStop[2]);
    }

    @Override
    public void handle(long now) {
        realShape.setTranslateX(positions.get(currentIndex)[0]);
        realShape.setTranslateY(positions.get(currentIndex)[1]);
        realShape.setTranslateZ(positions.get(currentIndex)[2]);
        currentIndex = (currentIndex + 1) % positions.size();
    }
}
