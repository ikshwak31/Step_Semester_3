public class PracticeCineScreen {

    private int seatsTotal;
    private int seatsAvailable;

    public PracticeCineScreen(int seatsTotal) {

        if (seatsTotal <= 0) {
            throw new IllegalArgumentException("Invalid seat count");
        }

        this.seatsTotal = seatsTotal;
        this.seatsAvailable = seatsTotal;
    }

    public void bookSeat() {

        if (seatsAvailable > 0) {
            seatsAvailable--;
        }
    }

    public void cancelBooking() {

        if (seatsAvailable < seatsTotal) {
            seatsAvailable++;
        }
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }


    public static void main(String[] args) {

        PracticeCineScreen c = new PracticeCineScreen(2);

        c.bookSeat();
        c.bookSeat();
        c.bookSeat();

        System.out.println(c.getSeatsAvailable());

        c.cancelBooking();
        c.cancelBooking();
        c.cancelBooking();

        System.out.println(c.getSeatsAvailable());
    }
}