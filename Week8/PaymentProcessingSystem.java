
import java.util.*;

class ShopCustomer {
    String name;

    ShopCustomer(String name) {
        this.name = name;
    }
}

class Product {
    String name;
    double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class OrderItem {
    Product product;
    int quantity;

    OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double getSubtotal() {
        return product.price * quantity;
    }
}

interface PaymentMethod {
    boolean processPayment(double amount, String orderName);
}

class CreditCardPayment implements PaymentMethod {
    public boolean processPayment(double amount, String orderName) {
        System.out.println("Payment initiated via Credit Card for "
                + orderName + ".");
        System.out.println("Payment for " + orderName + " successful.");
        return true;
    }
}

class PayPalPayment implements PaymentMethod {
    public boolean processPayment(double amount, String orderName) {
        System.out.println("Payment initiated via PayPal for "
                + orderName + ".");
        System.out.println("Payment for " + orderName + " failed.");
        return false;
    }
}

class BankTransferPayment implements PaymentMethod {
    public boolean processPayment(double amount, String orderName) {
        System.out.println("Payment initiated via Bank Transfer for "
                + orderName + ".");
        System.out.println("Payment for " + orderName + " successful.");
        return true;
    }
}

class ShoppingOrder {
    String orderName;
    ShopCustomer customer;
    List<OrderItem> items = new ArrayList<>();
    String status = "Pending";

    ShoppingOrder(String orderName, ShopCustomer customer) {
        this.orderName = orderName;
        this.customer = customer;

        System.out.println("Order created for "
                + customer.name + ".");
    }

    void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        items.add(new OrderItem(product, quantity));
    }

    double getTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    void makePayment(PaymentMethod method) {
        if (items.isEmpty()) {
            System.out.println(
                    "Cannot process payment for an empty order."
            );
            return;
        }

        if (status.equals("Paid")) {
            System.out.println("Order " + orderName
                    + " is already paid.");
            return;
        }

        boolean successful = method.processPayment(
                getTotal(), orderName
        );

        if (successful) {
            status = "Paid";
        }

        System.out.println("Order status: " + status);
    }
}

public class PaymentProcessingSystem {
    public static void main(String[] args) {
        ShopCustomer customerX = new ShopCustomer("Customer X");
        ShopCustomer customerY = new ShopCustomer("Customer Y");
        ShopCustomer customerZ = new ShopCustomer("Customer Z");

        Product productA = new Product("Product A", 100);
        Product productB = new Product("Product B", 200);
        Product productC = new Product("Product C", 150);

        ShoppingOrder orderX = new ShoppingOrder("Order X", customerX);
        orderX.addProduct(productA, 2);
        orderX.addProduct(productB, 1);
        orderX.makePayment(new CreditCardPayment());

        System.out.println();

        ShoppingOrder orderY = new ShoppingOrder("Order Y", customerY);
        orderY.makePayment(new CreditCardPayment());

        System.out.println();

        ShoppingOrder orderZ = new ShoppingOrder("Order Z", customerZ);
        orderZ.addProduct(productC, 1);
        orderZ.makePayment(new PayPalPayment());
    }
}
