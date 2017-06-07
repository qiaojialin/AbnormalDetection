/*
 * FastDtwTest.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.detection;

import com.dtw.WarpPath;
import com.timeseries.TimeSeries;
import com.util.DistanceFunction;
import com.util.DistanceFunctionFactory;
import com.dtw.TimeWarpInfo;

/**
 * Used to detect abnormal in two timeSeries
 */
public class Detection
{
      public static void main(String[] args) {
         args = new String[4];
         args[0] = "src/main/resources/sensor1.csv";
         args[1] = "src/main/resources/sensor2.csv";
         args[2] = "1";
         args[3] = "EuclideanDistance";

         final TimeSeries tsI = new TimeSeries(args[0], false, false, ',');
         final TimeSeries tsJ = new TimeSeries(args[1], false, false, ',');

         final DistanceFunction distFn;
         if (args.length < 4) {
            distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");
         }
         else {
            distFn = DistanceFunctionFactory.getDistFnByName(args[3]);
         }

         System.out.println("timeSeries1: " + AbnormalDetection.anomalDetect(tsI));
         System.out.println("timeSeries2: " + AbnormalDetection.anomalDetect(tsJ));

         final TimeWarpInfo info = com.dtw.FastDTW.getWarpInfoBetween(tsI, tsJ, Integer.parseInt(args[2]), distFn);
         WarpPath path = info.getPath();

         TSAbnormal tsAbnormal2 = new TSAbnormal("src/main/resources/abnormalIndex");

         PathAbnormal pathAbnormal = AbnormalDetection.detectWrapPath(tsI, tsJ, path);

         System.out.println(tsAbnormal2.precision(pathAbnormal.getAbnormal2()));
         System.out.println(tsAbnormal2.falseDetection(pathAbnormal.getAbnormal2()));
         System.out.println(tsAbnormal2.leakDetection(pathAbnormal.getAbnormal2()));
      }

}
