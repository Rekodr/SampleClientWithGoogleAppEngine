package commands;

import commands.ICommand;
import restapi.IPresenceService;
import restapi.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

public class Friends implements ICommand {
    public void execute(Object[] argv) throws RemoteException {
        Vector<User> reg_list = ((IPresenceService)argv[0]).listRegisteredUsers();

        // only one user. meaning you are alone.
        if (reg_list.size() == 1) {
            System.out.println( "No friend available." );
            return;
        }

        for(User registration : reg_list ) {
            String local_name = ((User)argv[1]).getUsername();
            String fried_name = registration.getUsername();
            if(!local_name.equals( fried_name )) {
                String status = (registration.getStatus() == true) ? "Available" : "Not available";
                System.out.println(registration.getUsername() + ": " + status);
            }
        }
    }
}

