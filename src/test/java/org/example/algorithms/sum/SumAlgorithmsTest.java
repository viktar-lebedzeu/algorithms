package org.example.algorithms.sum;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        long start = System.currentTimeMillis();
        final List<SumResult<Integer>> results1 = SumAlgorithms.findTwoSumsIndexes1(nums, targetSum);
        long duration1 = System.currentTimeMillis() - start;

        // logger.info(StringUtils.repeat("=", 100));
        start = System.currentTimeMillis();
        final List<SumResult<Integer>> results2 = SumAlgorithms.findTwoSumsIndexes2(nums, targetSum);
        long duration2 = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        final List<SumResult<Integer>> results3 = SumAlgorithms.findTwoSumsIndexes3(nums, targetSum);
        long duration3 = System.currentTimeMillis() - start;

        logger.debug("Durations ({}) - {} ({}) : {} ({}) : {} ({})", nums.length,
                results1.size(), duration1, results2.size(), duration2, results3.size(), duration3);
    }

    @Test
    public void testSum() {
        int[] nums = new int[] {1, 3, -2, 4, 3, 2, 8, 3};
        final List<SumResult<Integer>> results1 = SumAlgorithms.findTwoSumsIndexes1(nums, 6);
        logger.debug("results : {}", results1);
        final List<SumResult<Integer>> results2 = SumAlgorithms.findTwoSumsIndexes2(nums, 6);
        logger.debug("results : {}", results2);
        final List<SumResult<Integer>> results3 = SumAlgorithms.findTwoSumsIndexes3(nums, 6);
        logger.debug("results : {}", results3);
        Assertions.assertEquals(results1, results2);
        Assertions.assertEquals(results1, results3);
        Assertions.assertEquals(results2, results3);
        Assertions.assertEquals(new SumResult<>(-2, 8), new SumResult<>(8, -2));
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
