/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sauds.toolbox.server.client.manager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saud
 */
public class ExampleClient {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ClientManager cm = new ClientManager();
        cm.addConnectionChangeListener(new Runnable() {
            @Override
            public void run() {
                System.out.println(cm.isConnected()+", "+cm.isLoggedIn());
            }
        });
        cm.addMessageListener(new MessageListener() {
            @Override
            public void onReceived(Msg m) {
                System.out.println("received: "+m);
            }
        });
        cm.connect("localhost", 8888);
        System.out.println(cm.login("saud", "12345"));
        cm.sendMessage(new Msg(15, "hahaha"));
        cm.stopRecievingThread();
        
        Thread.sleep(5000);
    }
    
}
