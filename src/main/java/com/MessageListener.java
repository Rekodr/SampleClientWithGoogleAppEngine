package com;

import restapi.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageListener implements Runnable {

    private boolean status ;
    private int port = 1999;
    private ServerSocket echoServer = null;


    public MessageListener(User user) throws IOException {
        this.status = user.getStatus();
        this.port = user.getPort();

        echoServer = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        Socket clientSocket = null;

        while(true) {
            if(status) try {
                System.out.println( "Ready to receive notifications" );
                clientSocket = echoServer.accept();
                Thread thread = new Thread( new ProcessIncomingRequest( clientSocket ) );
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

