
import java.util.*;

enum ParcelStatus {
    BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED
}

interface ShippingType {
    double calculateCharge(double weight);
    String getName();
}

class StandardShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 40 + 10 * weight;
    }

    public String getName() {
        return "Standard";
    }
}

class ExpressShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 80 + 15 * weight;
    }

    public String getName() {
        return "Express";
    }
}

class FragileShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return new StandardShipping().calculateCharge(weight) + 50;
    }

    public String getName() {
        return "Fragile";
    }
}

interface NotificationChannel {
    void notify(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    public void notify(String id, ParcelStatus status) {
        System.out.println("[SMS] " + id + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    public void notify(String id, ParcelStatus status) {
        System.out.println("[Email] " + id + " is now " + status + ".");
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Parcel {
    String id;
    double weight;
    ShippingType shipping;
    ParcelStatus status = ParcelStatus.BOOKED;
    List<NotificationChannel> channels;

    Parcel(String id, double weight, ShippingType shipping,
           List<NotificationChannel> channels) {
        this.id = id;
        this.weight = weight;
        this.shipping = shipping;
        this.channels = new ArrayList<>(channels);
    }

    void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notify(id, status);
        }
    }

    void updateStatus(ParcelStatus next) {
        if (next.ordinal() != status.ordinal() + 1) {
            System.out.println("Invalid transition: " + status + " → "
                    + next + " is not allowed.");
            return;
        }

        status = next;
        notifyChannels();
    }

    void cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + id
                    + " can be cancelled only while BOOKED.");
            return;
        }

        System.out.println("Parcel " + id + " cancelled.");
    }
}

class ParcelService {
    Parcel book(String id, double weight, ShippingType shipping,
                List<NotificationChannel> channels) {
        if (weight <= 0) {
            System.out.println("Booking failed: Invalid weight.");
            return null;
        }

        Parcel p = new Parcel(id, weight, shipping, channels);

        System.out.println("Parcel " + id + " booked (" + shipping.getName()
                + ", " + weight + " kg).");
        System.out.printf("Charge: ₹%.2f%n", shipping.calculateCharge(weight));

        p.notifyChannels();
        return p;
    }
}

public class SwiftShipParcelTracker {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();

        List<NotificationChannel> channels = Arrays.asList(
                new SmsChannel(), new EmailChannel());

        Parcel p = service.book("P101", 2, new ExpressShipping(), channels);

        p.updateStatus(ParcelStatus.PICKED_UP);
        p.cancel();
        p.updateStatus(ParcelStatus.IN_TRANSIT);
        p.updateStatus(ParcelStatus.DELIVERED);
    }
}
