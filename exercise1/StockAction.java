public abstract class StockAction {
    private Stock stock;
    private String uuid; // a unique identifier for the stock
    private double price;
    private StockActionStatus status = StockActionStatus.PENDING;

    public boolean isComplete() {
        return (stock.getName() != StockName.NONE && price > 0);
    }

    public String toServerString()
    {
        return "stockName=" + stock.getName().name() + "\nprice=" + price + "\naction=" + this.getClass() + "\nstatus=\nid=\n";
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

    public void setStatus(StockActionStatus status)
    {
        this.status = status;
    }

    public StockActionStatus getStatus()
    {
        return status;
    }
}
