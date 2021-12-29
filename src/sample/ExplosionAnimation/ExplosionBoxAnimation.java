package sample.ExplosionAnimation;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.Box;

import java.util.Random;

public class ExplosionBoxAnimation extends AnimationTimer
{
    long lastMomentFragmentDisappear = System.currentTimeMillis();
    public double fragmentDisplacementLength = 0.08;
    double fragmentSpaceRunned = 0;
    double spaceRunnedToStartDisappear = 0.3;
    double gapFragmentDisappear = 2;
    double fragmentDisappearAmount = 0;
    double fragmentDisappearedPercentToStop = 0.8;
    Random random = new Random();
    FragmentBoxCreator fragmentBoxCreator;
    Box target;

    public ExplosionBoxAnimation(Box target, int scale, boolean optimized, Group parentGroup)
    {
        this.target = target;
        fragmentBoxCreator = new FragmentBoxCreator(target, scale, optimized, parentGroup);
        fragmentBoxCreator.createAllFragments();
    }

    @Override
    public void start() {
        for (FragmentBox fragmentBox : fragmentBoxCreator.fragmentBoxes) fragmentBox.setVisible(true);
        target.setVisible(false);
        fragmentBoxCreator.parentGroupToAttachFragments.getChildren().remove(target);
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
            FragmentBox box = fragmentBoxCreator.fragmentBoxes.get(i);
            box.setTranslateX((1 + (fragmentDisplacementLength / box.currentDistanceToTarget)) * (box.getTranslateX() - fragmentBoxCreator.target.getTranslateX()) + fragmentBoxCreator.target.getTranslateX());
            box.setTranslateY((1 + (fragmentDisplacementLength / box.currentDistanceToTarget)) * (box.getTranslateY() - fragmentBoxCreator.target.getTranslateY()) + fragmentBoxCreator.target.getTranslateY());
            box.setTranslateZ((1 + (fragmentDisplacementLength / box.currentDistanceToTarget)) * (box.getTranslateZ() - fragmentBoxCreator.target.getTranslateZ()) + fragmentBoxCreator.target.getTranslateZ());
            box.currentDistanceToTarget += fragmentDisplacementLength;

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

        if (fragmentDisappearAmount / fragmentBoxCreator.maxFragmentAmount >= fragmentDisappearedPercentToStop)
        {
            removeAllFragments();
            fragmentBoxCreator.fragmentBoxes.clear();
            this.stop();
        }

        fragmentSpaceRunned += fragmentDisplacementLength;
    }
}