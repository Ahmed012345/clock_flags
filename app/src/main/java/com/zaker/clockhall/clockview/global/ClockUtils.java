package com.zaker.clockhall.clockview.global;

import java.util.TreeMap;

public class ClockUtils {

    private final static TreeMap<Integer, String> romanMap = new TreeMap<Integer, String>();
    private final static TreeMap<Integer, String> arabicMap = new TreeMap<Integer, String>();

    static {

        romanMap.put(12, "XII");
        romanMap.put(11, "XI");
        romanMap.put(10, "X");
        romanMap.put(9, "IX");

        romanMap.put(8, "VIII");
        romanMap.put(7, "VII");
        romanMap.put(6, "VI");
        romanMap.put(5, "V");

        romanMap.put(4, "IV");
        romanMap.put(3, "III");
        romanMap.put(2, "II");
        romanMap.put(1, "I");

    }

    static {
        arabicMap.put(1, "1");
        arabicMap.put(2, "2");
        arabicMap.put(3, "3");
        arabicMap.put(4, "4");
        arabicMap.put(5, "5");
        arabicMap.put(6, "6");
        arabicMap.put(7, "7");
        arabicMap.put(8, "8");
        arabicMap.put(9, "9");
        arabicMap.put(10, "10");
        arabicMap.put(11, "11");
        arabicMap.put(12, "12");
    }

    public final static String toRoman(int number) {
        int l =  romanMap.floorKey(number);
        if ( number == l ) {
            return romanMap.get(number);
        }
        return romanMap.get(l) + toRoman(number-l);
    }

    public final static String toArabic(int number) {
        int l =  arabicMap.floorKey(number);
        if ( number == l ) {
            return arabicMap.get(number);
        }
        return arabicMap.get(l) + toArabic(number-l);
    }
}
