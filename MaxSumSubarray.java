
import java.util.Scanner;

public class MaxSumSubarray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] sales = new int[n];

        for (int i = 0; i < n; i++) {
            sales[i] = sc.nextInt();
        }

        int k = sc.nextInt();

        if (k < 1 || k > n) {
            System.out.println("Invalid k");
            sc.close();
            return;
        }

        int sum = 0;

        for (int i = 0; i < k; i++) {
            sum += sales[i];
        }

        int maxSum = sum;

        for (int i = k; i < n; i++) {
            sum = sum - sales[i - k] + sales[i];

            if (sum > maxSum) {
                maxSum = sum;
            }
        }

        System.out.println("Maximum Sum = " + maxSum);

        sc.close();
    }
}
