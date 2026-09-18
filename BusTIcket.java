import java.util.HashSet;
import java.util.Set;

class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;

    // Only parameterized constructor
    public BusTicket(String passengerName, String destination) {

        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid passenger name");
        }

        if (!passengerName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Passenger name must contain only letters");
        }

        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid destination");
        }

        this.passengerName = passengerName;
        this.destination = destination;
        this.checkedIn = false;
    }

    public void markCheckedIn() {
        if (checkedIn) {
            throw new IllegalStateException("Passenger already checked in");
        }

        checkedIn = true;
    }

    public String getKey() {
        return passengerName.trim().toLowerCase() + "|" +
               destination.trim().toLowerCase();
    }

    public static void processBatch(String[][] rawBookings) {

        Set<String> acceptedBookings = new HashSet<>();

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        for (String[] booking : rawBookings) {

            if (booking == null || booking.length < 2) {
                rejected++;
                continue;
            }

            try {
                BusTicket ticket =
                    new BusTicket(booking[0], booking[1]);

                String key = ticket.getKey();

                if (acceptedBookings.contains(key)) {
                    duplicates++;
                } else {
                    acceptedBookings.add(key);
                    valid++;
                }

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid);
        System.out.println("Rejected: " + rejected);
        System.out.println("Duplicates skipped: " + duplicates);
    }
}

public class Main {
    public static void main(String[] args) {

        String[][] bookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        BusTicket.processBatch(bookings);
    }
}