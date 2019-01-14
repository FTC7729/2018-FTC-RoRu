package com.disnodeteam.dogecv.filters;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 * Created by Victo on 1/1/2018.
 */

public abstract class DogeCVColorFilter {
    public abstract void process(Mat input, Mat mask);
    public void updateSettings(Scalar sc,Scalar sc2){
        // do nothing
    }
}
