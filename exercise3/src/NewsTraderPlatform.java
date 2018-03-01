public class NewsTraderPlatform {
    final static int NB_ACYCLIC_TRADERS = 100;
    final static int NB_CYCLIC_TRADERS = 100;

    public static void main(String[] args) throws Exception {
        String clientOptions[] = new String[0];
        for(int i = 0; i < NB_CYCLIC_TRADERS; i++) {
            new MQClient(new MQTrader(TraderType.CYCLIC), clientOptions).start();
        }

        for(int i = 0; i < NB_ACYCLIC_TRADERS; i++) {
            new MQClient(new MQTrader(TraderType.ACYCLIC), clientOptions).start();
        }
    }
}
