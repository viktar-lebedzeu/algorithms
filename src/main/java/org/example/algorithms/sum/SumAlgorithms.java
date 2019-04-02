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
import java.util.ListIterator;
import java.util.Map;
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
    public static List<SumResult<Integer>> findTwoSumsIndexes3(@NotNull int[] nums, int targetSum) {
        // logger.info("findTwoSumsIndexes3({}) : Searching", targetSum);
        if (nums == null || nums.length < 2) {
            logger.info("findTwoSumsIndexes3({}) : Nothing found", targetSum);
            return new ArrayList<>(0);
        }

        LinkedHashSet<SumResult<Integer>> resultNums = new LinkedHashSet<>();
        final List<Integer> numsList = Arrays.stream(nums).sorted().boxed().collect(Collectors.toList());
        HashSet<Integer> usedNumbers = new HashSet<>();
        int idx1 = 0;
        int idx2 = numsList.size() - 1;
        while (idx1 < idx2) {
            Integer num1 = numsList.get(idx1);
            Integer num2 = numsList.get(idx2);
            if (usedNumbers.contains(num1)) {
                idx1++;
                continue;
            }
            if (usedNumbers.contains(num2)) {
                idx2--;
                continue;
            }
            Integer sum = num1 + num2;
            if (sum.equals(targetSum)) {
                idx1++;
                idx2--;
                usedNumbers.add(num1);
                usedNumbers.add(num2);
                resultNums.add(new SumResult<>(num1, num2));
                continue;
            }
            if (sum < targetSum) {
                idx1++;
            } else {
                idx2--;
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
            Integer additionNum = targetSum - num;
            if (usedKeys.contains(num) || num > targetSum || additionNum < num) {
                break;
            }

            if (numberCountMap.containsKey(additionNum)
                    || (additionNum.equals(num) && (numberCountMap.get(num) > 1))) {

                usedKeys.add(num);
                usedKeys.add(additionNum);
                resultNums.add(new SumResult<>(num, additionNum));
            }
        }

        return resultNums;
    }

    public static List<SumResult<Integer>> findSums(List<Integer> nums, Integer targetSum, int depth) {
        HashMap<Integer, Integer> numberCountMap = new HashMap<>();
        nums.forEach(n -> {
            Integer count = numberCountMap.containsKey(n) ? numberCountMap.get(n) + 1 : 1;
            numberCountMap.put(n, count);
        });
        List<Integer> sortedKeys = numberCountMap.keySet().stream().sorted().collect(Collectors.toList());

        return findSumNumbers(depth, sortedKeys, targetSum, numberCountMap);
    }

    /**
     *
     * @param depth
     * @param sortedNums
     * @param targetSum
     * @param numberCountMap
     * @return
     */
    private static List<SumResult<Integer>> findSumNumbers(
            int depth, @NotNull final List<Integer> sortedNums, @NotNull Integer targetSum,
            @NotNull Map<Integer, Integer> numberCountMap) {

        // checking for the wrong parameters
        if (depth < 2
                || sortedNums == null || sortedNums.isEmpty() || (sortedNums.size() < depth)
                || numberCountMap == null) {
            return new ArrayList<>(0);
        }

        LinkedList<SumResult<Integer>> resultList = new LinkedList<>();
        HashSet<SumResult> resultSet = new HashSet<>();
        HashSet<Integer> usedKeys = new HashSet<>();

        ListIterator<Integer> iterator = sortedNums.listIterator();
        int idx = 1;
        while (iterator.hasNext()) {
            Integer num = iterator.next();
            Integer additionNum = targetSum - num;
            if (usedKeys.contains(num) || num > targetSum || additionNum < num) {
                break;
            }
            if (depth > 2) {
                usedKeys.add(num);
                List<Integer> nextList;
                Map<Integer, Integer> nextMap = new HashMap<>(numberCountMap);
                Integer count = numberCountMap.get(num);
                if (count > 1) {
                    nextList = sortedNums.subList(idx - 1, sortedNums.size());
                    nextMap.put(num, count -1);
                } else {
                    nextList = sortedNums.subList(idx, sortedNums.size());
                    nextMap.remove(num);
                }

                // calling this method recursively
                List<SumResult<Integer>> sumNumbers = findSumNumbers(depth - 1, nextList, additionNum, nextMap);
                if (!sumNumbers.isEmpty()) {
                    SumResult<Integer> sum = new SumResult<>(num);
                    sumNumbers.forEach(e -> {
                        SumResult<Integer> res = sum.add(e);
                        if (!resultSet.contains(res)) {
                            resultSet.add(res);
                            resultList.add(res);
                        }
                    });
                }

            } else {
                if (numberCountMap.containsKey(additionNum)
                        || (additionNum.equals(num) && (numberCountMap.get(num) > 1))) {

                    usedKeys.add(num);
                    usedKeys.add(additionNum);
                    resultList.add(new SumResult<>(num, additionNum));
                }
            }
            idx++;
        }
        return resultList;
    }
}
