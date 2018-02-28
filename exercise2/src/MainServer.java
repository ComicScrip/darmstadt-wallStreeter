import java.io.File;
import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        //(new File("test")).createNewFile();
        //FileLogger f = new FileLogger("test.csv");
        Broker b = new Broker();
        Thread ss = new SocketServer(b);
        ss.start();
        Thread rpcServer = new XMLRPCServer(b);
        rpcServer.start();

        try {
            synchronized (ss) { ss.wait(); }
            synchronized (rpcServer) {rpcServer.wait(); }
            //rpcServer.wait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
