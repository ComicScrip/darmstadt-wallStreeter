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
    static String line = "";
    static UserInterface user = new UserInterface();
  static Socket socket;
  static BufferedReader fromServer;
  static DataOutputStream toServer;
  static Trader trader;

  public static void main(String[] args) throws Exception {
    socket = new Socket("localhost", 9999);
    toServer = new DataOutputStream(socket.getOutputStream());
    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    while(true) {
        String toSend = Trader.getRequest();
        if(toSend == null) break;
        sendRequest(toSend);
        receiveResponse();
    }

    socket.close();
    toServer.close();
    fromServer.close();
  }

  private static void sendRequest(String request) throws IOException {
    user.output("Sending request : " + request);
    toServer.writeBytes((request + '\n'));
  }

  private static void receiveResponse() throws IOException {
    user.output("Server answers: " + fromServer.readLine() + '\n');
  }
}
