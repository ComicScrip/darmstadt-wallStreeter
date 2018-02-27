import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.*;

public class StockActionService extends Thread{
    Socket client;
    Broker broker;
    StockActionService(Socket client){this.client = client; broker = new Broker();}

    BufferedReader fromClient;
    DataOutputStream toClient;
    boolean verbunden = true;

    @Override
    public void run (){
        String line;
        System.out.println("Thread started: " + this); // Display Thread-ID

        try{
            fromClient = new BufferedReader              // Datastream FROM Client
                    (new InputStreamReader(client.getInputStream()));
            toClient = new DataOutputStream (client.getOutputStream()); // TO Client

            String methodToCall;
            while(verbunden){     // repeat as long as connection exists
                line = fromClient.readLine();              // Read Request
                System.out.println("Received: "+ line);
                if (line.equals("."))
                {
                    verbunden = false;   // Break Conneciton?
                }
                else if(line.contains("method"))
                {
                    methodToCall = getMethod(line);
                    executeMethod(methodToCall);
                }
                else
                {
                    toClient.writeBytes(line.toUpperCase()+'\n'); // Response
                }
            }

            fromClient.close();
            toClient.close();
            client.close(); // End

            System.out.println("Thread ended: "+this);
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private String getMethod(String line)
    {
        String[] splitLine = line.split("\n");
        for (String value : splitLine)
        {
            if(value.contains("method"))
            {
                String[] methodValue = value.split("=");
                return methodValue[1];
            }
        }

        return "";
    }

    private void executeMethod(String method)
    {
        try
        {
            switch(method)
            {
                case "addSellActionToList":
                    //broker.addSellActionToList();
                    toClient.writeBytes("Stock action sell request received. STATUS : " + '\n');
                    break;
                case "getSellActionByUuid":
                    //broker.getSellActionByUuid();
                    break;
                case "addBuyActionToList":
                    //broker.addBuyActionToList();
                    toClient.writeBytes("Action not allowed. Please restart the service" + '\n');
                    break;
                case "getBuyActionByUuid":
                    //broker.getBuyActionByUuid();
                    toClient.writeBytes("Stock action bid request received. STATUS : " + '\n');
                    break;
                default:
                    toClient.writeBytes("Action not allowed. Please restart the service" + '\n');
                    verbunden = false;
                    break;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}