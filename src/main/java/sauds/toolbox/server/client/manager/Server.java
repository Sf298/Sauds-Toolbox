
package sauds.toolbox.server.client.manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Creates a server and handles incoming connections with ClientManagers.
 * @author Saud Fatayerji
 */
public class Server implements Iterable {
	
	public static final int USER_ADDED = 0;
	public static final int USER_REMOVED = 1;
    
    private String name;
    private Thread serverThread;
    private ArrayList<UserManager> users = new ArrayList<>();
    
    /**
     * Creates a new Server object that does not monitor user accounts. Must
     * call start() to start the server.
     * @param name The name of this server.
     * @param port The port on which to host the server, or 0 to use a port
     * number that is automatically allocated.
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    public Server(String name, int port) throws IOException {
        this(name, port, null);
    }
    
    /**
     * Creates a new Server object that ensures only users with valid accounts
     * connect. Must call start() to start the server.
     * @param name The name of this server.
     * @param port The port on which to host the server, or 0 to use a port
     * number that is automatically allocated.
     * @param lci The login checker to use when validating new connections.
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    public Server(String name, int port, LoginCheckerInterface lci) throws IOException {
        this.name = name;
        
        System.out.println("starting...");
        ServerSocket ss = new ServerSocket(port);
        System.out.println("started");
        
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        System.out.println("waiting for connection...");
                        Socket s = ss.accept();
                        UserManager um = new UserManager(s, lci);
                        um.addDisconnectListener(new Runnable() {
                            @Override
                            public void run() {
                                users.remove(um);
                                runUserChangeListeners(USER_REMOVED, um);
                            }
                        });
                        users.add(um);
                        runUserChangeListeners(USER_ADDED, um);
                        System.out.println("connection "+um+" initiated");
                    } catch (IOException ex) {}
                }
            }
        });
    }

	public int userCount() {
		return users.size();
	}
	public UserManager getUserManager(int i) {
		return users.get(i);
	}

	@Override
	public Iterator<UserManager> iterator() {
		return users.listIterator();
	}
	
    /**
     * Gets the name of this server.
     * @return The name of this server.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Starts the server.
     */
    public void start() {
        serverThread.start();
    }
    
    
    private HashSet<UserChangeListener> userChangeListeners = new HashSet<>();
    
    /**
     * Adds a user change listener to the Server. The listener is called
     * whenever there is a change in the connected users.
     * @param l The listener to add.
     */
    public void addUserChangeListener(UserChangeListener l) {
        userChangeListeners.add(l);
    }
    
    /**
     * Removes a user change listener from the Server.
     * @param l The listener to remove.
     */
    public void removeUserChangeListener(UserChangeListener l) {
        userChangeListeners.remove(l);
    }
    
    /**
     * Runs all user change listeners associated with this Server.
     */
    private void runUserChangeListeners(int action, UserManager um) {
        for(UserChangeListener l : userChangeListeners) {
            l.onChange(action, um);
        }
    }
    
}
