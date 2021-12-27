package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        LinkedList<RainDrop> boxesList = new LinkedList<>();
        Group root = new Group();

        Space3D space3D = new Space3D(root);
        space3D.drawSimpleFloor(0, Color.BLUE);

        for (int i = 0; i < 70; i++)
        {
            Box box = new Box(0.03, 0.03, 0.03);
            Rotate xR = new Rotate(0, Rotate.X_AXIS);
            Rotate yR = new Rotate(0, Rotate.Y_AXIS);
            Rotate zR = new Rotate(0, Rotate.Z_AXIS);
            box.getTransforms().addAll(xR, yR, zR);
            root.getChildren().add(box);
            RainDrop rainDrop = new RainDrop(box, xR, yR, zR);
            boxesList.add(rainDrop);
        }

        Rain rain = new Rain(boxesList);
        rain.play();

        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        CameraView cameraView = new CameraView(scene);
        scene.setCamera(cameraView.camera);
        root.getChildren().add(cameraView.camera);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
