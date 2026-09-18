class EventTicket {
    protected double balanceDue;

    public EventTicket(double basePrice) {
        balanceDue = basePrice;
    }

    public void printTicket() {
        System.out.println("Standard | Balance: " + balanceDue);
    }

    public double getBalanceDue() {
        return balanceDue;
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    @Override
    public void printTicket() {
        System.out.println(
            "Workshop | Track: " + track +
            " | Balance: " + balanceDue
        );
    }

    public String getTrack() {
        return track;
    }
}

public class Main {

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();

        for (EventTicket ticket : tickets) {
            ticket.printTicket();

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop =
                    (WorkshopTicket) ticket;

                sb.append(
                    "Workshop | Track: " +
                    workshop.getTrack() +
                    " | Balance: " +
                    workshop.getBalanceDue() +
                    " [Track via downcast: " +
                    workshop.getTrack() +
                    "] | "
                );
            } else {
                sb.append(
                    "Standard | Balance: " +
                    ticket.getBalanceDue() +
                    " | "
                );
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };

        System.out.println(batchPrint(tickets));
    }
}