class LibraryMemberBroken {

    static String name;
    static String memberId;
    static int booksIssued;

    LibraryMemberBroken(String name, String memberId, int booksIssued) {
        LibraryMemberBroken.name = name;
        LibraryMemberBroken.memberId = memberId;
        LibraryMemberBroken.booksIssued = booksIssued;
    }
}

public class Main {
    public static void main(String[] args) {

        LibraryMemberBroken m1 =
            new LibraryMemberBroken("Aditi", "LM-1001", 2);

        LibraryMemberBroken m2 =
            new LibraryMemberBroken("Rohan", "LM-1002", 3);

        System.out.println(m1.name);
        System.out.println(m2.name);

        /*
         * name is static, so there is only ONE shared copy.
         * memberId is static, so it is shared by all members.
         * booksIssued is static, so all members share one value.
         *
         * Therefore, creating Rohan overwrites Aditi's data.
         */
    }
}