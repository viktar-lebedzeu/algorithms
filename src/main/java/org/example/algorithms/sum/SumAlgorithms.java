package org.example.algorithms.sum;

import com.sun.istack.internal.NotNull;
import org.example.algorithms.aop.LogMethodTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Viktar Lebedzeu
 */
public class SumAlgorithms {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(SumAlgorithms.class);

    /**
     * First algorithm for searching pairs of numbers whose sum is equals to target number
     * @param nums Array of numbers
     * @param targetSum Target sum
     * @return List of number pairs
     */
    @LogMethodTime
    public static List<SumResult<Integer>> findTwoSumsIndexes1(@NotNull int[] nums, int targetSum) {
        // logger.info("findTwoSumsIndexes1({}) : Searching", targetSum);
        if (nums == null || nums.length < 2) {
            logger.info("findTwoSumsIndexes1({}) : Nothing found", targetSum);
            return new ArrayList<>(0);
        }

        final List<Integer> numsList = Arrays.stream(nums).sorted().boxed().collect(Collectors.toList());
        LinkedHashSet<SumResult<Integer>> resultNums = new LinkedHashSet<>();
        for (int idx1 = 0; idx1 < numsList.size(); idx1++) {
            for (int idx2 = idx1 + 1; idx2 < numsList.size(); idx2++) {
                int sum = numsList.get(idx1) + numsList.get(idx2);
                if (sum == targetSum) {
                    resultNums.add(new SumResult<>(numsList.get(idx1), numsList.get(idx2)));
                }
            }
        }

        // List<SumResult<Integer>> filteredResultNums = new ArrayList<>(new HashSet<>(resultNums));
        // logger.debug("Found {} pairs (filtered : {})", resultNums.size(), filteredResultNums.size());
        return new ArrayList<>(resultNums);
    }

    @LogMethodTime
    public static List<SumResult<Integer>> findTwoSumsIndexes2(@NotNull int[] nums, int targetSum) {
        // logger.info("findTwoSumsIndexes2({}) : Searching", targetSum);
        if (nums == null || nums.length < 2) {
            logger.info("findTwoSumsIndexes2({}) : Nothing found", targetSum);
            return new ArrayList<>(0);
        }

        HashMap<Integer, Long> numberCountMap = new HashMap<>();
        Arrays.stream(nums).forEach(n -> {
            Long count = numberCountMap.containsKey(n) ? numberCountMap.get(n) + 1L : 1L;
            numberCountMap.put(n, count);
        });
        List<Integer> sortedKeys = numberCountMap.keySet().stream().sorted().collect(Collectors.toList());
        // logger.debug("sortedKeys : {}", sortedKeys);

        LinkedList<SumResult<Integer>> resultNums = new LinkedList<>();

        HashSet<Integer> usedKeys = new HashSet<>();
        for (Integer num : sortedKeys) {
            if (usedKeys.contains(num)) {
                break;
            }
            Integer additionNum = targetSum - num;

            if (numberCountMap.containsKey(additionNum)
                    || (additionNum.equals(num) && (numberCountMap.get(num) > 1))) {

                usedKeys.add(num);
                usedKeys.add(additionNum);
                resultNums.add(new SumResult<>(num, additionNum));
            }
        }

        return resultNums;
    }
}
