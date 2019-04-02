package org.example.algorithms.sum;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Result of summing of numbers
 * @author Viktar Lebedzeu
 */
public class SumResult<T extends Number> {
    /** Result numbers */
    private List<T> resultNums;

    private SumResult() {
    }

    @SafeVarargs
    SumResult(T... nums) {
        resultNums = Arrays.stream(nums).sorted().collect(Collectors.toList());
    }

    SumResult(@NotNull Collection<T> nums) {
        resultNums = new ArrayList<>(nums);
    }

    public List<T> getResultNums() {
        return resultNums;
    }

    /**
     * Adds the given sum result object to the current one
     * @param res SumResult object to be added
     * @return Result sum result
     */
    SumResult<T> add(@NotNull SumResult<T> res) {
        SumResult<T> result = new SumResult<>();
        int size = (resultNums != null ? resultNums.size() : 0) +
                (res != null && res.resultNums != null ? res.resultNums.size() : 0);
        result.resultNums = new ArrayList<>(size);
        if (resultNums != null) {
            result.resultNums.addAll(resultNums);
        }
        if (res != null && res.resultNums != null) {
            result.resultNums.addAll(res.resultNums);
        }
        result.resultNums = result.resultNums.stream().sorted().collect(Collectors.toList());
        return result;
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
