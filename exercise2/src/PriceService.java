import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class PriceService {
    static String line = "";
    static UserInterface user = new UserInterface();
    static Socket socket;
    static BufferedReader fromServer;
    static DataOutputStream toServer;
    static Trader trader;
    final static String QUIT = "quit";

    //Checks if the dates entered by the user are valid 
    public static boolean isValid(String text) {
        if (text == null || !text.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$")){
            System.out.println("text null or regex not matched, text : " + text + " match is : " +  text.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$"));
            return false;
        }

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        df.setLenient(false);
        try {
            df.parse(text);
            System.out.println("df : " +df);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String line = "";
        String dateBefore = "";
        String dateAfter = "";
        String stockSelected = "";
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        try {
            while(true) {
                user.output("What do you want to do ? (1) Ask price per name or (2) Ask data per stock name per price within a range of dates : ");
                line = user.input();
                if(line.equals(QUIT)) break;
                if(line.equals("1")){
                    if(line != null && !line.isEmpty()){
                        while(true) {
                            user.output("Following a list of the companies featured. Please enter the number of the stock name you want to search : ");
                            for (StockName name : StockName.values())
                            {
                                user.output((name.ordinal()) + "." + name.toString() + " / ");
                            }
                            line = user.input();
                            if(line.equals(QUIT)) break;
                            if(line.matches("\\d") && (Integer.parseInt(line) < StockName.values().length))
                            {
                                if((0 <= Integer.parseInt(line)) && (Integer.parseInt(line) < StockName.values().length))
                                {
                                    int ordinal = Integer.parseInt(line);
                                    stockSelected = StockName.values()[ordinal].name();
                                    System.out.println("StockSelected : " + stockSelected);
                                    Object[] params = new Object[]{new String(stockSelected)};

                                    String result =  (String) client.execute("Broker.searchActionPerName", params);
                                    System.out.println("The selected stock price is :  " + params[0] + ":\n" + result );

                                    break;
                                }
                            }
                        }
                    }
                }
                if(line.equals("2")){
                    if(line != null && !line.isEmpty()){
                        while(true) {
                            user.output("Following a list of the companies featured. Please enter the number of the stock name you want to search : ");
                            for (StockName name : StockName.values())
                            {
                                user.output((name.ordinal()) + "." + name.toString() + " / ");
                            }
                            line = user.input();
                            if(line.equals(QUIT)) break;
                            if(line != null && !line.isEmpty()){
                                if(line.matches("\\d") && (Integer.parseInt(line) < StockName.values().length))
                                {
                                    if((0 <= Integer.parseInt(line)) && (Integer.parseInt(line) < StockName.values().length))
                                    {
                                        int ordinal = Integer.parseInt(line);
                                        stockSelected = StockName.values()[ordinal].name();
                                        System.out.println("StockeSlected : " + stockSelected);
                                        user.output("Please enter the date range you want to search . ex : dateAfter:dateBefore in the following format dd/mm/yyyy:dd/mm/yyyy : ");
                                        line = user.input();
                                        if(line.equals(QUIT)) break;
                                        if(line != null && !line.isEmpty()){
                                            boolean dateFormatIsOk = false;
                                            while(!dateFormatIsOk){
                                                if(line.contains(":")){
                                                    dateAfter = line.split(":")[0];
                                                    dateBefore = line.split(":")[1];

                                                    if(isValid(dateAfter)&&isValid(dateBefore)){
                                                        dateFormatIsOk = true;
                                                    }else{
                                                        user.output("Please enter the date range you want to search . ex : dateAfter:dateBefore in the following format dd/mm/yyyy:dd/mm/yyyy : ");
                                                        line = user.input();
                                                    }
                                                }else{
                                                    user.output("Please enter the date range you want to search . ex : dateAfter:dateBefore in the following format dd/mm/yyyy:dd/mm/yyyy : ");
                                                    line = user.input();
                                                }

                                            }

                                            System.out.println("dateAfter : " + dateAfter + "\n" + " dateBefore : " + dateBefore);
                                            Object[] params = new Object[]{new String(dateAfter), new String(dateBefore), new String(stockSelected)};

                                            String result =  (String) client.execute("Broker.searchActionPerDates", params);
                                            System.out.println("Liste des transactions de " + params[2] + ":\n" + result );
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Exception : " + e);
        }
    }
}
