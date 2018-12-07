package restapi;

import com.MessageListener;
import commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class ClientRestAPI {

    public static void main(String args[]) {

        // cmds
        HashMap<String, Object> commands = new HashMap<>();
        commands.put( "friends", new Friends() );
        commands.put( "busy", new Busy() );
        commands.put( "available", new Available() );
        commands.put( "exit", new Exit() );
        commands.put( "talk", new Talk() );
        commands.put( "broadcast", new Broadcast() );


        String user_name = null;
        int port = 3000;

        try {

            user_name = args[0];
            if (user_name == null) {
                System.out.println( "Please provide a user name" );
                System.exit( -1 );
            }

            port = Integer.parseInt( args[1] );
        } catch (NumberFormatException e) {
            System.out.println( "Default port 3000 used." );
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println( "Default port 3000 used." );
        }


        try {

            IPresenceService presenceService = new PresenceService();

            User user = new User( user_name, "localhost", port, true );
            boolean registered = presenceService.register( user );

            if (!registered) {
                System.out.println( "Faild to register. Please try letter" );
                System.exit( -1 );
            }

            MessageListener listener;
            try {
                listener = new MessageListener( user );
                Thread thread = new Thread( listener );
                thread.start();

            } catch (IOException e) {
                presenceService.unregister( user.getUsername() );
                System.out.println( "Port: " + port + " is taken. Please try a different port." );
                System.exit( -1 );
            }

            String msg = user_name + " joined the network.";
            Object[] temp = msg.split( " " );
            Object[] notes = buildCommand( presenceService, user, temp );
            Broadcast notify = new Broadcast();
            notify.execute( notes );

            System.out.println( msg );
            //Enter data using BufferReader
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );

            while (true) {
                String input = reader.readLine();
                if (input != null) {
                    Object[] arg = input.split( " " );
                    if (commands.containsKey( arg[0] )) {
                        Object[] arguments = buildCommand( presenceService, user, arg );
                        ICommand cmd = (ICommand) commands.get( arg[0] );
                        cmd.execute( arguments );
                    }
                }
            }
        } catch (Exception e) {
            System.err.println( "PresenceService exception:" );
            e.printStackTrace();
        }
    }


    public static Object[] buildCommand(IPresenceService presenceService, User reg, Object[] args) {
        ArrayList<Object> params = new ArrayList<>();
        params.add( presenceService );
        params.add( reg );
        for (int i = 1; i < args.length; i++) {
            params.add( args[i] );
        }
        return params.toArray();
    }
}
