class Main {
    static String normalizeReference(String raw) {
        raw = raw.trim();

        String bankCode = raw.substring(0, 3).toUpperCase();
        String rest = raw.substring(3);

        return bankCode + rest;
    }

    static String validateAndFormat(String reference) {
        reference = normalizeReference(reference);

        if (reference.length() != 14)
            return "Invalid: wrong length";

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i)))
                return "Invalid: bank code must be 3 letters";
        }

        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i)))
                return "Invalid: body must contain only digits";
        }

        String bank = reference.substring(0, 3);
        String date = reference.substring(3, 9);
        String seq = reference.substring(9, 14);

        StringBuilder sb = new StringBuilder();
        sb.append("[")
          .append(bank)
          .append("] DATE: ")
          .append(date.substring(0, 2))
          .append("/")
          .append(date.substring(2, 4))
          .append("/")
          .append(date.substring(4, 6))
          .append(" | SEQ: ")
          .append(seq);

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(
            validateAndFormat("  hdf03022600042  ")
        );
    }
}