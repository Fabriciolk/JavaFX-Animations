package sample;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class FrameRate extends AnimationTimer
{
    final long[] frameTimes = new long[100];
    final int[] frameTimeIndex = {0};
    final boolean[] arrayFilled = {false};
    Stage stage;

    FrameRate(Stage stage)
    {
        this.stage = stage;
    }

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
            stage.setTitle(String.format("Current frame rate: %.3f", frameRate));
        }
    }
}
