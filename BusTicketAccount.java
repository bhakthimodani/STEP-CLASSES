class BusTicketAccount {

    protected String bookingId;
    protected double ticketFare;

    // One-time class-level setup
    static {
        System.out.println("Fleet reconciliation system initialized.");
    }

    // Full constructor
    public BusTicketAccount(String bookingId, double ticketFare) {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Invalid booking ID"
            );
        }

        if (ticketFare < 0) {
            throw new IllegalArgumentException(
                "Ticket fare cannot be negative"
            );
        }

        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    // Provisional constructor
    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    // Flat-rate version: 1% per late minute
    public final double calculatePenalty(int minutesLate) {

        if (minutesLate < 0) {
            throw new IllegalArgumentException(
                "Minutes late cannot be negative"
            );
        }

        return ticketFare * 0.01 * minutesLate;
    }

    public void processAccount(
        BusTicketAccount account,
        double amount,
        int minutesLate
    ) {

        if (account == null) {
            return;
        }

        if (amount < 0 || minutesLate < 0) {
            return;
        }

        double penalty = account.calculatePenalty(minutesLate);

        System.out.println(
            "Booking: " + account.bookingId +
            " | Amount: " + amount +
            " | Penalty: " + penalty
        );
    }

    public static void processBatch(
        BusTicketAccount[] accounts,
        double[] amounts,
        int[] minutesLateArray
    ) {

        if (accounts == null ||
            amounts == null ||
            minutesLateArray == null) {

            System.out.println("Invalid batch");
            return;
        }

        // Process only matching indexes
        int length = Math.min(
            accounts.length,
            Math.min(amounts.length, minutesLateArray.length)
        );

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;

        double grandTotalPenalty = 0.0;

        for (int i = 0; i < length; i++) {

            BusTicketAccount account = accounts[i];

            // Null safety
            if (account == null) {
                nullSkipped++;
                continue;
            }

            if (amounts[i] < 0 || minutesLateArray[i] < 0) {
                continue;
            }

            // instanceof-based dispatch
            if (account instanceof Sleeper) {
                sleeperCount++;
            } else {
                regularCount++;
            }

            account.processAccount(
                account,
                amounts[i],
                minutesLateArray[i]
            );

            grandTotalPenalty +=
                account.calculatePenalty(minutesLateArray[i]);

            processed++;
        }

        System.out.println();
        System.out.println(processed + " processed");
        System.out.println(nullSkipped + " null skipped");
        System.out.println(sleeperCount + " sleeper");
        System.out.println(regularCount + " regular");
        System.out.println(
            "Grand total penalties = " +
            grandTotalPenalty
        );
    }
}


// Sleeper account
class Sleeper extends BusTicketAccount {

    public Sleeper(
        String bookingId,
        double ticketFare
    ) {
        super(bookingId, ticketFare);
    }

    public Sleeper(String bookingId) {
        super(bookingId);
    }

    @Override
    public final double calculatePenalty(int minutesLate) {

        if (minutesLate < 0) {
            throw new IllegalArgumentException(
                "Minutes late cannot be negative"
            );
        }

        // Sleeper settles at half the regular penalty
        return ticketFare * 0.005 * minutesLate;
    }
}


public class Main {

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {
            new Sleeper("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {
            1200,
            900,
            700
        };

        int[] minutesLateArray = {
            10,
            5,
            0
        };

        BusTicketAccount.processBatch(
            accounts,
            amounts,
            minutesLateArray
        );
    }
}