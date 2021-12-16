
package sauds.toolbox.server.client.manager;

/**
 * This interface is used for message received listeners.
 * @author Saud Fatayerji
 */
public interface MessageListener {
    
    /**
     * Called when a message is received.
     * @param m the received message.
     */
    public void onReceived(Msg m);
    
}
