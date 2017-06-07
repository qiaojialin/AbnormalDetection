package com.detection;

import java.util.List;

/**
 * Created by qiaojialin on 2017/6/7.
 */
public class PathAbnormal {
    private TSAbnormal pathAbnormal;
    private TSAbnormal tsAbnormal1;
    private TSAbnormal tsAbnormal2;

    public PathAbnormal(List<Integer> pathabnormal, List<Integer> abnormal1, List<Integer> abnormal2) {
        this.pathAbnormal = new TSAbnormal(pathabnormal);
        tsAbnormal1 = new TSAbnormal(abnormal1);
        tsAbnormal2 = new TSAbnormal(abnormal2);
    }

    public TSAbnormal getAbnormal1() {
        return tsAbnormal1;
    }

    public TSAbnormal getAbnormal2() {
        return tsAbnormal2;
    }

}
