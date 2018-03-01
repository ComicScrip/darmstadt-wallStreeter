/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import javax.jms.Message;


class MQClient extends Thread {
    MQTrader trader;
    String [] args;
    TCPClient sockClient;

    public MQClient(MQTrader t, String [] options) throws Exception{
        trader = t;
        args = options;
        sockClient = new TCPClient("localhost", "9999");
    }

    @Override
    public void run () {
        String user = env("ACTIVEMQ_USER", "admin");
        String password = env("ACTIVEMQ_PASSWORD", "password");
        String host = env("ACTIVEMQ_HOST", "localhost");
        int port = Integer.parseInt(env("ACTIVEMQ_PORT", "61616"));
        String destination = arg(args, 0, "event");

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);

        try {
            Connection connection = factory.createConnection(user, password);
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = new ActiveMQTopic(destination);

            MessageConsumer consumer = session.createConsumer(dest);
            long start = System.currentTimeMillis();
            long count = 1;
            System.out.println("Waiting for messages...");
            while(true) {
                Message msg = consumer.receive();
                if( msg instanceof  TextMessage ) {
                    String body = ((TextMessage) msg).getText();
                    if( "SHUTDOWN".equals(body)) {
                        long diff = System.currentTimeMillis() - start;
                        System.out.println(String.format("Received %d in %.2f seconds", count, (1.0*diff/1000.0)));
                        break;
                    } else {
                        if( count != msg.getIntProperty("id") ) {
                            System.out.println("mismatch: "+count+"!="+msg.getIntProperty("id"));
                        }
                        count = msg.getIntProperty("id");

                        if( count == 0 ) {
                            start = System.currentTimeMillis();
                        }
                        System.out.println(String.format("Received from journalist : " + body));

                        News n = News.parseFromPublisherMessage(body);
                        SocketMessage toSend = trader.getRequest(n.getType(), n.getAbout());
                        if(toSend == null) break;
                        sockClient.sendRequest(toSend);
                        sockClient.receiveResponse();

                        count ++;
                    }

                } else {
                    System.out.println("Unexpected message type: "+msg.getClass());
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String traderType = arg(args, 0, "cyclic");
        MQTrader t = new MQTrader(traderType.equals("acyclic") ? TraderType.ACYCLIC : TraderType.CYCLIC);
        MQClient c = new MQClient(t, args);
        c.run();
    }

    private String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if( rc== null )
            return defaultValue;
        return rc;
    }

    private static String arg(String []args, int index, String defaultValue) {
        if( index < args.length )
            return args[index];
        else
            return defaultValue;
    }
}