public class NewsTraderPlatform {
    final static int NB_ACYCLIC_TRADERS = 1;
    final static int NB_CYCLIC_TRADERS = 1;

    public static void main(String[] args) throws Exception {
        TCPClientProgram.start();
        String clientOptions[] = new String[0];
        for(int i = 0; i < NB_CYCLIC_TRADERS; i++) {
            new MQClient(new MQTrader(TraderType.CYCLIC), clientOptions).start();
        }

        for(int i = 0; i < NB_ACYCLIC_TRADERS; i++) {
            new MQClient(new MQTrader(TraderType.ACYCLIC), clientOptions).start();
        }

        TCPClientProgram.stop();
    }
}
