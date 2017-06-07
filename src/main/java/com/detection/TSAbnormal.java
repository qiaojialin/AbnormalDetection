package com.detection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaojialin on 2017/6/7.
 */
public class TSAbnormal {
    private List<Integer> abnormal = new ArrayList<>();

    public TSAbnormal(List<Integer> indexes) {
        this.abnormal = indexes;
    }

    public TSAbnormal(String path) {
        File f = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                int index = Integer.parseInt(str);
                abnormal.add(index);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double precision(TSAbnormal de) {
        List<Integer> detected = de.abnormal;

        int size = abnormal.size();
        int detectedSize = 0;
        for(int index: abnormal) {
            if(detected.contains(index)) {
                detectedSize ++;
            }
        }
        return (double)detectedSize / (double)size;
    }

    public double falseDetection(TSAbnormal de) {
        List<Integer> detected = de.abnormal;

        int size = detected.size();
        int falseDetectedSize = 0;
        for(int detect: detected) {
            if(!abnormal.contains(detect))
                falseDetectedSize ++;
        }
        return (double)falseDetectedSize / (double)size;
    }

    public double leakDetection(TSAbnormal de) {
        List<Integer> detected = de.abnormal;

        int size = abnormal.size();
        int leakDetectedSize = 0;
        for(int index: abnormal) {
            if(!detected.contains(index)) {
                leakDetectedSize ++;
            }
        }
        return (double)leakDetectedSize / (double)size;
    }

}
