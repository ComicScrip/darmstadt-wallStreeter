/**
 * Represents a stock sell offer
 */

public class StockAsk extends StockAction {
    private float price; // the minimum amount of money the seller is ready to accept in exchange for his stock

    public StockAsk(Stock stock, float price) {
        super(stock);
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
