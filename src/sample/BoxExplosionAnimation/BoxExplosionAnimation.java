package sample.BoxExplosionAnimation;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.Box;

import java.util.Random;

public class BoxExplosionAnimation extends AnimationTimer
{
    private long lastMomentFragmentDisappear = System.currentTimeMillis();
    private final Random random = new Random();
    private final FragmentBoxCreator fragmentBoxCreator;
    private double fragmentSpaceRunned = 0;
    private double fragmentDisappearAmount = 0;

    public double fragmentDisplacementLength = 0.05;
    public double spaceRunnedToStartDisappear = 0.3;
    public double gapFragmentDisappear = 2;
    public double fragmentDisappearedPercentToStop = 0.8;
    private final double[] explosionCoordinateSource = new double[3];

    public BoxExplosionAnimation(Box target, int scale, boolean optimized, Group parentGroup)
    {
        fragmentBoxCreator = new FragmentBoxCreator(target, scale, optimized, parentGroup);
    }

    public void setExplosionCoordinateSource (double[] coordinatesXYZ)
    {
        if (coordinatesXYZ == null || coordinatesXYZ.length != 3) return;

        if (pointIsInsideBox(fragmentBoxCreator.target, coordinatesXYZ))
        {
            System.out.println("Coordenadas definidas.");
            explosionCoordinateSource[0] = coordinatesXYZ[0];
            explosionCoordinateSource[1] = coordinatesXYZ[1];
            explosionCoordinateSource[2] = coordinatesXYZ[2];
        }
    }

    boolean pointIsInsideBox (Box box, double[] point)
    {
        return (Math.abs(point[0] - box.getTranslateX()) <= box.getWidth()/2 &&
                Math.abs(point[1] - box.getTranslateY()) <= box.getHeight()/2 &&
                Math.abs(point[2] - box.getTranslateZ()) <= box.getDepth()/2);
    }

    @Override
    public void start() {
        fragmentBoxCreator.target.setVisible(false);
        explosionCoordinateSource[0] = fragmentBoxCreator.target.getTranslateX();
        explosionCoordinateSource[1] = fragmentBoxCreator.target.getTranslateY();
        explosionCoordinateSource[2] = fragmentBoxCreator.target.getTranslateZ();
        fragmentBoxCreator.createAllFragments();
        fragmentBoxCreator.parentGroupToAttachFragments.getChildren().remove(fragmentBoxCreator.target);
        super.start();
    }

    void removeAllFragments ()
    {
        for (FragmentBox fragmentBox : fragmentBoxCreator.fragmentBoxes){
            fragmentBox.setVisible(false);
            fragmentBoxCreator.parentGroupToAttachFragments.getChildren().remove(fragmentBox);
        }
    }

    @Override
    public void handle(long now) {

        for (int i = 0; i < fragmentBoxCreator.fragmentBoxes.size(); i++)
        {
            FragmentBox fragment = fragmentBoxCreator.fragmentBoxes.get(i);
            fragment.setTranslateX((1 + (fragmentDisplacementLength / fragment.currentDistanceToTarget)) * (fragment.getTranslateX() - explosionCoordinateSource[0]) + explosionCoordinateSource[0]);
            fragment.setTranslateY((1 + (fragmentDisplacementLength / fragment.currentDistanceToTarget)) * (fragment.getTranslateY() - explosionCoordinateSource[1]) + explosionCoordinateSource[1]);
            fragment.setTranslateZ((1 + (fragmentDisplacementLength / fragment.currentDistanceToTarget)) * (fragment.getTranslateZ() - explosionCoordinateSource[2]) + explosionCoordinateSource[2]);
            fragment.currentDistanceToTarget += fragmentDisplacementLength;

            if (fragmentSpaceRunned > spaceRunnedToStartDisappear)
            {
                if (System.currentTimeMillis() - lastMomentFragmentDisappear > gapFragmentDisappear)
                {
                    int randomIndex = random.nextInt(fragmentBoxCreator.fragmentBoxes.size());
                    fragmentBoxCreator.fragmentBoxes.get(randomIndex).setVisible(false);
                    fragmentBoxCreator.fragmentBoxes.remove(randomIndex);
                    fragmentDisappearAmount++;
                    lastMomentFragmentDisappear = System.currentTimeMillis();
                }
            }
        }

        if (fragmentDisappearAmount / fragmentBoxCreator.totalFragmentAmount >= fragmentDisappearedPercentToStop)
        {
            removeAllFragments();
            fragmentBoxCreator.fragmentBoxes.clear();
            this.stop();
        }

        fragmentSpaceRunned += fragmentDisplacementLength;
    }
}
