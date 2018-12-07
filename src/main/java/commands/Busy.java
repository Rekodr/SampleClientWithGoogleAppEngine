package commands;

import restapi.IPresenceService;
import restapi.User;

import java.rmi.RemoteException;

public class Busy implements ICommand {
    public void execute(Object[] argv) throws RemoteException {
        if(((User)argv[1]).getStatus() == false) {
            System.out.println("Your status is already set to: Not Available ");
            return;
        }

        ((User)argv[1]).setStatus(false);
        boolean result = ((IPresenceService)argv[0]).updateUser((User) argv[1]);
        if(result) {
            System.out.println("Your was updated to: Not Available.");
        } else {
            System.out.println("An error occured.");
        }
    }

}

