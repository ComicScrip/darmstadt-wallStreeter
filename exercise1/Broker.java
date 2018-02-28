import java.util.ArrayList;
import java.util.UUID;

public class Broker {
    private ArrayList<StockBid> buyActionList;
    private ArrayList<StockAsk> sellActionList;
    private FileLogger fileLogger;

    public Broker(){
        buyActionList = new ArrayList<StockBid>();
        sellActionList = new ArrayList<StockAsk>();
        fileLogger = new FileLogger("logFile.csv");
    }

    public ArrayList<StockBid> getBuyActionList() {
        return buyActionList;
    }

    public ArrayList<StockAsk> getSellActionList() {
        return sellActionList;
    }

    public void setBuyActionList(ArrayList<StockBid> buyActionList) {
        this.buyActionList = buyActionList;
    }

    public void setSellActionList(ArrayList<StockAsk> sellActionList) {
        this.sellActionList = sellActionList;
    }

    public void addSellActionToList(StockAsk action){
        action.setStatus(StockActionStatus.PENDING);
        sellActionList.add(action);
        //establishTrade();
        establishTrade(action.getStock().getName());
    }

    public void addBuyActionToList(StockBid action){
        action.setStatus(StockActionStatus.PENDING);
        buyActionList.add(action);
        //establishTrade();
        establishTrade(action.getStock().getName());
    }

    public StockAsk getSellActionByUuid(UUID uuid)
    {
        for(StockAsk action : sellActionList)
        {
            if(action.getUUID().equals(uuid))
            {
                return action;
            }
        }
        return null;
    }

    public StockBid getBuyActionByUuid(UUID uuid)
    {
        for(StockBid action : buyActionList)
        {
            if(action.getUUID().equals(uuid))
            {
                return action;
            }
        }
        return null;
    }

    public StockBid getHighestBid(){
        if(buyActionList.size() == 0) return null;
        StockBid maxBid = buyActionList.get(0);
        double max = maxBid.getPrice();

        for(int i = 0; i < buyActionList.size(); i++) {
            StockBid bid = buyActionList.get(i);
            if(bid.getPrice() > max)
            {
                max = bid.getPrice();
                maxBid = bid;
            }
        }
        return maxBid;
    }

    public StockBid getHighestBid(StockName stockName){
        //if(buyActionList.size() == 0) return null;
        ArrayList<StockBid> bids = getAllBidsOfStock(stockName);
        if(bids.size() == 0) return null;
        StockBid maxBid = bids.get(0);
        double max = maxBid.getPrice();

        for(StockBid bid : bids) {
            if(bid.getPrice() > max)
            {
                max = bid.getPrice();
                maxBid = bid;
            }
        }
        return maxBid;
    }

    public StockAsk getLowestAsk(){
        if(sellActionList.size() == 0) return null;
        StockAsk minAsk = sellActionList.get(0);
        if(minAsk == null) return null;

        double min = minAsk.getPrice();

        for(int i = 0; i < sellActionList.size(); i++) {
            StockAsk ask = sellActionList.get(i);
            if(ask.getPrice() > min)
            {
                min = ask.getPrice();
                minAsk = ask;
            }
        }
        return minAsk;
    }

    public StockAsk getLowestAsk(StockName stockName){
        //if(buyActionList.size() == 0) return null;
        ArrayList<StockAsk> asks = getAllAsksOfStock(stockName);
        if(asks.size() == 0) return null;
        StockAsk minAsk = asks.get(0);
        double min = minAsk.getPrice();

        for(StockAsk ask : asks) {
            if(ask.getPrice() < min)
            {
                min = ask.getPrice();
                minAsk = ask;
            }
        }
        return minAsk;
    }

    public ArrayList<StockBid> getAllBidsOfStock(StockName stockName)
    {
        ArrayList<StockBid> stockBids = new ArrayList<StockBid>();

        if(buyActionList.size() > 0)
        {
            for(StockBid bid : buyActionList)
            {
                if(bid.getStock().getName().equals(stockName))
                {
                    stockBids.add(bid);
                }
            }
        }

        return stockBids;
    }

    public ArrayList<StockAsk> getAllAsksOfStock(StockName stockName)
    {
        ArrayList<StockAsk> stockAsks = new ArrayList<StockAsk>();

        if(sellActionList.size() > 0)
        {
            for(StockAsk ask : sellActionList)
            {
                if(ask.getStock().getName().equals(stockName))
                {
                    stockAsks.add(ask);
                }
            }
        }

        return stockAsks;
    }

    public void establishTrade()
    {
        StockBid highestBid = getHighestBid();
        StockAsk lowestAsk = getLowestAsk();

        if(highestBid != null && lowestAsk != null) {

            if((highestBid.getStatus() == StockActionStatus.OK) || (lowestAsk.getStatus() == StockActionStatus.OK))
            {
                return;
            }

            if(highestBid.getPrice() >= lowestAsk.getPrice())
            {
                highestBid.setStatus(StockActionStatus.OK);
                lowestAsk.setStatus(StockActionStatus.OK);

                //Adds the transaction details to the logfile.csv
                fileLogger.writeToFile(lowestAsk.getStock().getName().name(),String.valueOf(highestBid.getPrice()));
            }
        }
    }

    public void establishTrade(StockName stockName)
    {
        StockBid highestBid = getHighestBid(stockName);
        StockAsk lowestAsk = getLowestAsk(stockName);

        if(highestBid != null && lowestAsk != null) {

            if((highestBid.getStatus() == StockActionStatus.OK) || (lowestAsk.getStatus() == StockActionStatus.OK))
            {
                return;
            }

            if(highestBid.getPrice() >= lowestAsk.getPrice())
            {
                highestBid.setStatus(StockActionStatus.OK);
                lowestAsk.setStatus(StockActionStatus.OK);

                //Adds the transaction details to the logfile.csv
                fileLogger.writeToFile(lowestAsk.getStock().getName().name(),String.valueOf(highestBid.getPrice()));
            }
        }
    }
}
