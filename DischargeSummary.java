class DischargeSummary {

    private final String patientId;
    private final String[] medicationCodes;

    static {
        // One-time shared initialization
        System.out.println("Discharge system initialized");
    }


    public DischargeSummary(String patientId,
                            String[] medicationCodes) {

        if (medicationCodes == null) {
            throw new IllegalArgumentException("Invalid medication codes");
        }

        for (String code : medicationCodes) {

            if (code == null ||
                !code.matches("MED-[A-Z]")) {

                throw new IllegalArgumentException(
                    "Invalid medication code"
                );
            }
        }

        this.patientId = patientId;

        // Defensive copy
        this.medicationCodes = medicationCodes.clone();
    }


    public String[] getMedicationCodes() {

        // Defensive copy
        return medicationCodes.clone();
    }


    public DischargeSummary withCorrectedMedication(
            int index,
            String newCode) {

        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException();
        }

        if (newCode == null ||
            !newCode.matches("MED-[A-Z]")) {

            throw new IllegalArgumentException(
                "Invalid medication code"
            );
        }

        String[] newCodes = medicationCodes.clone();

        newCodes[index] = newCode;

        return new DischargeSummary(
            patientId,
            newCodes
        );
    }
}


class CriticalCareDischargeSummary
        extends DischargeSummary {

    private final int icuDays;


    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);

        this.icuDays = icuDays;
    }
}


class NightlyProcessor {

    static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int critical = 0;
        int routine = 0;

        for (DischargeSummary summary : summaries) {

            if (summary == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (summary instanceof CriticalCareDischargeSummary) {
                critical++;
            } else {
                routine++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + critical + " critical-care | "
                + routine + " routine";
    }
}