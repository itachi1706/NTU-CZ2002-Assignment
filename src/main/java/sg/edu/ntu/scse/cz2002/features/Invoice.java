package sg.edu.ntu.scse.cz2002.features;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An extension of the {@link Order} class for completed orders
 *
 * @author Kenneth Soh
 * @version 1.0
 * @since 2019-04-08
 */
public class Invoice extends Order {

    /**
     * Enum for the payment types you can select
     */
    public enum PaymentType{ PAYMENT_CASH("Cash"), PAYMENT_CARD("Debit/Credit Card"), PAYMENT_NETS("NETS"), PAYMENT_EZLINK("EZ-Link");

        /**
         * Readable Name for the payment type
         */
        private String s;

        /**
         * Constructor for the enum usage
         * @param s Readable Name
         */
        PaymentType(String s) { this.s = s; }


        /**
         * Get name of the enum in a string form
         * @return Readable name of the payment type
         */
        @Override
        public String toString() {
            return s;
        }
    }

    /**
     * Filename of the receipt in the data folder
     */
    private String receipt;

    /**
     * Type of payment paid for the invoice
     */
    private PaymentType paymentType;

    /**
     * Total of the order
     */
    private double total;

    /**
     * Amount paid for the order
     */
    private double amountPaid;

    /**
     * Constructor to generate the invoice from. This has to accept an Order object as that is what we use to determine stuff
     * @param o Order object
     * @param receiptPath File name of the receipt
     * @param paymentType Type of payment paid
     * @param total Total cost of the order
     * @param amountPaid Amount paid for the order
     */
    public Invoice(@NotNull Order o, String receiptPath, PaymentType paymentType, double total, double amountPaid) {
        super(o);
        this.receipt = receiptPath;
        this.total = total;
        this.amountPaid = amountPaid;
        this.paymentType = paymentType;
    }

    /**
     * A method to read from a CSV string to convert to an object instance
     * This needs to be overridden if you need to retrieve CSV data from file
     * @param csv A string array of the CSV file
     */
    public Invoice(@NotNull String[] csv) {
        super(csv);
        this.total = Double.parseDouble(csv[8]);
        this.amountPaid = Double.parseDouble(csv[9]);
        switch (Integer.parseInt(csv[10])) {
            case 1: this.paymentType = PaymentType.PAYMENT_CARD; break;
            case 2: this.paymentType = PaymentType.PAYMENT_NETS; break;
            case 3: this.paymentType = PaymentType.PAYMENT_EZLINK; break;
            case 0:
            default: this.paymentType = PaymentType.PAYMENT_CASH; break;
        }
        this.receipt = csv[11];
    }

    /**
     * A method to convert to CSV
     * This needs to be overridden if you need to save files to CSV
     * @return A String array of the CSV file
     */
    @Override
    public String[] toCsv() {
        ArrayList<String> stuff = new ArrayList<>();
        Collections.addAll(stuff, super.toCsv());
        stuff.add(this.total + "");
        stuff.add(this.amountPaid + "");
        stuff.add(this.paymentType.ordinal() + "");
        stuff.add(this.receipt);
        return stuff.toArray(new String[0]);
    }

    /**
     * Gets the path to the receipt
     * @return Filename for the receipt
     */
    public String getReceipt() {
        return receipt;
    }

    /**
     * Payment type of the invoice
     * @return Payment Type
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Total of the invoice
     * @return Total amount
     */
    public double getTotal() {
        return total;
    }

    /**
     * Amount Paid of the invoice. If it is cash
     * If payment type is not cash, this would be the same as the total price
     * @return The amount paid if cash
     */
    public double getAmountPaid() {
        return amountPaid;
    }
}

