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

public class TCPClient {
    boolean started = false;
    String line = "";
    static UserInterface user = new UserInterface();
    Socket socket;
    BufferedReader fromServer;
    DataOutputStream toServer;

    public TCPClient(String serverIP, String serverPort) throws Exception{
        initConnection(serverIP, serverPort);
    }

    private void initConnection(String serverIP, String serverPort) throws Exception{
        socket = new Socket(serverIP, Integer.parseInt(serverPort));
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        started = true;
    }

    public void stop() throws Exception{
        if (started) {
            socket.close();
            toServer.close();
            fromServer.close();
            started = false;
        }
    }

    public void sendRequest(SocketMessage request) throws IOException {
        user.output("Sending request : \n" + request.toString());
        toServer.writeBytes(request.getEncapsulated());
    }

    public void receiveResponse() throws IOException {
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
