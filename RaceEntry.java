class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double balanceDue;

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

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String announce() {
        return "Runner Entry | Bib: " + bibNumber
                + " | Category: " + category
                + " | Balance: " + getBalanceDue();
    }
}