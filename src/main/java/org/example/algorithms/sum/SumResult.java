package org.example.algorithms.sum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Result of summing of numbers
 * @author Viktar Lebedzeu
 */
public class SumResult<T extends Number> {
    /** Result numbers */
    private ArrayList<T> resultNums;

    private SumResult() {
    }

    @SafeVarargs
    SumResult(T... nums) {
        resultNums = new ArrayList<>(nums.length);
        resultNums.addAll(Arrays.asList(nums));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(resultNums).toHashCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SumResult<T> rhs = (SumResult<T>) obj;
        return new EqualsBuilder()
                .append(new HashSet<>(resultNums), new HashSet<>(rhs.resultNums))
                .isEquals();
    }

    @Override
    public String toString() {
        return resultNums.toString();
    }
}
