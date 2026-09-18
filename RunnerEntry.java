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

    public String announce() {
        return "Race Entry | Bib: " + bibNumber
                + " | Balance: " + balanceDue;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber
                + " | Category: " + category
                + " | Balance: " + getBalanceDue();
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + bibNumber
                + " | Team Size: " + teamSize
                + " | Balance: " + getBalanceDue();
    }
}

class RaceAnnouncer {

    static String announceAll(RaceEntry[] entries) {
        StringBuilder result = new StringBuilder();

        for (RaceEntry entry : entries) {
            result.append(entry.announce());

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;

                result.append(" [Team size via downcast: ")
                      .append(relay.getTeamSize())
                      .append("]");
            }

            result.append(" | ");
        }

        return result.toString();
    }
}