package org.myproject.engine;

import java.util.List;

public class ListUtils{
    public static float[] ListToArrayFloat(List<Float> floats){
        float[] floatsArray = new float[floats.size()];
        for (int i = 0; i < floats.size(); i++) {
            floatsArray[i] = floats.get(i).floatValue();
        }

        return floatsArray;
    }


    public static int[] ListToArrayInt(List<Integer> ints){

        int[] intsArray = new int[ints.size()];

        for(int i = 0; i < ints.size(); i++){
            intsArray[i] = ints.get(i).intValue();
        }

        return intsArray;

    }
}
