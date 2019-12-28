package com.example.kotshare.utils;

import java.util.List;

public class Calculator
{
    public static Float mean(List<Float> floats)
    {
        int n = floats.size();
        Float sum = 0.f;
        if(n == 0) return sum;

        for(Float oneFloat : floats)
            sum += oneFloat;
        return sum/n;
    }
}
