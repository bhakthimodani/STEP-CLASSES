class DeliveryAccount {

    protected String studentId;
    protected double orderValue;

    static String systemName;

    static {
        systemName = "Campus Delivery System";
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    final double calculateSurgeFee(int delayMinutes) {

        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Invalid delay");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double fee = 0.0;

        int firstTier = Math.min(delayMinutes, 5);
        fee += firstTier * orderValue * 0.005;

        if (delayMinutes > 5) {
            int secondTier = Math.min(delayMinutes - 5, 10);
            fee += secondTier * orderValue * 0.01;
        }

        if (delayMinutes > 15) {
            int thirdTier = delayMinutes - 15;
            fee += thirdTier * orderValue * 0.02;
        }

        return fee;
    }

    void processAccount(DeliveryAccount account,
                        double amount,
                        int delayMinutes) {

        if (account == null) {
            return;
        }

        account.orderValue = amount;

        double fee = account.calculateSurgeFee(delayMinutes);

        System.out.println(
            "Student: " + account.studentId +
            " | Surge Fee: Rs " + fee
        );
    }

    static void processBatch(DeliveryAccount[] accounts,
                             double[] amounts,
                             int[] delayMinutesArray) {

        if (accounts == null ||
            amounts == null ||
            delayMinutesArray == null) {

            System.out.println("Invalid input arrays");
            return;
        }

        int n = Math.min(
            accounts.length,
            Math.min(amounts.length, delayMinutesArray.length)
        );

        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;

        double grandTotal = 0.0;

        DeliveryAccount processor =
                new DeliveryAccount("PROCESSOR");

        for (int i = 0; i < n; i++) {

            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }

            processor.processAccount(
                accounts[i],
                amounts[i],
                delayMinutesArray[i]
            );

            double fee =
                accounts[i].calculateSurgeFee(delayMinutesArray[i]);

            grandTotal += fee;
            processed++;

            if (accounts[i] instanceof Premium) {
                premium++;
            } else {
                regular++;
            }
        }

        System.out.println(
            processed + " processed | " +
            nullSkipped + " null skipped | " +
            premium + " premium | " +
            regular + " regular | " +
            "grand total surge fees = Rs " + grandTotal
        );
    }
}


class Premium extends DeliveryAccount {

    public Premium(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public Premium(String studentId) {
        this(studentId, 0.0);
    }
}