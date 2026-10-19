import java.time.LocalDate;
import java.util.*;

abstract class Room {
    protected String roomNumber;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public abstract double getPricePerNight();

    public String getRoomNumber() {
        return roomNumber;
    }
}

class StandardRoom extends Room {
    public StandardRoom(String number) {
        super(number);
    }

    public double getPricePerNight() {
        return 150.0;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String number) {
        super(number);
    }

    public double getPricePerNight() {
        return 200.0;
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(String number) {
        super(number);
    }

    public double getPricePerNight() {
        return 300.0;
    }
}

class Customer {
    String name;

    public Customer(String name) {
        this.name = name;
    }
}

class Reservation {
    Room room;
    Customer customer;
    LocalDate checkIn;
    LocalDate checkOut;
    LocalDate cancellationDeadline;
    boolean cancelled = false;

    public Reservation(Room room, Customer customer,
                       LocalDate checkIn, LocalDate checkOut) {
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cancellationDeadline = checkIn.minusDays(1);
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return !cancelled && start.isBefore(checkOut)
                && end.isAfter(checkIn);
    }

    public double getPrice() {
        long nights = java.time.temporal.ChronoUnit.DAYS
                .between(checkIn, checkOut);
        return nights * room.getPricePerNight();
    }
}

class BookingManager {
    private List<Reservation> reservations = new ArrayList<>();

    public Reservation bookRoom(Room room, Customer customer,
                                LocalDate start, LocalDate end) {
        if (!start.isBefore(end)) {
            System.out.println("Booking failed: Invalid dates.");
            return null;
        }

        for (Reservation r : reservations) {
            if (r.room.getRoomNumber().equals(room.getRoomNumber())
                    && r.overlaps(start, end)) {
                System.out.println("Booking failed: Room "
                        + room.getRoomNumber() + " is not available.");
                return null;
            }
        }

        Reservation reservation =
                new Reservation(room, customer, start, end);
        reservations.add(reservation);

        System.out.println("Room " + room.getRoomNumber()
                + " booked from " + start + " to " + end + ".");
        System.out.printf("Total price: $%.2f%n",
                reservation.getPrice());

        return reservation;
    }

    public void cancelReservation(Reservation r, LocalDate today) {
        if (r == null || r.cancelled) {
            System.out.println("Cancellation failed.");
            return;
        }

        if (today.isBefore(r.cancellationDeadline)
                || today.isEqual(r.cancellationDeadline)) {
            r.cancelled = true;
            System.out.println("Reservation for Room "
                    + r.room.getRoomNumber()
                    + " cancelled successfully.");
        } else {
            System.out.println("Cancellation deadline has passed.");
        }
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Customer customer = new Customer("John");

        Room deluxe = new DeluxeRoom("101");
        Room standard = new StandardRoom("205");

        Reservation r1 = manager.bookRoom(
                deluxe, customer,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 5));

        manager.bookRoom(
                standard, customer,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        manager.bookRoom(
                deluxe, customer,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        manager.cancelReservation(
                r1, LocalDate.of(2024, 11, 29));
    }
}