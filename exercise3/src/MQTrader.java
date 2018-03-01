import java.util.concurrent.ThreadLocalRandom;

public class MQTrader extends Trader {
    TraderType type;

    public MQTrader(TraderType tt) {
        super ();
        type = tt;
    }

    public SocketMessage getRequest(NewsType nt, StockName sn) {
        StockAction sa = null;
        if(nt.equals(NewsType.GOOD)){
            if(type == TraderType.CYCLIC) {
                sa = new StockBid();
            } else if (type == TraderType.ACYCLIC) {
                sa = new StockAsk();
            }
        } else if (nt.equals(NewsType.BAD)) {
            if(type == TraderType.CYCLIC) {
                sa = new StockAsk();
            } else if (type == TraderType.ACYCLIC) {
                sa = new StockBid();
            }
        }

        if(sa != null) {
            sa.setPrice(ThreadLocalRandom.current().nextInt(1, 101));
            sa.setStock(sn);
        }

        return getStockActionRequest(sa);
    }
}
