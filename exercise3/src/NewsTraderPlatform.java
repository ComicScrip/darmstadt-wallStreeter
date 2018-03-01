public class NewsTraderPlatform {
    static int NB_ACYCLIC_TRADERS = 3;
    static int NB_CYCLIC_TRADERS = 3;

    public static void main(String[] args) throws Exception {
        if(args.length >= 1) NB_CYCLIC_TRADERS = Integer.parseInt(args[0]);
        if(args.length >= 2) NB_ACYCLIC_TRADERS = Integer.parseInt(args[1]);

        String clientOptions[] = new String[0];

        for(int i = 0; i < NB_CYCLIC_TRADERS; i++) {
            new MQClient(new MQTrader(TraderType.CYCLIC), clientOptions).start();
        }

        for(int i = 0; i < NB_ACYCLIC_TRADERS; i++) {
            new MQClient(new MQTrader(TraderType.ACYCLIC), clientOptions).start();
        }
    }
}
