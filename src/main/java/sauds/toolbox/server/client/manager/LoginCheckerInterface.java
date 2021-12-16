
package sauds.toolbox.server.client.manager;

/**
 * This interface is used to check if the given login credentials are valid.
 * @author Saud Fatayerji
 */
public interface LoginCheckerInterface {
    
    /**
     * Checks if the given login credentials are valid.
     * @param username The username.
     * @param password The password.
     * @return True if the credentials are valid.
     */
    public boolean isValid(String username, String password);
    
}
