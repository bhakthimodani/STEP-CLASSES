final class BoardingPenaltyCalculator {

    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {

        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException(
                "Minimum penalty percent cannot be negative"
            );
        }

        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(
        double ticketFare,
        int minutesLate
    ) {

        if (ticketFare < 0) {
            throw new IllegalArgumentException(
                "Ticket fare cannot be negative"
            );
        }

        if (minutesLate < 0) {
            throw new IllegalArgumentException(
                "Minutes late cannot be negative"
            );
        }

        // On-time boarding = no penalty
        if (minutesLate == 0) {
            return 0.0;
        }

        double penalty = 0.0;

        // First bracket: minutes 1-5
        int firstTier = Math.min(minutesLate, 5);

        penalty += firstTier * ticketFare * 0.005;

        // Second bracket: minutes 6-15
        if (minutesLate > 5) {

            int secondTier =
                Math.min(minutesLate, 15) - 5;

            penalty += secondTier * ticketFare * 0.01;
        }

        // Third bracket: minute 16 onwards
        if (minutesLate > 15) {

            int thirdTier = minutesLate - 15;

            penalty += thirdTier * ticketFare * 0.02;
        }

        // Minimum flat-fee floor
        double minimumPenalty =
            ticketFare * minimumPenaltyPercent / 100.0;

        penalty = Math.max(penalty, minimumPenalty);

        return penalty;
    }
}

public class Main {
    public static void main(String[] args) {

        BoardingPenaltyCalculator calculator =
            new BoardingPenaltyCalculator(1.0);

        System.out.println(
            calculator.calculatePenalty(1000, 0)
        );

        System.out.println(
            calculator.calculatePenalty(1000, 1)
        );

        System.out.println(
            calculator.calculatePenalty(1000, 16)
        );
    }
}