class Canteen {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    int compareTo(Canteen other) {

        // Higher trust score first
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }

        // Code comparison ignoring case
        int codeCompare = this.canteenCode.compareToIgnoreCase(other.canteenCode);

        if (codeCompare != 0) {
            return codeCompare;
        }

        // Name length as final tie-breaker
        return this.canteenName.length() - other.canteenName.length();
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {

        // Bubble sort
        for (int i = 0; i < canteens.length - 1; i++) {

            for (int j = 0; j < canteens.length - 1 - i; j++) {

                if (canteens[j].compareTo(canteens[j + 1]) > 0) {
                    Canteen temp = canteens[j];
                    canteens[j] = canteens[j + 1];
                    canteens[j + 1] = temp;
                }
            }
        }

        return canteens;
    }
}