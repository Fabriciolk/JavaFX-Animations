package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Space3D
{
    Group group;

    Space3D (Group group)
    {
        this.group = group;
    }

    void draw3DAxis ()
    {
        Box xAxis = new Box(5, 0.01, 0.01);
        Box yAxis = new Box(0.01, 5, 0.01);
        Box zAxis = new Box(0.01, 0.01, 5);
        xAxis.setMaterial(new PhongMaterial(Color.RED));
        yAxis.setMaterial(new PhongMaterial(Color.GREEN));
        zAxis.setMaterial(new PhongMaterial(Color.BLUE));
        group.getChildren().add(xAxis);
        group.getChildren().add(yAxis);
        group.getChildren().add(zAxis);
    }

    void drawSimpleFloor (double yValue, Color color)
    {
        int totalLine = 20;

        for (int i = 0; i < totalLine; i++)
        {
            Box verticalLine = new Box(0.01, 0.01, 5);
            Box horizontalLine = new Box(5, 0.01, 0.01);
            verticalLine.setMaterial(new PhongMaterial(color));
            horizontalLine.setMaterial(new PhongMaterial(color));
            verticalLine.setTranslateX(-2 + i * (4.0 / totalLine));
            verticalLine.setTranslateY(yValue);
            horizontalLine.setTranslateZ(-2 + i * (4.0 / totalLine));
            horizontalLine.setTranslateY(yValue);
            group.getChildren().add(verticalLine);
            group.getChildren().add(horizontalLine);
        }
    }
}
