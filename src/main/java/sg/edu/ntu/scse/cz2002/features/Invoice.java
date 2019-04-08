package sg.edu.ntu.scse.cz2002.features;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

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

    public Invoice(@NotNull Order o, String receiptPath, PaymentType paymentType, double total, double amountPaid) {
        super(o);
        this.receipt = receiptPath;
        this.total = total;
        this.amountPaid = amountPaid;
        this.paymentType = paymentType;
    }

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
}

