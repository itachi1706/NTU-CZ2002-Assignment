package sg.edu.ntu.scse.cz2002.features;

import org.jetbrains.annotations.NotNull;

public class Invoice extends Order {

    public enum PaymentType{ PAYMENT_CASH("Cash"), PAYMENT_CARD("Debit/Credit Card"), PAYMENT_NETS("NETS"), PAYMENT_EZLINK("EZ-Link");

        private String s;

        PaymentType(String s) { this.s = s; }


        @Override
        public String toString() {
            return s;
        }
    }

    private String receipt;

    private PaymentType paymentType;

    private double total, amountPaid;

    public Invoice(@NotNull Order o, String receiptPath, int paymentType, double total, double amountPaid) {
        super(o);
        this.receipt = receiptPath;
        this.total = total;
        this.amountPaid = amountPaid;

        // TODO: Process Payment Type
    }

    public Invoice(@NotNull String[] csv) {
        super(csv);
        // TODO: Code Stub
    }

    @Override
    public String[] toCsv() {
        // TODO: Code Stub
        return super.toCsv();
    }
}

