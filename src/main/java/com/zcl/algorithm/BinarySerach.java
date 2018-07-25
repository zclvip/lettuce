package com.zcl.algorithm;

/**
 * Created by zhaocl1 on 2018/7/18.
 */
public class BinarySerach {

    public static void main(String[] args) {
        int[] nums = {1,3,5};
        int result = binary(nums,5);
        System.out.println("result="+result);
    }

    /**
     * 二分查找
     * @param nums
     * @param key
     * @return
     */
    static int binary(int[] nums,int key){
        int left=0;
        int right = nums.length -1;
        while (left <= right){
            int mid = (left+right) / 2;
            if (nums[mid]==key){
                return mid;
            }
            if (nums[mid] < key){
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return -1;
    }


    /** 套路
     * // 这里必须是 <=
     while (left <= right) {
     int mid = (left + right) / 2;
     if (array[mid] ? key) {
     //... right = mid - 1;
     }
     else {
     // ... left = mid + 1;
     }
     }
     return xxx;
     *
     *
     *
     */
}
