package com.detection;

import com.dtw.WarpPath;
import com.timeseries.TimeSeries;

import java.util.ArrayList;

/**
 * Created by qiaojialin on 2017/5/17.
 */
public class AbnormalDetection {


    /**
     * detect the abnormal in wrap path to find abnormal index in each timeSeries
     *
     * @param tsI
     * @param tsJ
     * @param path
     */
    public static PathAbnormal detectWrapPath(TimeSeries tsI, TimeSeries tsJ, WarpPath path) {
        ArrayList<Double> timeSeries = new ArrayList<>();
        for(int i=0; i<path.size(); i++) {
            double a = tsI.getMeasurementVector(path.getTsIindexes().get(i))[0];
            double b = tsJ.getMeasurementVector(path.getTsJindexes().get(i))[0];
            timeSeries.add(Math.abs(a - b));
        }

        ArrayList<Integer> pathAbnormal = anomalDetect(timeSeries);

        ArrayList<Integer> pathAbnormalI = new ArrayList<>();
        ArrayList<Integer> pathAbnormalJ = new ArrayList<>();

        for(int index: pathAbnormal) {
            int indI = path.getTsIindexes().get(index);
            int indJ = path.getTsJindexes().get(index);

            if(!pathAbnormalI.contains(indI))
                pathAbnormalI.add(indI);

            if(!pathAbnormalJ.contains(indJ))
                pathAbnormalJ.add(indJ);
        }

        return new PathAbnormal(pathAbnormal, pathAbnormalI, pathAbnormalJ);
    }


    /**
     * use 3 sigma to detect abnormal in one timeSeries
     *
     * @param timeSeries
     * @return
     */
    public static ArrayList<Integer> anomalDetect(TimeSeries timeSeries) {
        ArrayList<Double> tsi = new ArrayList<>();
        for(int i=0; i<timeSeries.size(); i++) {
            tsi.add(timeSeries.getMeasurementVector(i)[0]);
        }

        return anomalDetect(tsi);
    }

    /**
     * use 3 sigma to detect abnormal in one timeSeries
     *
     * @param timeSeries
     * @return
     */
    public static ArrayList<Integer> anomalDetect(ArrayList<Double> timeSeries) {
        double sum = 0;
        for(double d: timeSeries) {
            sum += d;
        }
        double average = sum/timeSeries.size();


        double allVariance = 0;
        for(double d: timeSeries) {
            allVariance += Math.pow(d-average, 2);
        }
        double variance = allVariance/timeSeries.size();
        double sigma = Math.sqrt(variance);

        double upper = average + 3 * sigma;
        double lower = average - 3 * sigma;

        ArrayList<Integer> abnormal = new ArrayList<Integer>();
        for(int i = 0; i<timeSeries.size(); i++) {
            double d = timeSeries.get(i);
            if(d > upper || d < lower) {
                abnormal.add(i);
            }
        }

        return abnormal;
    }
}
