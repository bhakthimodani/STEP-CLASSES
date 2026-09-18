import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    public LibraryMember() {
        this(null, null);
    }

    public LibraryMember(String name) {
        this(null, name);
    }

    public LibraryMember(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {

        if (membershipId == null && id != null) {
            membershipId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {

        if (answer == null) {
            securityAnswer = null;
            return;
        }

        try {
            MessageDigest md =
                MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(answer.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            securityAnswer = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}