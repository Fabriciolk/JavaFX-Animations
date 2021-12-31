package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import sample.BoxExplosionAnimation.BoxExplosionAnimation;
import sample.ImpulseAnimation.ImpulseAnimation;
import sample.ImpulseAnimation.ShakeAnimation;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Parent Node
        Group parentGroupNode = new Group();

        // Draw 3D coordinates
        Space3D space3D = new Space3D(parentGroupNode);
        space3D.drawSimpleFloor(0.3, Color.BLUE);

        // Node
        Sphere shapeToTest = new Sphere(0.3);
        ImpulseAnimation impulseAnimation = new ImpulseAnimation(shapeToTest, new double[] {-0.3, -0.01, 1}, 0.01);

        // Scene
        Scene scene = new Scene(parentGroupNode, 800, 600, true);
        scene.setFill(Color.BLACK);

        // Perspective Camera
        CameraView cameraView = new CameraView(scene);

        // Overwrite event
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode())
                {
                    case W:
                        impulseAnimation.start();
                        break;
                }
            }
        });

        // Attaching Nodes
        parentGroupNode.getChildren().add(cameraView.camera);
        parentGroupNode.getChildren().add(shapeToTest);

        // Stage configuration
        primaryStage.setScene(scene);
        FrameRate frameRate = new FrameRate(primaryStage);
        frameRate.start();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
