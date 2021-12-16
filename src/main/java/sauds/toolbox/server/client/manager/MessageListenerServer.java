
package sauds.toolbox.server.client.manager;

/**
 * This interface is used for message received listeners.
 * @author Saud Fatayerji
 */
public interface MessageListenerServer {
    
    /**
     * Called when a message is received.
	 * @param um the um the message was recieved from
     * @param m the received message.
     */
    public void onReceived(UserManager um, Msg m);
    
}
