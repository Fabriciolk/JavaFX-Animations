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

    public void drawXYZAxis (Color xAxisColor, Color yAxisColor, Color zAxisColor, double length, double density)
    {
        drawXAxis(xAxisColor, length, density);
        drawYAxis(yAxisColor, length, density);
        drawZAxis(zAxisColor, length, density);
    }

    public void drawXZAxis (Color xAxisColor, Color zAxisColor, double length, double density)
    {
        drawXAxis(xAxisColor, length, density);
        drawZAxis(zAxisColor, length, density);
    }

    public void drawXYAxis (Color xAxisColor, Color yAxisColor, double length, double density)
    {
        drawXAxis(xAxisColor, length, density);
        drawZAxis(yAxisColor, length, density);
    }

    public void drawYZAxis (Color yAxisColor, Color zAxisColor, double length, double density)
    {
        drawXAxis(yAxisColor, length, density);
        drawZAxis(zAxisColor, length, density);
    }

    public void drawXAxis (Color axisColor, double length, double density)
    {
        drawBox(axisColor, length, density, density);
    }

    public void drawYAxis (Color axisColor, double length, double density)
    {
        drawBox(axisColor, density, length, density);
    }

    public void drawZAxis (Color axisColor, double length, double density)
    {
        drawBox(axisColor, density, density, length);
    }

    private Box drawBox (Color axisColor, double width, double height, double depth)
    {
        Box box = new Box(width, height, depth);
        box.setMaterial(new PhongMaterial(axisColor));
        group.getChildren().add(box);
        return box;
    }

    public void drawSimpleRectangularFloor (double floorHeight, Color color, double width, double height, double depth)
    {
        drawBox(color, width, height, depth).setTranslateY(floorHeight);
    }
}
