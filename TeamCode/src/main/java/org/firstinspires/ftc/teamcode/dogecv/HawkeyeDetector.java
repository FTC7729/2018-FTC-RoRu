package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.GenericDetector;
import com.disnodeteam.dogecv.filters.HSVColorFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.scoring.MaxAreaScorer;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;
import com.disnodeteam.dogecv.scoring.RatioScorer;

import org.opencv.core.Scalar;

/**
 * A customized {@link com.disnodeteam.dogecv.detectors.DogeCVDetector} that extends from that module's {@link GenericDetector}.
 * It is built to detect emblems that we will put on our alliance partner to help our bot not collide with them during the autonomous portion of the match.
 * <br>
 * colorFilter: This is a color filter that takes two sets of three values. The first set defines a color in the form (Hue, Saturation, Value), and the second
 * provides a threshold for those three values so that the detector isn't too strict.
 * <br>
 * perfectAreaScorer: This is a "scorer" that takes an ideal area for the target to be (in pixels) and an importance ("weight") score, so that the program can
 * reduce the number of potential targets to recognize.
 * <br>
 * maxDifference: This is a value representing how far away the ratio of the rectangle formed by the program can be from the ideal ratio (1:1 in this case) before
 * the target is discarded as imperfect.
 * @author Isaac Blum
 */
public class HawkeyeDetector extends GenericDetector {
    /**
     * Simply sets the fields that are declared in the superclass. It allows us to detect the Hawkeye target with DogeCV loaded with our customized parameters.
     */
    public HawkeyeDetector() {
        super();
        detectorName = "Hawkeye Collision Detector";
        colorFilter = new HSVColorFilter(new Scalar(52.4166,211.905,255), new Scalar(45,25,25));
        //perfectAreaScorer = new PerfectAreaScorer(40000,4);
        maxDifference = 6;
        //areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA;
    }
}
