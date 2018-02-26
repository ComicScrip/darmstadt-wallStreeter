import java.util.ArrayList;

public class Broker {
    private ArrayList<StockBid> buyActionList;
    private ArrayList<StockAsk> sellActionList;

    public Broker(){
        buyActionList = new ArrayList<StockBid>();
        sellActionList = new ArrayList<StockAsk>();
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
        sellActionList.add(action);
    }

    public void addBuyActionToList(StockBid action){
        buyActionList.add(action);
    }

    public float getHighestBid(){
        float price ;
        float max = buyActionList.get(0).getPrice();
        for(int i = 0; i < buyActionList.size(); i++) {
            StockBid bid = buyActionList.get(i);
            if(bid.getPrice() > max) max = bid.getPrice();
        }
        return max;
    }

    public float getLowestAsk(){
        float min = sellActionList.get(0).getPrice();
        for(int i = 0; i < sellActionList.size(); i++) {
            StockAsk ask = sellActionList.get(i);
            if(ask.getPrice() > min) min = ask.getPrice();
        }
        return min;
    }

}
