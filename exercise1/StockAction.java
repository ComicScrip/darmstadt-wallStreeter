public abstract class StockAction implements java.io.Serializable {
    private Stock stock;
    private String uuid; // a unique identifier for the stock
    private StockActionStatus status;


    public  StockAction(Stock stock) {
        this.stock = stock;
        uuid = String.valueOf(Math.random()* 1000);
        status = StockActionStatus.PENDING;
    }
}
