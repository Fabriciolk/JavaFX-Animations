package sample.BoxExplosionAnimation;

import javafx.scene.Group;
import javafx.scene.shape.Box;

import java.util.ArrayList;

public class FragmentBoxCreator
{
    final ArrayList<FragmentBox> fragmentBoxes;
    final Group parentGroupToAttachFragments;
    final int totalFragmentAmount;
    final boolean optimized;
    final int scale;
    final Box target;

    public FragmentBoxCreator(Box target, int scale, boolean optimized, Group parentGroup)
    {
        this.parentGroupToAttachFragments = parentGroup;
        this.optimized = optimized;
        this.scale = Math.max(2, scale);
        this.target = target;
        this.fragmentBoxes = new ArrayList<>();

        if (optimized) totalFragmentAmount = 6*scale*scale - 12*scale + 8;
        else totalFragmentAmount = scale * scale * scale;

        // Maximum elements for optimized approach: scale³
        // Maximum elements for optimized approach: 6scale² - 12scale + 8
    }

    public void createAllFragments ()
    {
        if (optimized)
        {
            // cima e baixo
            for (int i = 0; i < scale; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    for (int k = 0; k < scale; k++)
                    {
                        createFragment(
                                target.getWidth() * ((2 * i + 1) / (2.0 * scale)),
                                (target.getWidth() / (2.0 * scale)) * (1 + 2.0 * j * (scale - 1)),
                                target.getWidth() * ((2 * k + 1) / (2.0 * scale)));
                    }
                }
            }

            // cada camada entre cima e baixo
            for (int j = 1; j < scale - 1; j++)
            {
                createFragmentAround(target.getWidth() * ((2 * j + 1) / (2.0 * scale)));
            }
        }
        else
        {
            for (int i = 0; i < scale; i++)
            {
                for (int j = 0; j < scale; j++)
                {
                    for (int k = 0; k < scale; k++)
                    {
                        createFragment(
                                target.getWidth() * ((2 * i + 1) / (2.0 * scale)),
                                target.getWidth() * ((2 * j + 1) / (2.0 * scale)),
                                target.getWidth() * ((2 * k + 1) / (2.0 * scale)));
                    }
                }
            }
        }
    }

    private void createFragmentAround (double yHeight)
    {
        // frente e tras
        for (int i = 0; i < scale; i++)
        {
            // frente
            createFragment(target.getWidth() * ((2 * i + 1) / (2.0 * scale)), yHeight, target.getWidth() / (2 * scale));
            // tras
            createFragment(target.getWidth() * ((2 * i + 1) / (2.0 * scale)), yHeight, (target.getWidth() / (2 * scale)) * (2 * scale - 1));
        }

        // esquerda e direita
        for (int k = 1; k < scale - 1; k++)
        {
            // esquerda
            createFragment(target.getWidth() / (2 * scale), yHeight, target.getWidth() * ((2 * k + 1) / (2.0 * scale)));
            // direita
            createFragment((target.getWidth() / (2 * scale)) * (2 * scale - 1), yHeight, target.getWidth() * ((2 * k + 1) / (2.0 * scale)));
        }
    }

    private void createFragment (double xPos, double yPos, double zPos)
    {
        double fragmentX = xPos + target.getTranslateX() - target.getWidth()/2;
        double fragmentY = yPos + target.getTranslateY() - target.getWidth()/2;
        double fragmentZ = zPos + target.getTranslateZ() - target.getWidth()/2;
        double distanceToCenterTarget = Math.sqrt(Math.pow(fragmentX - target.getTranslateX(), 2) + Math.pow(fragmentY - target.getTranslateY(), 2) + Math.pow(fragmentZ - target.getTranslateZ(), 2));
        FragmentBox fragmentBox = new FragmentBox(target.getWidth() / scale, target.getWidth() / scale, target.getWidth() / scale, distanceToCenterTarget);
        fragmentBox.setTranslateX(fragmentX);
        fragmentBox.setTranslateY(fragmentY);
        fragmentBox.setTranslateZ(fragmentZ);
        fragmentBox.setMaterial(target.getMaterial());
        fragmentBox.setVisible(false);
        fragmentBoxes.add(fragmentBox);
        parentGroupToAttachFragments.getChildren().add(fragmentBox);
    }
}
