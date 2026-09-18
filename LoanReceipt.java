import java.util.Arrays;

public final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    static {
        // One-time shared initialization
        System.setProperty("loan.receipt.initialized", "true");
    }

    public LoanReceipt(String memberId, String[] bookIds) {

        if (bookIds == null) {
            throw new IllegalArgumentException("construction rejected");
        }

        for (String id : bookIds) {

            if (!isValidBookId(id)) {
                throw new IllegalArgumentException(
                    "construction rejected");
            }
        }

        this.memberId = memberId;
        this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
    }

    private static boolean isValidBookId(String id) {

        if (id == null || id.length() != 6) {
            return false;
        }

        if (!id.startsWith("BK-")) {
            return false;
        }

        return Character.isDigit(id.charAt(3))
            && Character.isDigit(id.charAt(4))
            && Character.isDigit(id.charAt(5));
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return Arrays.copyOf(bookIds, bookIds.length);
    }

    public LoanReceipt withCorrectedBookId(int index,
                                           String newId) {

        if (index < 0 || index >= bookIds.length ||
            !isValidBookId(newId)) {
            throw new IllegalArgumentException(
                "invalid correction");
        }

        String[] corrected =
            Arrays.copyOf(bookIds, bookIds.length);

        corrected[index] = newId;

        return new LoanReceipt(memberId, corrected);
    }
}


class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId,
                                    String[] bookIds,
                                    String roomNumber) {

        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}


class CirculationProcessor {

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts == null) {
            return "0 processed | 0 null skipped | " +
                   "0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | " +
               nullSkipped + " null skipped | " +
               referenceOnly + " reference-only | " +
               regular + " regular";
    }
}