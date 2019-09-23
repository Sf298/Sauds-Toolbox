
package sauds.toolbox.server.client.manager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the sending and receiving of Msg objects from the server.
 * @author Saud Fatayerji
 */
public final class ClientManager {
    
    public static final int STATUS_DISCONNECTED = 0;
    public static final int STATUS_CONNECTED = 1;
    public static final int STATUS_LOGGED_IN = 2;
    
    public static final int OPERATION_SUCCESSFUL = 0;
    public static final int ERROR_NOT_CONNECTED = 1;
    public static final int ERROR_BAD_LOGIN = 2;
	
	public static boolean DEBUG = false;
    
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Thread recievingThread = null;
    private int connStatus = STATUS_DISCONNECTED;
    private boolean loginReqired;
    
    /**
     * Creates a new ClientManager Object
     */
    public ClientManager() {
        addConnectionChangeListener(new Runnable() {
            @Override
            public void run() {
                if(isConnected()) {
                    startRecievingThread();
                } else {
                    stopRecievingThread();
                }
            }
        });
    }
    
    /**
     * Initiates a connection between the ClientManager and the given Server.
     * @param url The URL of the server to connect to.
     * @param port The port number of the server to connect to.
     * @throws IOException If an I/O error occurs when creating the connection.
     */
    public void connect(String url, int port) throws IOException {
        try {
            Socket s = new Socket(url, port);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(s.getInputStream());
            s.setSoTimeout(200);
			
			Msg reply = sendAndWaitReply(new Msg(Msg.IS_LOGIN_REQ));
			loginReqired = reply.getAction() == Msg.LOGIN_REQ_TRUE;

            connStatus = STATUS_CONNECTED;
            runConnectionChangeListeners();
        } catch(IOException ex) {
            connStatus = STATUS_DISCONNECTED;
            runConnectionChangeListeners();
            throw ex;
        }
    }
    
    /**
     * Performs the login routine with the connected server.
     * @param username The username to use.
     * @param password The password to use.
     * @return The result of the operation (ERROR_NOT_CONNECTED,
     * ERROR_BAD_LOGIN, or OPERATION_SUCCESSFUL)
     */
    public int login(String username, String password) {
        if(!isConnected())
            return ERROR_NOT_CONNECTED;
        Msg m = sendAndWaitReply(new Msg(Msg.LOGIN, username, password));
        if(m.getAction() == Msg.LOGIN_OK) {
            connStatus = STATUS_LOGGED_IN;
            runConnectionChangeListeners();
            return OPERATION_SUCCESSFUL;
        }
        return ERROR_BAD_LOGIN;
    }
    
    /**
     * Check if the ClientManager has a connection with the server.
     * @return True if the ClientManager has a connection with the server.
     */
    public boolean isConnected() {
        return connStatus == STATUS_LOGGED_IN
                || connStatus == STATUS_CONNECTED;
    }
    
    /**
     * Check if the ClientManager is logged into the connected server.
     * @return If the ClientManager is logged in. Also returns true if there is no
     * need to login.
     */
    public boolean isLoggedIn() {
        return connStatus == STATUS_LOGGED_IN;
    }
    
