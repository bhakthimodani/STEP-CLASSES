class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double balanceDue;

    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount = 0;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty()
                || bibNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.balanceDue = entryFee;
    }

    public void pay(double amount) {
        balanceDue -= amount;
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    protected void applyLateFee(double amount) {
        balanceDue += amount;
        lateFeeHistory[lateFeeCount] = amount;
        lateFeeCount++;
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[lateFeeCount];

        for (int i = 0; i < lateFeeCount; i++) {
            copy[i] = lateFeeHistory[i];
        }

        return copy;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}