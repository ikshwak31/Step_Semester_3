
import java.time.LocalDate;
import java.util.*;

abstract class HotelRoom {
    String roomName;
    double pricePerNight;

    HotelRoom(String roomName, double pricePerNight) {
        this.roomName = roomName;
        this.pricePerNight = pricePerNight;
    }

    abstract double calculatePrice(int nights);
}

class StandardRoom extends HotelRoom {
    StandardRoom(String name) {
        super(name, 100);
    }

    double calculatePrice(int nights) {
        return pricePerNight * nights;
    }
}

class DeluxeRoom extends HotelRoom {
    DeluxeRoom(String name) {
        super(name, 150);
    }

    double calculatePrice(int nights) {
        return pricePerNight * nights * 1.10;
    }
}

class Suite extends HotelRoom {
    Suite(String name) {
        super(name, 250);
    }

    double calculatePrice(int nights) {
        return pricePerNight * nights;
    }
}

class HotelCustomer {
    String name;

    HotelCustomer(String name) {
        this.name = name;
    }
}

class Reservation {
    HotelCustomer customer;
    HotelRoom room;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate cancellationDeadline;
    boolean active = true;

    Reservation(HotelCustomer customer, HotelRoom room,
                LocalDate startDate, LocalDate endDate,
                LocalDate cancellationDeadline) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = cancellationDeadline;
    }

    int getNights() {
        return (int) java.time.temporal.ChronoUnit.DAYS
                .between(startDate, endDate);
    }
}

class HotelBookingService {
    List<Reservation> reservations = new ArrayList<>();

    boolean isAvailable(HotelRoom room, LocalDate start,
                        LocalDate end) {
        if (!end.isAfter(start)) {
            return false;
        }

        for (Reservation r : reservations) {
            if (r.room == room && r.active
                    && start.isBefore(r.endDate)
                    && end.isAfter(r.startDate)) {
                return false;
            }
        }

        return true;
    }

    void checkAvailability(HotelRoom room, LocalDate start,
                           LocalDate end) {
        if (isAvailable(room, start, end)) {
            System.out.println(room.roomName
                    + " is available from "
                    + start + " to " + end + ".");
        } else {
            System.out.println(room.roomName
                    + " is not available from "
                    + start + " to " + end + ".");
        }
    }

    void bookRoom(HotelCustomer customer, HotelRoom room,
                  LocalDate start, LocalDate end,
                  LocalDate cancellationDeadline) {
        if (!isAvailable(room, start, end)) {
            System.out.println(room.roomName
                    + " is not available from "
                    + start + " to " + end + ".");
            return;
        }

        Reservation reservation = new Reservation(
                customer, room, start, end, cancellationDeadline
        );

        reservations.add(reservation);

        System.out.println("Reservation confirmed for "
                + customer.name + ", "
                + room.roomName + " ("
                + start + " to " + end + ").");
        System.out.println("Price: $"
                + room.calculatePrice(reservation.getNights()));
    }

    void cancelReservation(HotelCustomer customer,
                           HotelRoom room, LocalDate today) {
        for (Reservation r : reservations) {
            if (r.customer == customer && r.room == room && r.active) {
                if (today.isAfter(r.cancellationDeadline)) {
                    System.out.println(
                            "Cancellation deadline has passed."
                    );
                    return;
                }

                r.active = false;
                System.out.println("Reservation for "
                        + customer.name + ", "
                        + room.roomName + " ("
                        + r.startDate + " to "
                        + r.endDate
                        + ") cancelled successfully.");
                return;
            }
        }

        System.out.println("No active reservation found.");
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        HotelBookingService service = new HotelBookingService();

        HotelCustomer customerA = new HotelCustomer("Customer A");
        HotelCustomer customerB = new HotelCustomer("Customer B");
        HotelCustomer customerC = new HotelCustomer("Customer C");

        HotelRoom standard = new StandardRoom("Standard Room 101");
        HotelRoom deluxe = new DeluxeRoom("Deluxe Room 201");

        LocalDate jan1 = LocalDate.of(2026, 1, 1);
        LocalDate jan3 = LocalDate.of(2026, 1, 3);
        LocalDate jan5 = LocalDate.of(2026, 1, 5);
        LocalDate jan7 = LocalDate.of(2026, 1, 7);

        service.checkAvailability(standard, jan1, jan5);

        service.bookRoom(
                customerA, standard, jan1, jan5,
                LocalDate.of(2025, 12, 30)
        );

        service.bookRoom(
                customerB, standard, jan3, jan7,
                LocalDate.of(2026, 1, 1)
        );

        service.cancelReservation(
                customerA, standard, LocalDate.of(2025, 12, 29)
        );

        service.bookRoom(
                customerC, deluxe,
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 12),
                LocalDate.of(2026, 2, 8)
        );
    }
}
