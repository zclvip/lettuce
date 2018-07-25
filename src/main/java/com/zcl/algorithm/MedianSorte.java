package com.zcl.algorithm;

import java.util.Arrays;

/**
 * Created by zhaocl1 on 2018/7/17.
 */
public class MedianSorte {

    public static void main(String[] args) {
        int[] nums1 = {1,2,5,6,8,13};
        int[] nums2 = {3,4,7,9,10,11,14};
        double result = findMedianSortedArrays(nums1, nums2);
        System.out.println("result="+result);

        int[] merge = merge(nums1,nums2);
        if (merge.length % 2 == 0){
            //偶数
            int left = merge.length / 2;
            int right = merge.length / 2 +1;
            result = (merge[left-1] + merge[right-1]) / 2.0;
        }else {
            result = merge[(merge.length+1) / 2 -1];
        }
        System.out.println("result="+result);

        for (int i : merge){
            System.out.print(i + " ");
        }
        System.out.println();

        int[] n1 = {1,2,5,6,8};
        int[] n2 = {3,4};
        int mid = findMidMerge(n1,n2);
        System.out.println("mid="+mid);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length, left = (m + n + 1) / 2, right = (m + n + 2) / 2;
        return (findKth(nums1, nums2, left) + findKth(nums1, nums2, right)) / 2.0;
    }

    static int  findKth(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        if (m > n) return findKth(nums2, nums1, k);
        if (m == 0) return nums2[k - 1];
        if (k == 1) return Math.min(nums1[0], nums2[0]);
        int i = Math.min(m, k / 2), j = Math.min(n, k / 2);
        if (nums1[i - 1] > nums2[j - 1]) {
            return findKth(nums1, Arrays.copyOfRange(nums2, j, n), k - j);
        } else {
            return findKth(Arrays.copyOfRange(nums1, i, m), nums2, k - i);
        }
    }

    static int findMidMerge(int[] nums1, int[] nums2){
        int n1 = nums1.length,n2 = nums2.length;
        int mid = (n1+n2+1)/2;
        int i=0,j=0;
        for (int k=1;k<mid;k++){
            if (i < n1 && j < n2){
                if (nums1[i] < nums2[j]){
                    i++;
                }else {
                    j++;
                }
            }else if (i>=n1){
                j++;
            }else if (j >=n2){
                i++;
            }
        }

        if (i< n1 && j < n2){
            return Math.min(nums1[i], nums2[j]);
        }else if (i >=n1){
            return nums2[j];
        }else if (j >= n2){
            return nums1[i];
        }
        return 0;
    }

    /**
     * 两个有序数组合并
     * @param nums1
     * @param nums2
     * @return
     */
    static int[] merge(int[] nums1, int[] nums2){
        int newLength = nums1.length + nums2.length;
        int[] result = new int[newLength];
        int i=0,j=0,h=0;
        while (i<nums1.length || j<nums2.length){
            if (i == nums1.length && j < nums2.length){
                result[h++] = nums2[j++];
            }else if (j == nums2.length && i < nums1.length){
                result[h++] = nums1[i++];
            }else if (nums1[i] <= nums2[j]){
                result[h++] = nums1[i++];
            }else if (nums1[i] > nums2[j]){
                result[h++] = nums2[j++];
            }
        }
        return result;
    }
}
