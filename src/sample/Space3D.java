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

    void draw3DAxis (Color xAxisColor, Color yAxisColor, Color zAxisColor, double length, double density)
    {
        Box xAxis = new Box(length, density, density);
        Box yAxis = new Box(density, length, density);
        Box zAxis = new Box(density, density, length);
        xAxis.setMaterial(new PhongMaterial(xAxisColor));
        yAxis.setMaterial(new PhongMaterial(yAxisColor));
        zAxis.setMaterial(new PhongMaterial(zAxisColor));
        group.getChildren().add(xAxis);
        group.getChildren().add(yAxis);
        group.getChildren().add(zAxis);
    }

    void drawSimpleRectangularFloor (double floorHeight, Color color, double width, double height, double depth)
    {
        Box plane = new Box(width, height, depth);
        plane.setTranslateY(floorHeight);
        plane.setMaterial(new PhongMaterial(color));
        group.getChildren().add(plane);
    }
}
