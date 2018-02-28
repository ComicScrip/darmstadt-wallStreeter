import java.util.ArrayList;
import java.util.UUID;

public class Broker {
    private static ArrayList<StockBid> buyActionList = new ArrayList<StockBid>();
    private static ArrayList<StockAsk> sellActionList = new ArrayList<StockAsk>();
    private static FileLogger fileLogger = new FileLogger("logFile.csv");

    public Broker(){

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
        establishTrade();
    }

    public void addBuyActionToList(StockBid action){
        action.setStatus(StockActionStatus.PENDING);
        buyActionList.add(action);
        establishTrade();
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

    public String searchAction(String dMin, String dMax, String actionName) {
        System.out.println("OKEY !");
        return fileLogger.searchLogFile(dMin, dMax, actionName);
    }
}
