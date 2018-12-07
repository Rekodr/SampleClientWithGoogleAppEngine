package commands;

import restapi.IPresenceService;
import restapi.User;

import java.rmi.RemoteException;

public class Exit implements ICommand {
    public void execute(Object[] argv) throws RemoteException {
        String username = ((User)argv[1]).getUsername();
        ((IPresenceService)argv[0]).unregister(username);
        System.out.println("You have unregister yourself. Bye.");
        System.exit(0);
    }
}


