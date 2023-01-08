package org.firstinspires.ftc.teamcode.vision;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ConeDetectionPipeline extends OpenCvPipeline {

    public Scalar bottomColorGreen = new Scalar(69.4,72.3,0);
    public Scalar bottomColorYellow = new Scalar(25.5,29.8,130.3);
    public Scalar bottomColorBlue = new Scalar(107.7,51,113.5);
    public Scalar topColorGreen = new Scalar(94.9,255,255);
    public Scalar topColorYellow = new Scalar(35.4,255,255);
    public Scalar topColorBlue = new Scalar(137.4,255,255);
    private Scalar curBotton = bottomColorGreen;
    private Scalar curTop = topColorGreen;
    public enum Color {
        Yellow,
        Cyan,
        Magenta
    }
    private Color currentColor = Color.Cyan;

    String answer = "";

    private Mat hsl = new Mat();
    private Mat binaryMat = new Mat();
    private Mat maskedInputMat = new Mat();

    private Scalar gAvg = new Scalar(0,0,0);
    private Scalar bAvg = new Scalar(0,0,0);
    private Scalar yAvg = new Scalar(0,0,0);

    Telemetry telemetry;

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, hsl, Imgproc.COLOR_RGB2HSV);

        Core.inRange(hsl, bottomColorGreen, topColorGreen, binaryMat);

        maskedInputMat.release();

        Core.bitwise_and(input, input, maskedInputMat, binaryMat);

        gAvg = Core.mean(maskedInputMat);

        Imgproc.cvtColor(input, hsl, Imgproc.COLOR_RGB2HSV);

        binaryMat.release();

        Core.inRange(hsl, bottomColorYellow, topColorYellow, binaryMat);

        maskedInputMat.release();

        Core.bitwise_and(input, input, maskedInputMat, binaryMat);

        yAvg = Core.mean(maskedInputMat);

        Imgproc.cvtColor(input, hsl, Imgproc.COLOR_RGB2HSV);

        binaryMat.release();

        Core.inRange(hsl, bottomColorBlue, topColorBlue, binaryMat);

        maskedInputMat.release();

        Core.bitwise_and(input, input, maskedInputMat, binaryMat);

        bAvg = Core.mean(maskedInputMat);

        if (bAvg.val[0] > yAvg.val[0] && bAvg.val[0] > gAvg.val[0]) {
            currentColor = Color.Cyan;
        } else if (yAvg.val[0] > gAvg.val[0] && yAvg.val[0] > bAvg.val[0]) {
            currentColor = Color.Yellow;
        } else {
            currentColor = Color.Magenta;
        }

        telemetry.addData("bAvg", bAvg.toString());
        telemetry.addData("gAvg", gAvg.toString());
        telemetry.addData("yAvg", yAvg.toString());
        telemetry.addData("guessed color:", answer);
        telemetry.update();

        return input;
    }

    public Color getColorGuess() {
        return currentColor;
    }

}
