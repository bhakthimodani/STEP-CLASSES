
public class LongestBudgetFriendlyStreak {

    public static int[] longestStreak(
            int[] costs, int budget) {

        int left = 0;
        int length = 0;
        int startIndex = -1;
        long sum = 0;

        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];

            while (sum > budget && left <= right) {
                sum -= costs[left];
                left++;
            }

            int currentLength = right - left + 1;

            if (sum <= budget && currentLength > length) {
                length = currentLength;
                startIndex = left;
            }
        }

        return new int[]{length, startIndex};
    }

    public static void main(String[] args) {
        int[] costs = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        int budget = 8;

        int[] result = longestStreak(costs, budget);

        System.out.println(
            "(" + result[0] + ", " + result[1] + ")"
        );

        int[] costs2 = {9, 10};
        int[] result2 = longestStreak(costs2, 8);

        System.out.println(
            "(" + result2[0] + ", " + result2[1] + ")"
        );
    }
}
