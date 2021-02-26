package com.mictlan.math.legacy.geometry;

public enum PointOrientation {
        COLLINEAR(0),
        LIES_LEFT(-1),
        LIES_RIGHT(1);

        private final int flag;
        PointOrientation(int i) {
            flag = i;
        }

        int getInt(){
            return flag;
        }
}
