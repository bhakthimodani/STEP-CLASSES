class AccessRuleEngine {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        // Same class
        if (accessorContext.equals("SAME_CLASS")) {
            return "ALLOWED";
        }

        // Same package
        if (accessorContext.equals("SAME_PACKAGE")) {

            if (fieldModifier.equals("private"))
                return "DENIED";

            return "ALLOWED";
        }

        // Different package
        if (accessorContext.equals("DIFFERENT_PACKAGE")) {

            if (fieldModifier.equals("public"))
                return "ALLOWED";

            return "DENIED";
        }

        // Subclass in different package using its own type
        if (accessorContext.equals(
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {

            if (fieldModifier.equals("protected") ||
                fieldModifier.equals("public"))
                return "ALLOWED";

            return "DENIED";
        }

        // Subclass in different package using parent type
        if (accessorContext.equals(
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")) {

            if (fieldModifier.equals("public"))
                return "ALLOWED";

            return "DENIED";
        }

        return "DENIED";
    }


    static String describeContext(String accessorContext) {

        String[] words = accessorContext.toLowerCase().split("_");

        String result = "";

        for (String word : words) {

            result += Character.toUpperCase(word.charAt(0))
                    + word.substring(1) + " ";
        }

        return result.trim();
    }
}