    /**
     * Sends a message to the connected server.
     * @param m The Msg object to send.
     */
    public void sendMessage(Msg m) {
		if(loginReqired && !isLoggedIn() && !Msg.isPreLogin(m)) throw new RuntimeException("Not logged in!!");
        if(DEBUG) System.out.println("****\nSending: "+m.toString()+"\n****");
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection();
        }
    }
    
    /**
     * Waits for a response from the server.
     * @return The received Msg object.
     */
    private synchronized Msg waitMessage() {
		if(loginReqired && !isLoggedIn()) throw new RuntimeException("Not logged in!!");
        try {
            Object o = null;
            while(o==null && !Thread.currentThread().isInterrupted()) {
                try {
                    // socket was given a timeout to make this thread interruptable
                    o = ois.readObject();
                } catch (SocketTimeoutException ex) {}
            }
            return (Msg) o;
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection();
        } catch(ClassNotFoundException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Sends a message and awaits a response. Does not trigger message listeners.
     * @param m The Msg object to send.
     * @return The received Msg object.
     */
    public Msg sendAndWaitReply(Msg m) {
        stopRecievingThread();
        sendMessage(m);
        Msg out = waitMessage();
        startRecievingThread();
        return out;
    }
    
    /**
     * Starts a thread that waits for and processes incoming messages.
     */
    protected void startRecievingThread() {
        if(recievingThread!=null && recievingThread.isAlive())
            recievingThread.interrupt();
        
        recievingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Msg m = waitMessage();
                    if(Thread.currentThread().isInterrupted())
                        break;
                    if(m!=null) runMessageListeners(m);
                }
            }
        });
        
        recievingThread.setName("recievingThread");
        recievingThread.start();
    }
    
    /**
     * Stops the thread that waits for and processes incoming messages.
     */
    protected void stopRecievingThread() {
        if(recievingThread!=null) {
            recievingThread.interrupt();
            try {
                recievingThread.join();
            } catch (InterruptedException ex) {}
        }
        recievingThread = null;
    }
    
    /**
     * Closes the connection with the server.
     */
    public void closeConnection() {
        try {
            if(ois!=null) ois.close();
            if(oos!=null) oos.close();
            connStatus = 0;
            runConnectionChangeListeners();
        } catch(IOException e) {}
    }
    
    
    private HashSet<Runnable> connectionChangeListeners = new HashSet<>();
    
    /**
     * Adds a connection change listener to the ClientManager. The listener is
     * called whenever there is a change in the connection status.
     * @param r The listener to add.
     */
    public void addConnectionChangeListener(Runnable r) {
        connectionChangeListeners.add(r);
    }
    
    /**
     * Removes a connection change listener from the ClientManager.
     * @param r The listener to remove.
     */
    public void removeConnectionChangeListener(Runnable r) {
        connectionChangeListeners.remove(r);
    }
    
    /**
     * Runs all connection change listeners associated with this ClientManager.
     */
    private void runConnectionChangeListeners() {
        for(Runnable r : connectionChangeListeners) {
            r.run();
        }
    }
    
    
    
    private HashSet<MessageListener> messageListeners = new HashSet<>();
    private HashMap<Integer, HashSet<MessageListener>> filteredMessageListeners = new HashMap<>();
    
    /**
     * Adds a message listener to the ClientManager. The listener is
     * called whenever any Msg object is received from the server.
     * @param ml The listener to add.
     */
    public void addMessageListener(MessageListener ml) {
        messageListeners.add(ml);
    }
    
    /**
     * Adds a message listener to the ClientManager. The listener is called
     * whenever a new Msg object with the specified action is received from the
     * server.
     * @param action The action to filter for.
     * @param ml The listener to add.
     */
    public void addMessageListener(int action, MessageListener ml) {
        if(!filteredMessageListeners.containsKey(action))
            filteredMessageListeners.put(action, new HashSet<>());
        filteredMessageListeners.get(action).add(ml);
    }
    
    /**
     * Removes a message listener with an unspecified action from the
     * ClientManager.
     * @param ml The listener to remove.
     */
    public void removeMessageListener(MessageListener ml) {
        messageListeners.remove(ml);
    }
    
    /**
     * Removes a message listener with the specified action from the
     * ClientManager.
     * @param action The action associated with the filter.
     * @param ml The listener to remove.
     */
    public void removeMessageListener(int action, MessageListener ml) {
        if(filteredMessageListeners.containsKey(action))
            filteredMessageListeners.get(action).remove(ml);
    }
    
    /**
     * Runs all message listeners associated with this ClientManager.
     * @param m The received message.
     */
    private void runMessageListeners(Msg m) {
        if(filteredMessageListeners.containsKey(m.getAction()))
            for(MessageListener ml : filteredMessageListeners.get(m.getAction())) 
                ml.onReceived(m);
        for(MessageListener ml : messageListeners) {
            ml.onReceived(m);
        }
    }
    
}
