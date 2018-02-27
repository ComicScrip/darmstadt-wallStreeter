/**
 * Represents a stock buy offer
 */

public class StockBid extends StockAction {
    private float price; // the maximum amount of money the buyer is ready to pay for the stock

    public StockBid(Stock stock) {
    }
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}