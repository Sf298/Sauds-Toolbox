
package sauds.toolbox.server.client.manager;

import java.io.Serializable;

/**
 * The Msg object is used as a wrapper for passing messages between the server
 * and client.
 * @author Saud Fatayerji
 */
public class Msg implements Serializable {
    
    public static final int ERR = -1;
    public static final int LOGIN = -2;
    public static final int LOGOUT = -3;
    public static final int LOGIN_OK = -4;
    public static final int LOGIN_FAIL = -5;
    public static final int IS_LOGIN_REQ = -6;
    public static final int LOGIN_REQ_TRUE = -7;
    public static final int LOGIN_REQ_FALSE = -8;
	
	public static boolean isPreLogin(int action) {
		return action==LOGIN || action==LOGIN_OK || action==LOGIN_FAIL ||
				action==IS_LOGIN_REQ || action==LOGIN_REQ_TRUE || action==LOGIN_REQ_FALSE;
	}
	public static boolean isPreLogin(Msg msg) {
		return isPreLogin(msg.getAction());
	}
    
    private int action;
    private Serializable[] args;
    
    /**
     * Create a new Msg object.
     * @param action An indicator of the type of message being sent.
     * @param args An array of the objects to send.
     */
    public Msg(int action, Serializable... args) {
        this.action = action;
        this.args = args;
    }
    
    /**
     * Create a new Msg object.
     * @param action An indicator of the type of message being sent.
     */
    public Msg(int action) {
        this(action, (Serializable) null);
    }
    
    /**
     * Gets the action of this message.
     * @return The action of this message.
     */
    public int getAction() {
        return action;
    }
    
    /**
     * Gets the arguments of this message.
     * @return The arguments of this message.
     */
    public Serializable[] getArgs() {
        return args;
    }
    
    /**
     * Gets an argument from this message.
     * @param i The position of the argument to get.
     * @return The argument in the given position of the argument array.
     */
    public Serializable getArg(int i) {
        return args[i];
    }
    
    @Override
    public String toString() {
        if(args!=null) {
            String out = "Message[action="+action;
            for (Serializable arg : args) {
                if(arg==null) out += ", null";
                else out += ", "+arg.toString();
            }
            return out+"]";
        } else {
            return "Message[action="+action+"]";
        }
    }
    
}
