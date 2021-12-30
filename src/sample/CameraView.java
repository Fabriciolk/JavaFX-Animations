package sample;

import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class CameraView
{
    double maxZoomOut = 100;
    double maxZoomIn = -100;
    Scene attachedScene;
    PerspectiveCamera camera;
    double[] lastMouseScenePosition = {0, 0};

    Rotate xCameraRotate = new Rotate(-20, Rotate.X_AXIS);
    Rotate yCameraRotate = new Rotate(-10, Rotate.Y_AXIS);
    Translate cameraTranslate = new Translate(0, -0.4, -10);

    CameraView (Scene attachedScene)
    {
        this.attachedScene = attachedScene;
        camera = new PerspectiveCamera(true);
        attachedScene.setCamera(camera);
        camera.getTransforms().addAll(xCameraRotate, yCameraRotate, cameraTranslate);
        configEvents();
    }

    void configEvents ()
    {
        attachedScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("Mouse: (" + event.getSceneX() + ", " + event.getSceneY() + ")");
            }
        });
        attachedScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseScenePosition[0] = event.getSceneX();
                lastMouseScenePosition[1] = event.getSceneY();
            }
        });
        attachedScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown())
                {
                    yCameraRotate.setAngle((yCameraRotate.getAngle() + (event.getSceneX() - lastMouseScenePosition[0]) * Math.abs(cameraTranslate.getZ())/40) % 360);
                    xCameraRotate.setAngle((xCameraRotate.getAngle() - (event.getSceneY() - lastMouseScenePosition[1]) * Math.abs(cameraTranslate.getZ())/40) % 360);
                }
                else {
                    double newZValue = cameraTranslate.getZ() + (event.getSceneY() - lastMouseScenePosition[1]) * 0.10;
                    if (maxZoomIn < newZValue && newZValue < maxZoomOut)
                    {
                        cameraTranslate.setZ(newZValue);
                    }
                }

                lastMouseScenePosition[0] = event.getSceneX();
                lastMouseScenePosition[1] = event.getSceneY();
            }
        });
        attachedScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                switch (event.getCode())
                {
                    case LEFT:
                        if (event.isShiftDown()) camera.setTranslateX(camera.getTranslateX() + 0.1);
                        break;
                    case RIGHT:
                        if (event.isShiftDown()) camera.setTranslateX(camera.getTranslateX() - 0.1);
                        break;
                    case UP:
                        if (event.isShiftDown()) camera.setTranslateY(camera.getTranslateY() + 0.1);
                        break;
                    case DOWN:
                        if (event.isShiftDown()) camera.setTranslateY(camera.getTranslateY() - 0.1);
                        break;

                }
            }
        });
    }
}
