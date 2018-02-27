import java.io.IOException;

public class Trader {
    static UserInterface user = new UserInterface();

    public static String getRequest() {
        // TODO: implement general menu
        return getStockActionRequest(getStockActionFormCLI());
    }

    public static String getStockActionRequest(StockAction sa){
        String toSend = null;
        if(sa instanceof StockAsk){
            toSend = "method=addSellActionToList\npayloadLength=%s\n\n%s";
        } else if (sa instanceof StockBid){
            toSend = "method=addBuyActionToList\npayloadLength=%s\n\n%s";
        }

        return toSend == null ? null : String.format(toSend, sa.toServerString().length(), sa.toServerString());
    }

    public static StockAction getStockActionFormCLI(){
        final String QUIT = "quit";
        String line = "";
        StockAction stockAction = null;

        try {
            while(true) {
                user.output("Enter 1 (Ask), 2 (Bid), or end the session with 'quit': ");
                line = user.input(); if(line.equals(QUIT)) return null;
                if(line.equals("1"))
                {
                    stockAction = new StockAsk();
                    break;
                }
                else if(line.equals("2"))
                {
                    stockAction = new StockBid();
                    break;
                }
            }

            while(true) {
                user.output("Following a list of the companies featured : ");
                for (StockName name : StockName.values())
                {
                    user.output((name.ordinal()) + "." + name.toString() + " / ");
                }
                line = user.input(); if(line.equals(QUIT)) return null;
                if(line.matches("\\d") && (Integer.parseInt(line) < StockName.values().length))
                {
                    if((0 <= Integer.parseInt(line)) && (Integer.parseInt(line) < StockName.values().length))
                    {
                        int ordinal = Integer.parseInt(line);
                        stockAction.setStock(StockName.values()[ordinal]);
                        break;
                    }
                }
            }

            while(true) {
                user.output("Provide a price (float only) : ");
                line = user.input(); if(line.equals(QUIT)) return null;
                if(line.matches("^([+-]?\\d*\\.?\\d*)$"))
                {
                    double price = Double.parseDouble(line);
                    stockAction.setPrice(price);
                    break;
                }
            }
        } catch (IOException e){
            //TODO: handle exception
        }

        return stockAction;
    }
}
