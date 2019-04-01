package org.example.algorithms.sum;

import org.apache.commons.lang3.RandomUtils;
import org.example.algorithms.aop.LogMethodTime;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test suite for checking features of SumAlgorithms class
 * @author Viktar Lebedzeu
 */
public class SumAlgorithmsTest {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(SumAlgorithmsTest.class);

    @RepeatedTest(10)
    public void testSumTwoNums(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        int targetSum = RandomUtils.nextInt(0, repetitionInfo.getCurrentRepetition() * 500);
        int[] nums = generateNumbers(repetitionInfo.getCurrentRepetition());
        SumAlgorithms.findTwoSumsIndexes1(nums, targetSum);
    }

    private static int[] generateNumbers(int repetition) {
        int arraySize = repetition * 10000;
        int edgeValue = repetition * 250;
        int[] nums = new int[arraySize];
        for (int idx = 0; idx < arraySize; idx++) {
            int sign = RandomUtils.nextInt(0, 100);
            nums[idx] = ((sign < 50) ? -1 : 1) * RandomUtils.nextInt(0, edgeValue);
        }
        return nums;
    }
}
