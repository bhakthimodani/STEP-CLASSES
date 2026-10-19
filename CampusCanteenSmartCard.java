
import java.util.*;

interface PricingPlan {
    double calculatePrice(double price);
    String getName();
}

class DayScholarPlan implements PricingPlan {
    public double calculatePrice(double price) {
        return price;
    }

    public String getName() {
        return "Day Scholar";
    }
}

class HostellerPlan implements PricingPlan {
    public double calculatePrice(double price) {
        return price * 0.90;
    }

    public String getName() {
        return "Hosteller";
    }
}

class StaffPlan implements PricingPlan {
    public double calculatePrice(double price) {
        return price * 0.80;
    }

    public String getName() {
        return "Staff";
    }
}

class Transaction {
    String description;
    double amount;

    Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
}

class Purchase {
    String item;
    double amount;
    boolean refunded = false;

    Purchase(String item, double amount) {
        this.item = item;
        this.amount = amount;
    }
}

class SmartCard {
    private String cardId;
    private PricingPlan plan;
    private double balance = 0;
    private boolean blocked = false;

    private List<Transaction> transactions = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();

    SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
    }

    void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up rejected: Card is blocked.");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up rejected: Minimum top-up is ₹100.");
            return;
        }

        if (balance + amount > 5000) {
            System.out.println("Top-up rejected: Maximum balance is ₹5000.");
            return;
        }

        balance += amount;
        transactions.add(new Transaction("Top-up", amount));

        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n",
                cardId, amount, balance);
    }

    void purchase(String item, double originalPrice) {
        if (blocked) {
            System.out.println("Purchase rejected: Card is blocked.");
            return;
        }

        if (originalPrice < 0) {
            System.out.println("Purchase rejected: Invalid price.");
            return;
        }

        double charged = Math.round(
                plan.calculatePrice(originalPrice) * 100.0) / 100.0;

        if (balance < charged) {
            System.out.printf(
                    "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    charged, balance);
            return;
        }

        balance -= charged;
        transactions.add(new Transaction(item, -charged));
        purchases.add(new Purchase(item, charged));

        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n",
                item, charged, balance);
    }

    void refund(String item) {
        for (Purchase p : purchases) {
            if (p.item.equals(item)) {
                if (p.refunded) {
                    System.out.println("Refund rejected: " + item
                            + " has already been refunded.");
                    return;
                }

                if (blocked) {
                    System.out.println("Refund rejected: Card is blocked.");
                    return;
                }

                if (balance + p.amount > 5000) {
                    System.out.println("Refund rejected: Maximum balance exceeded.");
                    return;
                }

                balance += p.amount;
                p.refunded = true;
                transactions.add(new Transaction("Refund: " + item, p.amount));

                System.out.printf(
                        "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                        p.amount, item, balance);
                return;
            }
        }

        System.out.println("Refund rejected: Purchase not found.");
    }

    void block() {
        blocked = true;
        System.out.println(cardId + " blocked.");
    }

    void unblock() {
        blocked = false;
        System.out.println(cardId + " unblocked.");
    }

    void miniStatement() {
        System.out.print("Mini-statement for " + cardId + ": ");

        double total = 0;

        for (int i = 0; i < transactions.size(); i++) {
            double amount = transactions.get(i).amount;
            total += amount;

            System.out.printf("%s%.2f",
                    amount >= 0 ? "+" : "", amount);

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(" = ₹%.2f.%n", total);
    }

    public double getBalance() {
        return balance;
    }
}

public class CampusCanteenSmartCard {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045",
                new HostellerPlan());

        card.topUp(500);
        card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Other Items", 400);
        card.refund("Veg Thali");
        card.refund("Veg Thali");
        card.miniStatement();
    }
}
