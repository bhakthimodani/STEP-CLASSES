final class SurgeFeeCalculator {

    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    final double calculateSurgeFee(double orderValue, int delayMinutes) {

        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Negative values are not allowed");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double fee = 0.0;

        // First 5 minutes: 0.5% per minute
        int firstTier = Math.min(delayMinutes, 5);
        fee += firstTier * orderValue * 0.005;

        // Minutes 6-15: 1% per minute
        if (delayMinutes > 5) {
            int secondTier = Math.min(delayMinutes - 5, 10);
            fee += secondTier * orderValue * 0.01;
        }

        // Minute 16 onwards: 2% per minute
        if (delayMinutes > 15) {
            int thirdTier = delayMinutes - 15;
            fee += thirdTier * orderValue * 0.02;
        }

        // Minimum surge floor
        double minimumFee =
                orderValue * minimumSurgePercent / 100.0;

        return Math.max(fee, minimumFee);
    }
}