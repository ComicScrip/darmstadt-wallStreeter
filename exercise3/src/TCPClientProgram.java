/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClientProgram {
    static boolean started = false;
    static String line = "";
    static UserInterface user = new UserInterface();
    static Socket socket;
    static BufferedReader fromServer;
    static DataOutputStream toServer;

    static void start() throws Exception{
        if(!started) {
            user.output("Please provide IP:Port of the server, or hit enter to leave the defaults : ");
            line = user.input();
            String serverIP = "localhost";
            String serverPort = "9999";
            String lineParts[] = line.split(":");

            if(lineParts.length == 2) {
                serverIP = lineParts[0];
                serverPort =  lineParts[1];
            }

            socket = new Socket(serverIP, Integer.parseInt(serverPort));
            toServer = new DataOutputStream(socket.getOutputStream());
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            started = true;
        }
    }

    static void stop() throws Exception{
        if (started) {
            socket.close();
            toServer.close();
            fromServer.close();
            started = false;
        }
    }

    public static void main(String[] args) throws Exception {
        start();
        while(true) {
            SocketMessage toSend = Trader.getRequest();
            if(toSend == null) break;
            sendRequest(toSend);
            receiveResponse();
        }
        stop();
    }

    public static void sendRequest(SocketMessage request) throws IOException {
        user.output("Sending request : \n" + request.toString());
        toServer.writeBytes(request.getEncapsulated());
    }

    public static void receiveResponse() throws IOException {
        StringBuilder response = new StringBuilder();
        String firstLine = fromServer.readLine();
        int contentLength = Integer.parseInt(firstLine);
        for(int i = 0; i<contentLength; i++){
            response.append((char)fromServer.read());
        }

        SocketMessage received = new SocketMessage(response.toString());
        user.output("\nServer answers: \n--------------");
        user.output(received.toString() + "\n --------------\n");
    }
}
