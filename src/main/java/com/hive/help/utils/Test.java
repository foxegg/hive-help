package com.hive.help.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Test {
    public static void main1(String[] args) {
        String a = "asdasdasdfdfsadf";
        String b = "aaaaf";
        //canPartition(a,b);
        canPartition2(new int[]{1, 2, 1, 4, 3, 2, 1, 3, 4, 1});
        //canPartition(a,b);
    }

    public static boolean canPartition(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        int[][] counts = new int[aChars.length + 1][bChars.length + 1];
        for (int i = 1; i <= aChars.length; i++) {
            for (int j = 1; j <= bChars.length; j++) {
                if (aChars[i - 1] == bChars[j - 1]) {
                    counts[i][j] = counts[i - 1][j - 1] + 1;
                } else {
                    counts[i][j] = Math.max(counts[i - 1][j], counts[i][j - 1]);
                }
            }
        }
        for (int i = 1; i <= aChars.length; i++) {
            for (int j = 1; j <= bChars.length; j++) {
                System.out.print(counts[i][j] + " ");
            }
            System.out.println();
        }
        return true;
    }

    public static boolean canPartition1(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        int[][] counts = new int[aChars.length + 1][bChars.length + 1];
        for (int i = 1; i <= aChars.length; i++) {
            for (int j = 1; j <= bChars.length; j++) {
                if (aChars[i - 1] == bChars[j - 1]) {
                    counts[i][j] = counts[i - 1][j - 1] + 1;
                } else {
                    counts[i][j] = Math.max(counts[i - 1][j], counts[i][j - 1]);
                }
            }
        }
        for (int i = 1; i <= aChars.length; i++) {
            for (int j = 1; j <= bChars.length; j++) {
                System.out.print(counts[i][j] + " ");
            }
            System.out.println();
        }
        return true;
    }


    public static boolean canPartition2(int[] nums) {
        int[][] counts = new int[nums.length][nums.length];
        counts[0][0] = 1;
        for (int i = 1; i < nums.length; i++) {
            counts[i][i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && counts[j][j] >= counts[i][i]) {
                    counts[i][i] = counts[j][j] + 1;
                }
            }
        }
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts.length; j++) {
                System.out.print(counts[i][j] + " ");
            }
            System.out.println();
        }
        ArrayList a;
        LinkedList b;
        return true;
    }

    public static void main(String[] args) {
        int[] nums1 = {6, 7};
        int[] nums2 = {6, 0, 4};
        int k = 5;
        int[] out = maxNumber(nums1, nums2, k);
        for (int i = 0; i < out.length; i++) {
            System.out.print(out[i]);
        }
    }

    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int index1 = 0;
        int index2 = 0;
        int[] out = new int[k];
        int max1, max2, indexTmp1, indexTmp2;
        for (int j = 0; j < k; j++) {
            indexTmp1 = -1;
            indexTmp2 = -1;
            max1 = -1;
            max2 = -1;
            for (int i = index1; i < nums1.length; i++) {
                if (nums1[i] > max1) {
                    max1 = nums1[i];
                    indexTmp1 = i;
                }
            }
            for (int i = index2; i < nums2.length; i++) {
                if (nums2[i] > max2) {
                    max2 = nums2[i];
                    indexTmp2 = i;
                }
            }
            int rest1 = 0;
            if (indexTmp1 >= 0) {
                rest1 = nums1.length - indexTmp1 - 1 + nums2.length - index2 - 1;
            }
            int rest2 = 0;
            if (indexTmp2 >= 0) {
                rest2 = nums1.length - index1 - 1 + nums2.length - indexTmp2 - 1;
            }
            int needRest = k - j - 1;
            if (max1 > max2) {
                if (rest1 >= needRest) {
                    index1 = indexTmp1 + 1;
                }
            } else {
                index2 = indexTmp2 + 1;
            }
            out[j] = Math.max(max1, max2);
        }
        return out;
    }
}
