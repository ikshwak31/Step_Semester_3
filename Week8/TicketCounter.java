
import java.util.*;

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Seat {
    private String seatId;

    public Seat(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatId() {
        return seatId;
    }

    public abstract double getPrice();
}

class RegularSeat extends Seat {
    public RegularSeat(String id) {
        super(id);
    }

    @Override
    public double getPrice() {
        return 150;
    }
}

class PremiumSeat extends Seat {
    public PremiumSeat(String id) {
        super(id);
    }

    @Override
    public double getPrice() {
        return 250;
    }
}

class ReclinerSeat extends Seat {
    public ReclinerSeat(String id) {
        super(id);
    }

    @Override
    public double getPrice() {
        return 400;
    }
}

class Show {
    private String showTime;
    private boolean started = false;
    private Map<String, Seat> availableSeats = new HashMap<>();
    private Set<String> bookedSeats = new HashSet<>();

    public Show(String showTime) {
        this.showTime = showTime;
    }

    public String getShowTime() {
        return showTime;
    }

    public boolean hasStarted() {
        return started;
    }

    public void startShow() {
        started = true;
    }

    public void addSeat(Seat seat) {
        availableSeats.put(seat.getSeatId(), seat);
    }

    public Seat getSeat(String seatId) {
        return availableSeats.get(seatId);
    }

    public boolean isAvailable(String seatId) {
        return availableSeats.containsKey(seatId)
                && !bookedSeats.contains(seatId);
    }

    public boolean reserveSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            if (!isAvailable(seat.getSeatId())) {
                return false;
            }
        }

        for (Seat seat : seats) {
            bookedSeats.add(seat.getSeatId());
        }

        return true;
    }

    public void releaseSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            bookedSeats.remove(seat.getSeatId());
        }
    }
}

class Booking {
    private Customer customer;
    private Show show;
    private List<Seat> seats;
    private boolean cancelled = false;

    public Booking(Customer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);
    }

    public double getTotal() {
        double total = 0;

        for (Seat seat : seats) {
            total += seat.getPrice();
        }

        return total;
    }

    public String confirm() {
        List<String> ids = new ArrayList<>();

        for (Seat seat : seats) {
            ids.add(seat.getSeatId());
        }

        return "Booking confirmed for " + customer.getName()
                + ": " + String.join(", ", ids)
                + ". Total: ₹"
                + String.format("%.2f", getTotal()) + ".";
    }

    public String cancel() {
        if (cancelled) {
            return "Booking has already been cancelled.";
        }

        if (show.hasStarted()) {
            return "Cannot cancel: the show has already started.";
        }

        show.releaseSeats(seats);
        cancelled = true;

        List<String> ids = new ArrayList<>();

        for (Seat seat : seats) {
            ids.add(seat.getSeatId());
        }

        return customer.getName() + "'s booking cancelled. "
                + "Seats " + String.join(", ", ids) + " released.";
    }
}

public class TicketCounter {

    public static Booking bookSeats(
            Customer customer, Show show, List<String> seatIds) {

        if (seatIds == null || seatIds.isEmpty()) {
            System.out.println("Booking rejected: no seats selected.");
            return null;
        }

        if (seatIds.size() > 6) {
            System.out.println("Booking rejected: maximum 6 seats.");
            return null;
        }

        List<Seat> seats = new ArrayList<>();

        for (String id : seatIds) {
            Seat seat = show.getSeat(id);

            if (seat == null || !show.isAvailable(id)) {
                System.out.println("Seat " + id
                        + " is already booked or unavailable for this show.");
                return null;
            }

            seats.add(seat);
        }

        if (!show.reserveSeats(seats)) {
            System.out.println("Booking rejected: a seat is unavailable.");
            return null;
        }

        Booking booking = new Booking(customer, show, seats);
        System.out.println(booking.confirm());

        return booking;
    }

    public static void main(String[] args) {

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Show show = new Show("7 PM");

        show.addSeat(new RegularSeat("A1"));
        show.addSeat(new RegularSeat("A2"));
        show.addSeat(new PremiumSeat("F5"));
        show.addSeat(new ReclinerSeat("R1"));

        Booking ashaBooking = bookSeats(
                asha, show, Arrays.asList("A1", "A2", "F5"));

        bookSeats(ravi, show, Arrays.asList("A2"));

        bookSeats(ravi, show, Arrays.asList("R1"));

        if (ashaBooking != null) {
            System.out.println(ashaBooking.cancel());
        }

        bookSeats(neha, show, Arrays.asList("A2"));
    }
}
