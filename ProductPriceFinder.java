
public class ProductPriceFinder {

    static int lowerBound(int[] prices, int target) {
        int low = 0, high = prices.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (prices[mid] < target)
                low = mid + 1;
            else
                high = mid;
        }

        return low;
    }

    static int upperBound(int[] prices, int target) {
        int low = 0, high = prices.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (prices[mid] <= target)
                low = mid + 1;
            else
                high = mid;
        }

        return low;
    }

    public static void main(String[] args) {
        int[] prices = {100, 150, 150, 200, 300, 450};
        int lower = 150, upper = 300;

        int start = lowerBound(prices, lower);
        int end = upperBound(prices, upper);

        System.out.println("Lower bound index " + start);
        System.out.println("Upper bound index " + end);
        System.out.println("Count " + (end - start));
    }
}
