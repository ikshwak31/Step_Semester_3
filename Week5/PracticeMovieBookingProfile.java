public class PracticeMovieBookingProfile {

    private String name;
    private boolean confirmed;
    private String otp;

    public PracticeMovieBookingProfile() {
        confirmed = false;
    }

    public PracticeMovieBookingProfile(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setOtp(String otp) {
        if (otp.matches("\\d{4,6}")) {
            this.otp = otp;
        }
    }


    public static void main(String[] args) {

        PracticeMovieBookingProfile p =
                new PracticeMovieBookingProfile("Rahul Dev");

        System.out.println(p.getName());

        p.setConfirmed(true);
        System.out.println(p.isConfirmed());

        p.setOtp("4471");

        System.out.println("OTP set successfully");
    }
}