public abstract class StockAction {
    private Stock stock;
    private String uuid; // a unique identifier for the stock
    private double price;

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
}
