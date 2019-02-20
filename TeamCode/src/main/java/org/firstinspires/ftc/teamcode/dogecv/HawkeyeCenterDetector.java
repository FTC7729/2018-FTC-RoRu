package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.filters.HSVColorFilter;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;

import org.opencv.core.Scalar;

public class HawkeyeCenterDetector extends GoldAlignDetector {
    public HawkeyeCenterDetector() {
        super();
        detectorName = "Hawkeye Aligment Detector";
        yellowFilter = new HSVColorFilter(new Scalar(60,210,185), new Scalar(35,75,85));
        perfectAreaScorer = new PerfectAreaScorer(20000,2);
        maxDifference = 10;
        alignSize = 150;
    }
}
