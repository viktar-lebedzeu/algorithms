package org.example.algorithms.sum;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        start = System.currentTimeMillis();
        final List<SumResult<Integer>> results4 = SumAlgorithms.findSums(
                Arrays.stream(nums).boxed().collect(Collectors.toList()), targetSum, 2);
        long duration4 = System.currentTimeMillis() - start;

/*
        start = System.currentTimeMillis();
        final List<SumResult<Integer>> results5 = SumAlgorithms.findSums(
                Arrays.stream(nums).boxed().collect(Collectors.toList()), targetSum, 5);
        long duration5 = System.currentTimeMillis() - start;
*/

        logger.debug("Durations ({}) - {} ({}) : {} ({}) : {} ({}) : {} ({})", nums.length,
                results1.size(), duration1, results2.size(), duration2,
                results3.size(), duration3, results4.size(), duration4);
//        logger.debug("    --- 5 ({}) - {} ({})", nums.length, results5.size(), duration5);
    }

    @Test
    public void testFindSumNumbers() {
        List<Integer> list = Arrays.asList(6, 3, 2, 1, 1, 2, 3, 0, 4, 4, 4, 6);
        List<SumResult<Integer>> sums1 = SumAlgorithms.findSums(list, 5, 3);
        List<SumResult<Integer>> sums2 = SumAlgorithms.findSums(list, 5, 4);
        List<SumResult<Integer>> sums3 = SumAlgorithms.findSums(list, 5, 5);
        logger.debug("sums[1] : {}", sums1);
        logger.debug("sums[2] : {}", sums2);
        logger.debug("sums[3] : {}", sums3);
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
        int edgeValue = repetition * 11000;
        int[] nums = new int[arraySize];
        for (int idx = 0; idx < arraySize; idx++) {
            int sign = RandomUtils.nextInt(0, 100);
            nums[idx] = ((sign < 40) ? -1 : 1) * RandomUtils.nextInt(0, edgeValue);
        }
        return nums;
    }
}
