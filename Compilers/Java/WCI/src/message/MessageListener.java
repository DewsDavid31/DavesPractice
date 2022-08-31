package message;

public interface MessageListener 
{

	/**
	 * Called to receive a message sent by the messageProducer.
	 * @param message the message that was sent.
	 */
	public void messageReceived(Message message);
}
