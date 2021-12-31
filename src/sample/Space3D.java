package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.zip.ZipEntry;

public class Space3D
{
    Group group;

    Space3D (Group group)
    {
        this.group = group;
    }

    void drawXYZAxis (Color xAxisColor, Color yAxisColor, Color zAxisColor, double length, double density)
    {
        drawXAxis(xAxisColor, length, density);
        drawYAxis(yAxisColor, length, density);
        drawZAxis(zAxisColor, length, density);
    }

    void drawXZAxis (Color xAxisColor, Color zAxisColor, double length, double density)
    {
        drawXAxis(xAxisColor, length, density);
        drawZAxis(zAxisColor, length, density);
    }

    void drawXYAxis (Color xAxisColor, Color yAxisColor, double length, double density)
    {
        drawXAxis(xAxisColor, length, density);
        drawZAxis(yAxisColor, length, density);
    }

    void drawYZAxis (Color yAxisColor, Color zAxisColor, double length, double density)
    {
        drawXAxis(yAxisColor, length, density);
        drawZAxis(zAxisColor, length, density);
    }

    void drawXAxis (Color axisColor, double length, double density)
    {
        drawBoxAsAxis(axisColor, length, density, density);
    }

    void drawYAxis (Color axisColor, double length, double density)
    {
        drawBoxAsAxis(axisColor, density, length, density);
    }

    void drawZAxis (Color axisColor, double length, double density)
    {
        drawBoxAsAxis(axisColor, density, density, length);
    }

    void drawBoxAsAxis (Color axisColor, double width, double height, double depth)
    {
        Box axis = new Box(width, height, depth);
        axis.setMaterial(new PhongMaterial(axisColor));
        group.getChildren().add(axis);
    }

    void drawSimpleRectangularFloor (double floorHeight, Color color, double width, double height, double depth)
    {
        Box plane = new Box(width, height, depth);
        plane.setTranslateY(floorHeight);
        plane.setMaterial(new PhongMaterial(color));
        group.getChildren().add(plane);
    }
}
