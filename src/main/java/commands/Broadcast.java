package commands;

import com.MessageSender;
import restapi.IPresenceService;
import restapi.User;

import java.rmi.RemoteException;

public class Broadcast implements ICommand {
    public void execute(Object[] argv) throws RemoteException {
        try {
            User src = (User) argv[1];
            String msg = "[ " + src.getUsername() + " ] ";
            String temp = "";
            // build the message.
            for(int i = 2; i < argv.length; i++) {
                temp += (String) argv[i] + " ";
            }

            if(temp.trim().isEmpty()) {
                System.out.println( "Cannot send empty msg." );
                return;
            }

            msg += temp ;

            for(User registration : ((IPresenceService)argv[0]).listRegisteredUsers()) {
                String fried_name = registration.getUsername();
                if(!src.getUsername().equals( fried_name )) {
                    if(registration.getStatus()) {
                        MessageSender.sendMessage( src, registration.getUsername(), (IPresenceService)argv[0], msg );
                    }
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println( "Please provide receiver and message." );
        }
    }
}
