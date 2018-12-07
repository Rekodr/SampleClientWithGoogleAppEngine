package com;

import restapi.IPresenceService;
import restapi.User;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class MessageSender implements Runnable{

    private String message;
    private String host;
    private int port;

    public MessageSender(String msg, User dst_user) {
        this.message = msg;
        this.port = dst_user.getPort();
        this.host = dst_user.getHost();
    }

    public void send() {
        try {
            PrintStream os;

            System.out.println("Sending message...");
            Socket clientSocket = new Socket(this.host, this.port);

            os = new PrintStream(clientSocket.getOutputStream());

            if(this.message  == null) {
                return;
            }
            os.println(this.message);
            System.out.println( "Message sent." );

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(User src, String dst_username, IPresenceService presenceService, String msg) {

        User dst_user = presenceService.lookup( dst_username );

        if(dst_user == null) {
            System.out.println( "Destination user is not registered." );
            return;
        }

        if(!dst_user.getStatus()) {
            System.out.println( "Destination user is not available." );
            return;
        }



        MessageSender sender = new MessageSender( msg, dst_user );
        Thread thread = new Thread( sender );
        thread.start();

    }

    @Override
    public void run() {
        this.send();
    }
}

