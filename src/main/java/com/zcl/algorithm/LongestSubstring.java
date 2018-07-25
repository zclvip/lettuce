package com.zcl.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by zhaocl1 on 2018/7/17.
 */
public class LongestSubstring {

    public static void main(String[] args) {
//        String s = "abcabcbb";
//        int res = lengthOfLongestSubstring(s);
//        System.out.println("res:"+res);

        String ss = "abcabcbb";
        int ress = getRes(ss);
        System.out.println("res:"+ress);
    }




    private static int getRes(String s) {
        Map<Integer,Character> map = new HashMap<>();
        char[] charstr = s.toCharArray();
        int left =0,right=0,res = 0;
        while (right < charstr.length){
            if (map.containsValue(charstr[right])){
                map.remove(left);
                ++left;
            }else {
                map.put(right,charstr[right]);
                ++right;
                res = Math.max(res,map.size());
            }
        }
        return res;
    }

    //abcabcbb
    public static int lengthOfLongestSubstring(String s) {
        int res = 0, left = 0, right = 0;
        HashSet<Character> t = new HashSet<Character>();
        while (right < s.length()) {
            if (!t.contains(s.charAt(right))) {
                t.add(s.charAt(right++));
                res = Math.max(res, t.size());
            } else {
                t.remove(s.charAt(left++));
            }
        }
        return res;
    }
}
