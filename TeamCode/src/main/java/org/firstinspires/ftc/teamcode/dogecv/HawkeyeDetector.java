package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.detectors.GenericDetector;
import com.disnodeteam.dogecv.filters.HSVColorFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.scoring.MaxAreaScorer;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;
import com.disnodeteam.dogecv.scoring.RatioScorer;

import org.opencv.core.Scalar;

public class HawkeyeDetector extends GenericDetector {
    public HawkeyeDetector() {
        super();
        detectorName = "Hawkeye Collision Detector";
        colorFilter = new HSVColorFilter(new Scalar(85,225,225), new Scalar(45,25,25));
        perfectAreaScorer = new PerfectAreaScorer(40000,4);
        maxDifference = 6;
    }
}
