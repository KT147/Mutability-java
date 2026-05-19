package Bank;

public class Transaction {
    private int routingNumber;
    private int customerId;
    private long transactionId;
    private double transactionAmount;

    public Transaction(double transactionAmount, long transactionId, int customerId, int routingNumber) {
        this.transactionAmount = transactionAmount;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.routingNumber = routingNumber;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "%015d:%020d:%015d:%012.2f"
                .formatted(routingNumber, customerId, transactionId, transactionAmount);
    }
}
