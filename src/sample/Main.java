package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import sample.BoxExplosionAnimation.BoxExplosionAnimation;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Parent Node
        Group parentGroupNode = new Group();

        // Node
        Box boxToExplode = new Box(0.3, 0.3, 0.3);
        BoxExplosionAnimation boxExplosionAnimation = new BoxExplosionAnimation(boxToExplode, 20, true, parentGroupNode);

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
                        boxExplosionAnimation.start();
                        break;
                }
            }
        });

        // Attaching Nodes
        parentGroupNode.getChildren().add(cameraView.camera);
        parentGroupNode.getChildren().add(boxToExplode);

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
