package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import sample.RainAnimation.Rain;
import sample.RainAnimation.RainDrop;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Rain rain = new Rain();
        int rainDropAmount = 70;
        Group parentGroupNode = new Group();

        Space3D space3D = new Space3D(parentGroupNode);
        space3D.drawSimpleFloor(0, Color.BLUE);

        for (int i = 0; i < rainDropAmount; i++)
        {
            Shape3D shape = new Box(0.03, 0.03, 0.03);
//            Rotate xR = new Rotate(0, Rotate.X_AXIS);
//            Rotate yR = new Rotate(0, Rotate.Y_AXIS);
//            Rotate zR = new Rotate(0, Rotate.Z_AXIS);
//            shape.getTransforms().addAll(xR, yR, zR);
            parentGroupNode.getChildren().add(shape);
//            rain.addRainDrop(new RainDrop(shape, xR, yR, zR));
            rain.addRainDrop(new RainDrop(shape));
        }

        rain.start();

        Scene scene = new Scene(parentGroupNode, 800, 600, Color.BLACK);
        CameraView cameraView = new CameraView(scene);
        scene.setCamera(cameraView.camera);
        parentGroupNode.getChildren().add(cameraView.camera);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
