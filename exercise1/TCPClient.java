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
    user.output("Please provide IP:PORT of the server, or hit enter to leave the defaults : ");
    line = user.input();
    String ip = "localhost";
    String port = "9999";
    String lineParts[] = line.split(":");

    if(lineParts.length == 2) {
        ip = lineParts[0];
        port =  lineParts[1];
    }

    socket = new Socket(ip, Integer.parseInt(port));
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
    user.output("Sending request : \n--------------\n" + request + "\n --------------\n");
    toServer.writeBytes(('\t' + request + '\n'));
  }

  private static void receiveResponse() throws IOException {
    user.output("Server answers: \n--------------\n" + fromServer.readLine() + "\n --------------\n");
  }
}
