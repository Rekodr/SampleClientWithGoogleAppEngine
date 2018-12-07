package restapi;
/**
 * <p>Title: Lab2</p>
 * <p>Description: Old School Instant Messaging Application </p>
 * @author Jonathan Engelsma
 * @version 1.0
 */


import java.util.Vector;

/**
 * The abstract interface that is to implemented by a remote
 * presence server.  ChatClients will use this interface to
 * register themselves with the presence server, and also to
 * determine and locate other users who are available for chat
 * sessions.
 */
public interface IPresenceService  {

    /**
     * Register a client with the presence service.
     * @param user The information that is to be registered about a client.
     * @return true if the user was successfully registered, or false if somebody
     * the given name already exists in the system.
     */
    boolean register(User user);

    /**
     * Updates the information of a currently registered client.
     * @param user The updated registration info.
     * @return true if successful, or false if no user with the given
     * name is registered.
     *
     */
    boolean updateUser(User user);

    /**
     * Unregister a client from the presence service.  MessageSender must call this
     * method when it terminates execution.
     * @param username The name of the user to be unregistered.
     */
    void unregister(String username);

    /**
     * Lookup the registration information of another client.
     * @name The name of the client that is to be located.
     * @return The RegistrationInfo info for the client, or null if
     * no such client was found.
     */
    User lookup(String name);

    /**
     * Determine all users who are currently registered in the system.
     * @return An array of RegistrationInfo objects - one for each client
     * present in the system.
     */
    Vector<User> listRegisteredUsers();
}
