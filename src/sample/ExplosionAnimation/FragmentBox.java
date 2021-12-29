package sample.ExplosionAnimation;

import javafx.scene.shape.Box;

public class FragmentBox extends Box
{
    double currentDistanceToTarget;

    FragmentBox(double width, double height, double depth, double initialDistanceToTarget)
    {
        super(width, height, depth);
        this.currentDistanceToTarget = initialDistanceToTarget;
    }
}
