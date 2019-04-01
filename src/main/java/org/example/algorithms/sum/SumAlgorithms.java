package org.example.algorithms.sum;

import com.sun.istack.internal.NotNull;
import org.example.algorithms.aop.LogMethodTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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
        // logger.info("findTwoSums({}) : Searching", targetSum);
        if (nums == null || nums.length < 2) {
            logger.info("findTwoSums({}) : Nothing found", targetSum);
            return new ArrayList<>(0);
        }
        LinkedList<SumResult<Integer>> resultNums = new LinkedList<>();
        for (int idx1 = 0; idx1 < nums.length; idx1++) {
            for (int idx2 = idx1 + 1; idx2 < nums.length; idx2++) {
                int sum = nums[idx1] + nums[idx2];
                if (sum == targetSum) {
                    resultNums.add(new SumResult<>(nums[idx1], nums[idx2]));
                }
            }
        }

        List<SumResult<Integer>> filteredResultNums = new ArrayList<>(new HashSet<>(resultNums));
        logger.debug("Found {} pairs (filtered : {})", resultNums.size(), filteredResultNums.size());
        return new ArrayList<>(resultNums);
    }
}
