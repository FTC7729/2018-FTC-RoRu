package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.detectors.GenericDetector;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.disnodeteam.dogecv.scoring.MaxAreaScorer;
import com.disnodeteam.dogecv.scoring.PerfectAreaScorer;
import com.disnodeteam.dogecv.scoring.RatioScorer;

public class HawkeyeDetector extends GenericDetector {
    public HawkeyeDetector() {
        super();
        detectorName = "Hawkeye Collision Detector";
        colorFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.BLUE);
        perfectAreaScorer = new PerfectAreaScorer(40000,4);
        maxDifference = 6;
    }
}
