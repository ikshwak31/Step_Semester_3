
import java.util.*;

abstract class Vehicle {
    String vehicleName;
    boolean available = true;

    Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    Sedan(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return days * 50;
    }
}

class SUV extends Vehicle {
    SUV(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return days * 80;
    }
}

class Truck extends Vehicle {
    Truck(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return days * 120;
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Rental {
    Customer customer;
    Vehicle vehicle;
    int days;
    boolean active = true;

    Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
    }
}

class RentalService {
    List<Rental> rentals = new ArrayList<>();

    void rentVehicle(Customer customer, Vehicle vehicle, int days) {
        if (days <= 0) {
            System.out.println("Rental duration must be positive.");
            return;
        }

        if (!vehicle.available) {
            System.out.println(vehicle.vehicleName
                    + " is currently unavailable.");
            return;
        }

        Rental rental = new Rental(customer, vehicle, days);
        rentals.add(rental);
        vehicle.available = false;

        double charge = vehicle.calculateCharge(days);

        System.out.println(vehicle.vehicleName
                + " rented successfully by "
                + customer.name + ".");
        System.out.println("Rental charge: $" + charge);
    }

    void returnVehicle(Customer customer, Vehicle vehicle) {
        for (Rental rental : rentals) {
            if (rental.vehicle == vehicle
                    && rental.customer == customer
                    && rental.active) {
                rental.active = false;
                vehicle.available = true;

                System.out.println(vehicle.vehicleName
                        + " returned by "
                        + customer.name + ".");
                return;
            }
        }

        System.out.println("No active rental found for "
                + customer.name + ".");
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService service = new RentalService();

        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");

        Vehicle sedanA = new Sedan("Sedan A");
        Vehicle suvB = new SUV("SUV B");

        service.rentVehicle(c1, sedanA, 3);

        service.rentVehicle(c2, sedanA, 2);

        service.returnVehicle(c1, sedanA);

        service.rentVehicle(c3, suvB, 5);
    }
}
