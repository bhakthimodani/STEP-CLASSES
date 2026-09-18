class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;

    private String lockerPinHash;

    private boolean patientIdSet;


    public PatientProfile() {
        this(null, null);
    }


    public PatientProfile(String name) {
        this(null, name);
    }


    public PatientProfile(String patientId, String name) {

        this.patientId = patientId;
        this.name = name;

        if (patientId != null) {
            patientIdSet = true;
        }
    }


    public String getPatientId() {
        return patientId;
    }


    public void setPatientId(String id) {

        if (!patientIdSet && id != null) {
            patientId = id;
            patientIdSet = true;
        }
    }


    public boolean isDischarged() {
        return discharged;
    }


    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }


    public void setLockerPin(String pin) {

        if (pin == null ||
            !pin.matches("\\d{4,6}")) {
            throw new IllegalArgumentException("Invalid PIN");
        }

        // Deterministic one-way transformation
        lockerPinHash = Integer.toHexString(pin.hashCode());
    }
}