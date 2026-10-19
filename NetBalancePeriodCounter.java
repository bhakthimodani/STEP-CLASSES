
import java.util.*;

public class NetBalancePeriodCounter {

    public static long countPeriods(
            int[] transactions, int k) {

        Map<Long, Integer> frequency = new HashMap<>();

        frequency.put(0L, 1);

        long prefixSum = 0;
        long count = 0;

        for (int value : transactions) {
            prefixSum += value;

            long required = prefixSum - k;

            count += frequency.getOrDefault(required, 0);

            frequency.put(
                prefixSum,
                frequency.getOrDefault(prefixSum, 0) + 1
            );
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};

        System.out.println(countPeriods(transactions, 7));

        int[] transactions2 = {1, 2, 3};

        System.out.println(countPeriods(transactions2, 10));
    }
}
