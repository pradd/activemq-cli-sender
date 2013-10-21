import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

public class App
{
    public static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws Exception
    {
        ConnectionFactory factory;
        String queue;
        String message;

        switch (args.length)
        {
        case 2:
            System.out.println("Connecting to default URL [" + DEFAULT_BROKER_URL + "]");
            factory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
            queue = args[0];
            message = args[1];
            break;
        case 3:
            System.out.println("Connecting to URL [" + args[0] + "]");
            factory = new ActiveMQConnectionFactory(args[0]);
            queue = args[1];
            message = args[2];
            break;
        case 5:
            System.out.println("Connecting to [" + args[0] + "] as user [" + args[1] + "] with password [" + args[2]
                    + "]");
            factory = new ActiveMQConnectionFactory(args[0], args[1], args[2]);
            queue = args[3];
            message = args[4];
            break;
        default:
            System.err.println("Unexpected number of parameters: " + args.length);
            for (int i = 0; i < args.length; i++)
            {
                System.err.println(i + ": " + args[i]);
            }
            System.err.println("Usage:   activemq-cli-sender <url> [user pass] <queue> <message>");
            System.err
                    .println("Example: activemq-cli-sender tcp://localhost:61616 admin pa55w0rd JMS/QUEUE.NAME \"message text\"");
            return;
        }

        Producer producer = new Producer(factory, queue);
        producer.send(message);
        producer.close();

        System.out.println("Exiting.");
    }
}
