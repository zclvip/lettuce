package com.zcl.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaocl1 on 2018/7/16.
 */
public class Solution {
    public static int[] twoSum(int[] nums,int target){

        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            temp = nums[i];
            for (int j = 0; j < nums.length; j++) {
                if (i!= j && temp + nums[j] == target){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        int[] nums = {7,2,12,15};
        int target=14;
        int[] result = twoSum2(nums,target);
        System.out.println(result[0]+" "+result[1]);
    }
}
