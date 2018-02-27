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
  static ObjectOutputStream toServer;
  static UserInterface user = new UserInterface();

  public static void main(String[] args) throws Exception {
    socket = new Socket("localhost", 9999);
    toServer = new ObjectOutputStream(socket.getOutputStream());
    fromServer = new BufferedReader(     // Datastream TO Server
      new InputStreamReader(socket.getInputStream()));

  //  while (sendRequest()) {              // Send requests while connected
      receiveResponse();                 // Process server's answer
  //  }
    socket.close();
    toServer.close();
    fromServer.close();
  }

  public void sell(float price, StockName name) throws IOException{
    StockAction sa = new StockAsk(new Stock(name), price);
    toServer.writeObject(sa);
  }

  /*
  private static boolean sendRequest(String data) throws IOException {
    toServer.writeBytes((line = user.input()) + '\n');
    if (line.equals(".")) {              // Does the user want to end the session?
      holdTheLine = false;
    }
    return holdTheLine;
  }
*/
  private static void receiveResponse() throws IOException {
    //user.output("Server answers: " +
      //new String(fromServer.readLine()) + '\n');
    ObjectInputStream response = new ObjectInputStream((OutputStream)fromServer.readLine());
  }
}
