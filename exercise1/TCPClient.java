/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;

public class TCPClient {

  static String line;
  static Socket socket;
  static BufferedReader fromServer;
  static DataOutputStream toServer;
  static UserInterface user = new UserInterface();

  static StockAction stockAction;

  public static void main(String[] args) throws Exception {
    socket = new Socket("localhost", 9999);
    toServer = new DataOutputStream(     // Datastream FROM Server
      socket.getOutputStream());
    fromServer = new BufferedReader(     // Datastream TO Server
      new InputStreamReader(socket.getInputStream()));
    while (sendStockAction()) { // Send requests while connected
        if(stockAction != null)
        {
            if(stockAction.isComplete())
            {
                receiveResponse();
            }
        }
        else if(line.equals("."))
        {
            receiveResponse();
        }
    }
    socket.close();
    toServer.close();
    fromServer.close();
  }

  private static boolean sendRequest() throws IOException {
    boolean holdTheLine = true;          // Connection exists
    user.output("Enter message for the Server, or end the session with . : ");
    toServer.writeBytes((line = user.input()) + '\n');
    if (line.equals(".")) {              // Does the user want to end the session?
      holdTheLine = false;
    }
    return holdTheLine;
  }

  private static boolean sendStockAction() throws IOException {
      boolean holdTheLine = true;

      if(stockAction == null) // no current stock action
      {
          user.output("Enter 1 (Ask),2 (Bid), or end the session with . : ");
          line = user.input();
          if (line.equals(".")) { // Does the user want to end the session?
              toServer.writeBytes(line + '\n');
              holdTheLine = false;
          }
          else if (line.equals("1") || line.equals("2")) // Ask or Bid?
          {
              if(line.equals("1"))
              {
                  stockAction = new StockAsk();
              }
              else if(line.equals("2"))
              {
                  stockAction = new StockBid();
              }
              stockAction.setStock(StockName.NONE);
          }
      }
      else if(stockAction.getStock().getName() == StockName.NONE)
      {
          user.output("Following a list of the companies featured : ");
          for (StockName name : StockName.values())
          {
              if(name == StockName.NONE)
              {
                  continue;
              }
              user.output((name.ordinal()) + "." + name.toString() + " / ");
          }
          user.output("\nSelect a number, or end session with . : ");
          line = user.input();

          if (line.equals(".")) { // Does the user want to end the session?
              toServer.writeBytes(line + '\n');
              holdTheLine = false;
          }
          else
          {
              if(line.matches("\\d") && (Integer.parseInt(line) < StockName.values().length))
              {
                  if((0 < Integer.parseInt(line)) && (Integer.parseInt(line) < StockName.values().length))
                  {
                      int ordinal = Integer.parseInt(line);
                      stockAction.setStock(StockName.values()[ordinal]);
                  }
              }
          }
      }
      else
      {
          user.output("Provide a price (float only), or end session with . : ");
          line = user.input();
          if (line.equals(".")) { // Does the user want to end the session?
              toServer.writeBytes(line + '\n');
              holdTheLine = false;
          }
          else
          {
              if(line.matches("^([+-]?\\d*\\.?\\d*)$"))
              {
                  double price = Double.parseDouble(line);
                  stockAction.setPrice(price);
                  toServer.writeBytes(stockAction.toServerString() + '\n');
                  holdTheLine = false;
              }
          }
      }


      return holdTheLine;
  }

  private static void receiveResponse() throws IOException {
    user.output("Server answers: " +
      new String(fromServer.readLine()) + '\n');
  }
}
