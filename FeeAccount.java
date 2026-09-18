class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        } else {
            System.out.println("Payment rejected");
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount);
        pay(amount);
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    ScholarshipFeeAccount(String regNo, double totalFee,
                          double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    double effectiveDue() {
        return getDue() * (1 - scholarshipPercent / 100);
    }
}

public class Main {
    public static void main(String[] args) {

        FeeAccount plain =
                new FeeAccount("RA001", 150000, 0);

        HostelFeeAccount hostel =
                new HostelFeeAccount("RA002", 200000, 0);

        ScholarshipFeeAccount scholarship =
                new ScholarshipFeeAccount("RA003", 180000, 0, 20);

        plain.pay(150000);

        if (hostel instanceof HostelFeeAccount) {
            ((HostelFeeAccount) hostel).payInTwoInstallments(30000);
        }

        scholarship.pay(-5000);

        System.out.println("Plain account due: Rs " + plain.getDue());

        System.out.println("Hostel account due: Rs "
                + hostel.getDue());

        System.out.println("Scholarship account effective due: Rs "
                + scholarship.effectiveDue());
    }
}