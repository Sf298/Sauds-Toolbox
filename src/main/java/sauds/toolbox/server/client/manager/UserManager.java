
package sauds.toolbox.server.client.manager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UserManager class handles interactions with connected clients. Usually
 * created by the Server class.
 * @author Saud Fatayerji
 */
public final class UserManager {
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String userId = null;
    private LoginCheckerInterface loginChecker;
    private Thread recievingThread = null;
    
    /**
     * Creates a UserManager object and starts the thread.
     * @param s The open socket connected with the ClientManager.
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    public UserManager(Socket s) throws IOException {
        this(s, null);
    }
    
    /**
     * Creates a UserManager object and starts the thread.
     * @param s The open socket connected with the ClientManager.
     * @param lci The login checker to use when validating new connections.
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    public UserManager(Socket s, LoginCheckerInterface lci) throws IOException {
        ois = new ObjectInputStream(s.getInputStream());
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.flush();
        loginChecker = lci;
        userId = null;
        addMessageListener(Msg.LOGIN, new MessageListenerServer() {
            @Override
            public void onReceived(UserManager um, Msg m) {
                String uname = (String) m.getArg(0);
                String pword = (String) m.getArg(1);
                if(loginChecker==null || loginChecker.isValid(uname, pword)) {
                    sendMessage(new Msg(Msg.LOGIN_OK));
                    userId = uname;
                } else {
                    sendMessage(new Msg(Msg.LOGIN_FAIL));
                    userId = null;
                }
            }
        });
        addMessageListener(Msg.LOGOUT, new MessageListenerServer() {
            @Override
            public void onReceived(UserManager um, Msg m) {
                stopRecievingThread();
                closeConnection();
            }
        });
        addMessageListener(Msg.IS_LOGIN_REQ, new MessageListenerServer() {
            @Override
            public void onReceived(UserManager um, Msg m) {
                sendMessage(new Msg(
					(loginChecker==null) ? Msg.LOGIN_REQ_FALSE : Msg.LOGIN_REQ_TRUE
				));
            }
        });
        /*addMessageListener(new MessageListenerServer() {
            @Override
            public void onReceived(UserManager um, Msg m) {
                System.out.println(m);
            }
        });*/
        startRecievingThread();
    }
    
    /**
     * Gets the user ID of the connected client.
     * @return The user ID of the connected client.
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Checks if the Client is logged in. Also returns true if there is no need
     * to login.
     * @return 
     */
    public boolean isLoggedIn() {
        return userId!=null || loginChecker==null;
    }
    
    /**
     * Starts a thread that waits for and processes incoming messages.
     */
    public void startRecievingThread() {
        if(recievingThread!=null && recievingThread.isAlive())
            recievingThread.interrupt();
        
        recievingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    Msg m = waitMessage();
                    if(m!=null && (isLoggedIn() || m.getAction()==Msg.LOGIN || m.getAction()==Msg.IS_LOGIN_REQ))
                        runMessageListeners(m);
					else 
						sendMessage(new Msg(Msg.LOGIN_FAIL));
                }
            }
        });
        
        recievingThread.start();
    }
    
    /**
     * Stops the thread that waits for and processes incoming messages.
     */
    public void stopRecievingThread() {
        if(recievingThread!=null)
            recievingThread.interrupt();
        recievingThread = null;
    }
    
	Lock sendLock = new ReentrantLock();
    /**
     * Sends a message to the connected client.
     * @param m The Msg object to send.
     */
    public void sendMessage(Msg m) {
		sendLock.lock();
        System.out.println("****\nSending: "+m.toString()+"\n****");
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection();
        } finally {
			sendLock.unlock();
		}
    }
    
	Lock recieveLock = new ReentrantLock();
    /**
     * Waits for a response from the client.
     * @return The received Msg object.
     */
    private Msg waitMessage() {
		recieveLock.lock();
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
        } finally {
			recieveLock.unlock();
		}
        return null;
    }
    
    /**
     * Closes the connection with the client.
     */
    public void closeConnection() {
        try {
            if(ois!=null) ois.close();
            if(oos!=null) oos.close();
            userId = null;
            stopRecievingThread();
            runDisconnectListeners();
        } catch(IOException e) {}
    }
    
    private HashSet<MessageListenerServer> messageListeners = new HashSet<>();
    private HashMap<Integer, HashSet<MessageListenerServer>> filteredMessageListeners = new HashMap<>();
    
    /**
     * Adds a message listener to the UserManager. The listener is
     * called whenever any Msg object is received from the client.
     * @param ml The listener to add.
     */
    public void addMessageListener(MessageListenerServer ml) {
        messageListeners.add(ml);
    }
    
    /**
     * Adds a message listener to the UserManager. The listener is called
     * whenever a new Msg object with the specified action is received from the
     * client.
     * @param action The action to filter for.
     * @param ml The listener to add.
     */
    public void addMessageListener(int action, MessageListenerServer ml) {
        if(!filteredMessageListeners.containsKey(action))
            filteredMessageListeners.put(action, new HashSet<>());
        filteredMessageListeners.get(action).add(ml);
    }
    
    /**
     * Removes a message listener with an unspecified action from the
     * UserManager.
     * @param ml The listener to remove.
     */
    public void removeMessageListener(MessageListenerServer ml) {
        messageListeners.remove(ml);
    }
    
    /**
     * Removes a message listener with the specified action from the
     * UserManager.
     * @param action The action associated with the filter.
     * @param ml The listener to remove.
     */
    public void removeMessageListener(int action, MessageListenerServer ml) {
        if(filteredMessageListeners.containsKey(action))
            filteredMessageListeners.get(action).remove(ml);
    }
    
    /**
     * Runs all message listeners associated with this UserManager.
     * @param m The received message.
     */
    private void runMessageListeners(Msg m) {
        if(filteredMessageListeners.containsKey(m.getAction()))
            for(MessageListenerServer ml : filteredMessageListeners.get(m.getAction())) 
                ml.onReceived(this, m);
        for(MessageListenerServer ml : messageListeners) {
            ml.onReceived(this, m);
        }
    }
    
    private HashSet<Runnable> disconnectChangeListeners = new HashSet<>();
    
    /**
     * Adds a disconnect listener to the UserManager. The listener is
     * called whenever the connection terminates.
     * @param r The listener to add.
     */
    public void addDisconnectListener(Runnable r) {
        disconnectChangeListeners.add(r);
    }
    
    /**
     * Removes a disconnect listener from the UserManager.
     * @param r The listener to remove.
     */
    public void removeDisconnectListener(Runnable r) {
        disconnectChangeListeners.remove(r);
    }
    
    /**
     * Runs all disconnect listeners associated with this UserManager.
     */
    private void runDisconnectListeners() {
        for(Runnable r : disconnectChangeListeners) {
            r.run();
        }
    }
    
}
