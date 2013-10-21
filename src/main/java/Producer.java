import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class Producer
{
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public Producer(ConnectionFactory factory, String queueName) throws JMSException
    {
        System.out.println("Queue name is [" + queueName + "]");

        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        messageProducer = session.createProducer(destination);
    }

    public void send(String text) throws JMSException
    {
        System.out.println("Sending message:\n" + text);
        Message message = session.createTextMessage(text);
        messageProducer.send(message);
    }

    public void close() throws JMSException
    {
        if (connection != null)
        {
            connection.close();
        }
    }

}
