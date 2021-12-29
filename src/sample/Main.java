package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import sample.ExplosionAnimation.ExplosionBoxAnimation;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Group parentGroupNode = new Group();

        Box boxToExplode = new Box(0.3, 0.3, 0.3);
        boxToExplode.setVisible(true);
        ExplosionBoxAnimation explosionBoxAnimation = new ExplosionBoxAnimation(boxToExplode, 15, true, parentGroupNode);

        Scene scene = new Scene(parentGroupNode, 800, 600, true);
        scene.setFill(Color.BLACK);

        final long[] frameTimes = new long[100];
        final int[] frameTimeIndex = {0};
        final boolean[] arrayFilled = {false};

        AnimationTimer frameRateMeter = new AnimationTimer() {

            @Override
            public void handle(long now) {
                long oldFrameTime = frameTimes[frameTimeIndex[0]] ;
                frameTimes[frameTimeIndex[0]] = now ;
                frameTimeIndex[0] = (frameTimeIndex[0] + 1) % frameTimes.length ;
                if (frameTimeIndex[0] == 0) {
                    arrayFilled[0] = true ;
                }
                if (arrayFilled[0]) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    primaryStage.setTitle(String.format("Current frame rate: %.3f", frameRate));
                }
            }
        };
        frameRateMeter.start();

        CameraView cameraView = new CameraView(scene);
        scene.setCamera(cameraView.camera);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode())
                {
                    case W:
                        explosionBoxAnimation.start();
                        break;
                    case A:
                        break;
                }
            }
        });
        cameraView.cameraTranslate.setZ(-20);

        parentGroupNode.getChildren().add(cameraView.camera);
        parentGroupNode.getChildren().add(boxToExplode);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
