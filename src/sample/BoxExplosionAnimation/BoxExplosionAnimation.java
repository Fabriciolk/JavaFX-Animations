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

    private double fragmentDisplacementLength = 0.05;
    private double spaceRunnedToStartDisappear = 0.3;
    private double gapFragmentDisappear = 2;
    private double fragmentDisappearedPercentToStop = 0.8;

    public BoxExplosionAnimation(Box target, int scale, boolean optimized, Group parentGroup)
    {
        fragmentBoxCreator = new FragmentBoxCreator(target, scale, optimized, parentGroup);
    }

    // This method avoids coordinates problems caused by another animation that modify those,
    // creating another instance of Box with same properties. At this moment, it supports the
    // functionality that define the explosion source coordinates.

    // To include later: the new instance should have same rotation angles.
    private synchronized void updateBoxInstance()
    {
        Box newBox = new Box(fragmentBoxCreator.target.getWidth(), fragmentBoxCreator.target.getHeight(), fragmentBoxCreator.target.getDepth());
        newBox.setTranslateX(fragmentBoxCreator.target.getTranslateX());
        newBox.setTranslateY(fragmentBoxCreator.target.getTranslateY());
        newBox.setTranslateZ(fragmentBoxCreator.target.getTranslateZ());
        newBox.setMaterial(fragmentBoxCreator.target.getMaterial());
        newBox.setVisible(false);
        fragmentBoxCreator.target = newBox;
    }

    private void removeAllFragments ()
    {
        for (FragmentBox fragmentBox : fragmentBoxCreator.fragmentBoxes){
            fragmentBox.setVisible(false);
            fragmentBoxCreator.parentGroupToAttachFragments.getChildren().remove(fragmentBox);
        }
    }

    @Override
    public void start() {
        fragmentBoxCreator.target.setVisible(false);
        updateBoxInstance();
        fragmentBoxCreator.parentGroupToAttachFragments.getChildren().remove(fragmentBoxCreator.target);
        fragmentBoxCreator.createAllFragments();
        super.start();
    }

    @Override
    public void handle(long now) {

        for (int i = 0; i < fragmentBoxCreator.fragmentBoxes.size(); i++)
        {
            FragmentBox fragment = fragmentBoxCreator.fragmentBoxes.get(i);
            fragment.setTranslateX((1 + (fragmentDisplacementLength / fragment.currentDistanceToTarget)) * (fragment.getTranslateX() - fragmentBoxCreator.target.getTranslateX() - fragmentBoxCreator.explosionCoordinateSource[0]) + fragmentBoxCreator.target.getTranslateX() + fragmentBoxCreator.explosionCoordinateSource[0]);
            fragment.setTranslateY((1 + (fragmentDisplacementLength / fragment.currentDistanceToTarget)) * (fragment.getTranslateY() - fragmentBoxCreator.target.getTranslateY() - fragmentBoxCreator.explosionCoordinateSource[1]) + fragmentBoxCreator.target.getTranslateY() + fragmentBoxCreator.explosionCoordinateSource[1]);
            fragment.setTranslateZ((1 + (fragmentDisplacementLength / fragment.currentDistanceToTarget)) * (fragment.getTranslateZ() - fragmentBoxCreator.target.getTranslateZ() - fragmentBoxCreator.explosionCoordinateSource[2]) + fragmentBoxCreator.target.getTranslateZ() + fragmentBoxCreator.explosionCoordinateSource[2]);
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

    /*****************************
     **                         **
     **         Setters         **
     **                         **
     ****************************/

    public void setFragmentDisplacementLength(double fragmentDisplacementLength) {
        if (fragmentDisplacementLength <= 0) return;
        this.fragmentDisplacementLength = fragmentDisplacementLength;
    }

    public void setSpaceRunnedToStartDisappear(double spaceRunnedToStartDisappear) {
        if (spaceRunnedToStartDisappear < 0) return;
        this.spaceRunnedToStartDisappear = spaceRunnedToStartDisappear;
    }

    public void setGapFragmentDisappear(double gapFragmentDisappear) {
        this.gapFragmentDisappear = Math.max(0, gapFragmentDisappear);
    }

    public void setFragmentDisappearedPercentToStop(double fragmentDisappearedPercentToStop) {
        if (fragmentDisappearedPercentToStop < 0 || fragmentDisappearedPercentToStop > 1) return;
        this.fragmentDisappearedPercentToStop = fragmentDisappearedPercentToStop;
    }

    public void setExplosionCoordinateSource (double[] coordinatesXYZ)
    {
        if (coordinatesXYZ == null || coordinatesXYZ.length != 3) return;

        if (pointIsInsideBox(fragmentBoxCreator.target, coordinatesXYZ))
        {
            fragmentBoxCreator.explosionCoordinateSource[0] = coordinatesXYZ[0];
            fragmentBoxCreator.explosionCoordinateSource[1] = coordinatesXYZ[1];
            fragmentBoxCreator.explosionCoordinateSource[2] = coordinatesXYZ[2];
        }
    }

    private boolean pointIsInsideBox (Box box, double[] point)
    {
        return (Math.abs(point[0] - box.getTranslateX()) <= box.getWidth()/2 &&
                Math.abs(point[1] - box.getTranslateY()) <= box.getHeight()/2 &&
                Math.abs(point[2] - box.getTranslateZ()) <= box.getDepth()/2);
    }
}
