
interface Printable {
    String printLabel();
}

class PackageBox implements Printable {

    private String trackingId;

    public PackageBox(String trackingId) {
        if (trackingId == null || trackingId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Tracking ID cannot be blank");
        }

        this.trackingId = trackingId;
    }

    @Override
    public String printLabel() {
        return "Package label: " + trackingId;
    }
}

class Invoice implements Printable {

    private String invoiceNumber;

    public Invoice(String invoiceNumber) {
        if (invoiceNumber == null
                || invoiceNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Invoice number cannot be blank");
        }

        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String printLabel() {
        return "Invoice label: " + invoiceNumber;
    }
}

class PrintableTest {

    public static void printAll(Printable[] items) {
        for (Printable item : items) {
            System.out.println(item.printLabel());
        }
    }

    public static void main(String[] args) {

        PackageBox p = new PackageBox("TRK-88");
        Invoice i = new Invoice("INV-42");

        System.out.println(p.printLabel());
        System.out.println(i.printLabel());

        Printable[] items = {p, i};

        printAll(items);
    }
}
