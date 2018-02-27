import java.util.UUID;

public abstract class StockAction {
    private Stock stock;
    private UUID uuid = UUID.randomUUID() ; // a unique identifier for the stock
    private double price;
    private StockActionStatus status = StockActionStatus.PENDING;

    public boolean isComplete() {
        return (stock.getName() != StockName.NONE && price > 0);
    }

    public String toServerString()
    {
        return "stockName=" + stock.getName().name() +
                "\nprice=" + price +
                "\nstatus=" + status +
                "\nuuid=" + uuid +
                "\n";
    }

    public void hydrateFromServerString(String strRepresentation){
        String fieldAssignements[] = strRepresentation.split("\n");
        String assignementParts[] = null;
        String fieldName = null;
        String fieldValue = null;
        for (String fieldAssignement: fieldAssignements) {
            assignementParts = fieldAssignement.split("=");
            if(assignementParts.length != 2) break;
            fieldName = assignementParts[0];
            fieldValue = assignementParts[1];

            switch (fieldName) {
                case "stockName": setStock(new Stock(StockName.valueOf(fieldValue)));
                case "price": setPrice(Double.parseDouble(fieldValue));
                case "status": setStatus(StockActionStatus.valueOf(fieldValue));
                case "uuid": setUUID(UUID.fromString(fieldValue));
            }
        }
    }

    public void setStock(StockName stockName)
    {
        this.stock = new Stock(stockName);
    }

    public Stock getStock()
    {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return price;
    }

    public void setStatus(StockActionStatus s) {
        status = s;
    }

    public UUID getUUID() {return uuid;}

    public StockActionStatus getStatus() { return status; }

    private void setUUID(UUID id) {uuid = id;}
}
