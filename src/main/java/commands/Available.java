package commands;

import restapi.IPresenceService;
import restapi.User;

import java.rmi.RemoteException;

public class Available implements ICommand {
    public void execute(Object[] argv) throws RemoteException {
        if(((User)argv[1]).getStatus() == true) {
            System.out.println("Your status is already set to: Available ");
            return;
        }

        ((User)argv[1]).setStatus(true);
        boolean result = ((IPresenceService)argv[0]).updateUser((User)argv[1]);
        if(result) {
            System.out.println("Your status was updated to: Available.");
        } else {
            System.out.println("An error occured.");
        }
    }
}

