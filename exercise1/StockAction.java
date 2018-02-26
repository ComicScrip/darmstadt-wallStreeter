public abstract class StockAction implements java.io.Serializable {
    private Stock stock;
    private String uuid; // a unique identifier for the stock
    private StockActionStatus status;
}
